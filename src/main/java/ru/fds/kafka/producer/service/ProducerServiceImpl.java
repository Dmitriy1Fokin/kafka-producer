package ru.fds.kafka.producer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.fds.kafka.producer.Constants;

@Slf4j
@Service
public class ProducerServiceImpl implements ProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Constants constants;
    private final NewTopic topic;

    public ProducerServiceImpl(KafkaTemplate<String, String> kafkaTemplate,
                               Constants constants,
                               NewTopic topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.constants = constants;
        this.topic = topic;
    }

    @Override
    public void sendMessage(String msg) {
        log.info("topic name: {}, message: {}", constants.getTopicNameSimple(), msg);
        kafkaTemplate.send(topic.name(), 0, "ss", msg);
    }

    @Override
    public void sendMessagePartition(String msg) {
        log.info("topic name: {}, message: {}", constants.getTopicNameSimple(), msg);
        kafkaTemplate.send(topic.name(), 1, "ssqq", msg);
    }

    @Override
    public void sendMessageWithCallback(String msg){
        log.info("topic name: {}, message: {}", constants.getTopicNameCallback(), msg);

        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(constants.getTopicNameSimple(), msg);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Send message=[{}] with offset=[{}]", msg, result.getRecordMetadata().offset());
            }
            @Override
            public void onFailure(Throwable ex) {
                log.info("Unable to send message=[{}] due to : {}", msg, ex.getMessage());
            }
        });
    }
}
