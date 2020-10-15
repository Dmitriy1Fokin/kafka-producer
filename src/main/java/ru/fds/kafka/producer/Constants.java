package ru.fds.kafka.producer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "ru.fds.kafka.producer")
public class Constants {

    private String bootstrapAddress;
    private String topicNameSimple;
    private String topicNameCallback;

}
