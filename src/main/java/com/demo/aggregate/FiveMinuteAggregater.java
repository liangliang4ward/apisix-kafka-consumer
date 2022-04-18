package com.demo.aggregate;

import com.demo.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author gaoll
 * @time 2022/4/11 11:11
 **/
@Component
@Slf4j
public class FiveMinuteAggregater extends AbsAggregater{

    @Value("${consumer.kafka.output.fiveminute.topic}")
    private String outputTopic;

    @Autowired
    public void init(@Qualifier("accessLogStream") KStream<String, String> streams) {
        process(streams,dateTime-> DateUtils.getPreEndWithMinute0or5(new Date(dateTime)),"fiveMinuteCount");
    }



    @Override
    String getOutputTopic() {
        return this.outputTopic;
    }
}
