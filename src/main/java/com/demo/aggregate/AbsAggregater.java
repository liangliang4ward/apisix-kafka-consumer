package com.demo.aggregate;

import com.demo.model.AccessLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.connect.json.JsonDeserializer;
import org.apache.kafka.connect.json.JsonSerializer;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

import java.util.function.Function;

/**
 * @author gaoll
 * @time 2022/4/11 15:40
 **/
@Slf4j
public abstract class AbsAggregater {

    public static final String CONSUMER_PREFIX = "csss_";

    abstract String getOutputTopic();
    @Value("${consumer.kafka.input.local-filter:false}")
    private boolean localFilter;

    private ObjectMapper objectMapper = new ObjectMapper();

    private JsonNode parseNode(String value) {
        try {
            return objectMapper.readValue(value, JsonNode.class);
        } catch (JsonProcessingException e) {
            log.error("parse node error,value={}", value, e);
            return null;
        }
    }

    void process(KStream<String, String> accessLogStream, Function<Long, String> dateProcess, String countName) {
        final Serializer<JsonNode> jsonSerializer = new JsonSerializer();
        ErrorHandlingDeserializer<JsonNode> jsonHandler= new ErrorHandlingDeserializer<>(new JsonDeserializer());
        jsonHandler.setFailedDeserializationFunction(failedDeserializationInfo -> null);
        final Serde<JsonNode> jsonSerde = Serdes.serdeFrom(jsonSerializer, jsonHandler);
        KTable<String, String> result = accessLogStream.filterNot((key, value) -> {
            JsonNode jsonNode = parseNode(value);
            AccessLog accessLog = buildAccessLogInfo(jsonNode);
            if(jsonNode==null){
                return true;
            }
            if (basicFilter(accessLog)) {
                return false;
            }

            return true;
        }).map((key, value) -> {
            JsonNode node = parseNode(value);
            AccessLog info = buildAccessLogInfo(node);
            if(info==null){
                return null;
            }
            String newKey = info.getServiceId() + "#" + info.getUsername().replaceFirst(CONSUMER_PREFIX, "") + "#" + dateProcess.apply(info.getStartTime());
            return KeyValue.pair(newKey, value);
        }).groupByKey(Grouped.with(Serdes.String(), Serdes.String())).aggregate(() -> null, (key, value, aggregate) -> {
            if (value == null) {
                return aggregate;
            }
            AccessLog info = buildAccessLogInfo(parseNode(value));
            if (aggregate == null) {
                aggregate = objectMapper.createObjectNode();
                ((ObjectNode) aggregate).set("count", LongNode.valueOf(0L));
                ((ObjectNode) aggregate).set("successCount", LongNode.valueOf(0L));
                ((ObjectNode) aggregate).set("about4xxCount", LongNode.valueOf(0L));
                ((ObjectNode) aggregate).set("about5xxCount", LongNode.valueOf(0L));

            }
            if (aggregate instanceof ObjectNode) {
                ((ObjectNode) aggregate).set("key", TextNode.valueOf(key));
                ((ObjectNode) aggregate).set("count", LongNode.valueOf(aggregate.at("/count").longValue() + 1));
                if (filterSuccess(info)) {
                    ((ObjectNode) aggregate).set("successCount", LongNode.valueOf(aggregate.at("/successCount").longValue() + 1));
                }
                if (filter4xx(info)) {
                    ((ObjectNode) aggregate).set("about4xxCount", LongNode.valueOf(aggregate.at("/about4xxCount").longValue() + 1));
                }
                if (filter5xx(info)) {
                    ((ObjectNode) aggregate).set("about5xxCount", LongNode.valueOf(aggregate.at("/about5xxCount").longValue() + 1));
                }
            }
            return aggregate;
        }, Materialized.with(Serdes.String(), jsonSerde)).mapValues(value -> "count=" + value.at("/count").longValue() + ",successCount=" + value.at("/successCount").asLong() + ",about4xxCount=" + value.at("/about4xxCount") + ",about5xxCount=" + value.at("/about5xxCount"), Materialized.with(Serdes.String(), Serdes.String()));
        result.toStream().to(getOutputTopic());
    }

    private AccessLog buildAccessLogInfo(JsonNode jsonNode) {
        if(jsonNode==null){
            return null;
        }
        AccessLog info = new AccessLog();
        info.setRouteId(jsonNode.at("/route_id").asText());
        info.setUsername(jsonNode.at("/consumer/username").asText());
        info.setStartTime(jsonNode.at("/start_time").longValue());
        info.setResponseStatus(jsonNode.at("/response/status").asText());
        info.setServiceId(jsonNode.at("/service_id").asText());
        return info;
    }

    private boolean basicFilter(AccessLog info) {
        if (info == null) {
            return false;
        }
        if (Strings.isEmpty(info.getUsername())) {
            return false;
        }
        if (Strings.isEmpty(info.getRouteId())) {
            return false;
        }
        if (Strings.isEmpty(info.getServiceId())) {
            return false;
        }

        return true;
    }


    private Integer getLastStatus(AccessLog info) {
        String status = info.getResponseStatus();
        if (Strings.isEmpty(status)) {
            return null;
        }
        String[] statusArray = status.split(",");
        if (statusArray.length == 0) {
            return null;
        }
        try {
            return Integer.valueOf(statusArray[statusArray.length - 1].replace(" ",""));
        } catch (NumberFormatException e) {
            log.error("number format exception ,info={}",info,e);
            return null;
        }

    }


    private boolean filterSuccess(AccessLog info) {
        if (!basicFilter(info)) {
            return false;
        }
        Integer lastStatus = getLastStatus(info);
        if (lastStatus == null) {
            return false;
        }

        if (lastStatus < 400) {
            return true;
        }
        return false;
    }

    private boolean filter4xx(AccessLog info) {
        if (!basicFilter(info)) {
            return false;
        }
        Integer lastStatus = getLastStatus(info);
        if (lastStatus == null) {
            return false;
        }
        if (lastStatus >= 400 && lastStatus < 500) {
            return true;
        }
        return false;
    }

    private boolean filter5xx(AccessLog info) {
        if (!basicFilter(info)) {
            return false;
        }
        Integer lastStatus = getLastStatus(info);
        if (lastStatus == null) {
            return false;
        }
        if (lastStatus >= 500) {
            return true;
        }
        return false;
    }

}
