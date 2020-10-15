package ru.fds.kafka.producer.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import ru.fds.kafka.producer.Constants;

@Configuration
public class KafkaTopicConfig {

    private final Constants constants;

    public KafkaTopicConfig(Constants constants) {
        this.constants = constants;
    }

    @Bean
    public NewTopic simpleTopicWithTwoPartitions(){
        return TopicBuilder.name(constants.getTopicNameSimple()).partitions(2).build();
    }
}
