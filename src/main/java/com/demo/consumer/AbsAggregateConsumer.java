package com.demo.consumer;

import cn.hutool.core.util.ReflectUtil;
import com.demo.model.AggregateData;
import com.demo.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * @author gaoll
 * @time 2022/4/11 15:36
 **/
@Slf4j
public abstract class AbsAggregateConsumer {


    protected AggregateData getAggregateData(ConsumerRecord<String, String> consumerRecord) {
        AggregateData result = getData(consumerRecord);
        if(result==null){
            log.error("aggregateData is null,key={},value={}",consumerRecord.key(),consumerRecord.value());
        }
        return result;
    }

    private AggregateData getData(ConsumerRecord<String, String> consumerRecord) {
        String key = consumerRecord.key();
        String value = consumerRecord.value();
        String[] keyArray = key.split("#");
        if (keyArray.length != 3) {
            return null;
        }
        String serviceId = keyArray[0];
        String username = keyArray[1];
        String datetime = keyArray[2];

        String[] valueArray = value.split(",");

        AggregateData resultData = new AggregateData();
        resultData.setKey(key);
        resultData.setServiceId(serviceId);
        resultData.setUsername(username);
        resultData.setDataTime(DateUtils.parseDate("yyyyMMddHHmmss", datetime));

        for (String valueString : valueArray) {
            String[] allCountKeyValue = valueString.split("=");
            if (allCountKeyValue.length != 2) {
                return null;
            }
            String countKey = allCountKeyValue[0];
            String valueCount = allCountKeyValue[1];
            Long longValue = 0L;
            if (!"null".equals(valueCount)) {
                longValue = Long.valueOf(valueCount);
            }
            ReflectUtil.setFieldValue(resultData, countKey, longValue);
        }
        return resultData;
    }
}
