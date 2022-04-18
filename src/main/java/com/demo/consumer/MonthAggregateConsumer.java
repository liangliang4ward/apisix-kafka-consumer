package com.demo.consumer;

import com.demo.model.AggregateData;
import com.demo.service.entity.MonthCountDo;
import com.demo.service.manager.IMonthCountManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author gaoll
 * @time 2022/4/11 11:09
 **/
@Component
@Slf4j
public class MonthAggregateConsumer extends AbsAggregateConsumer {
    @Autowired
    private IMonthCountManager monthCountManager;


    @KafkaListener(topics = "${consumer.kafka.output.month.topic}", groupId = "monthAggregateP")
    public void process(ConsumerRecord<String, String> consumerRecord) {
        AggregateData resultData = null;
        try {
            resultData = getAggregateData(consumerRecord);
        } catch (Exception e) {
            log.error("consumer MonthAggregateConsumer", e);
        }
        if (resultData == null) {
            return;
        }
        MonthCountDo countDo = new MonthCountDo();
        countDo.setServiceId(resultData.getServiceId());
        countDo.setCountDate(resultData.getDataTime());
        countDo.setUsername(resultData.getUsername());
        countDo.setAbout4xxCount(resultData.getAbout4xxCount());
        countDo.setAbout5xxCount(resultData.getAbout5xxCount());
        countDo.setCount(resultData.getCount());
        countDo.setId(resultData.getKey());
        countDo.setSuccessCount(resultData.getSuccessCount());
        monthCountManager.saveOrUpdate(countDo);

    }


}