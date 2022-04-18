package com.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.StreamsBuilderFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

/**
 * @author gaoll
 * @time 2022/4/12 11:33
 **/
@Configuration
@EnableKafkaStreams
@Slf4j
public class KafkaStreamConfig {

    @Value("${consumer.kafka.input.topic}")
    private String inputTopic;

    @Bean
    public KStream<String, String> accessLogStream(StreamsBuilder kstreamBuilder) {
        return kstreamBuilder.stream(inputTopic, Consumed.with(Serdes.String(), Serdes.String()));
    }

    @Bean
    public StreamsBuilderFactoryBeanCustomizer streamsBuilderFactoryBeanCustomizer() {
        return factoryBean -> factoryBean.setKafkaStreamsCustomizer(kafkaStreams -> kafkaStreams.setUncaughtExceptionHandler(exception -> {
            log.error("exception ....", exception);
            return StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse.REPLACE_THREAD;
        }));
    }
}
