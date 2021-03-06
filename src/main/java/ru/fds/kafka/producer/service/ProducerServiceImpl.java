package ru.fds.kafka.producer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.multipart.MultipartFile;
import ru.fds.kafka.producer.Constants;
import ru.fds.kafka.producer.dto.Message;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProducerServiceImpl implements ProducerService {

    private final KafkaTemplate<String, String> kafkaStringTemplate;
    private final KafkaTemplate<String, Integer> kafkaIntegerTemplate;
    private final KafkaTemplate<String, Message> kafkaMessageTemplate;
    private final KafkaTemplate<String, byte[]> kafkaFileTemplate;
    private final Constants constants;
    private final NewTopic topic;

    public ProducerServiceImpl(KafkaTemplate<String, String> kafkaStringTemplate,
                               KafkaTemplate<String, Integer> kafkaIntegerTemplate,
                               KafkaTemplate<String, Message> kafkaMessageTemplate,
                               KafkaTemplate<String, byte[]> kafkaFileTemplate,
                               Constants constants,
                               NewTopic topic) {
        this.kafkaStringTemplate = kafkaStringTemplate;
        this.kafkaIntegerTemplate = kafkaIntegerTemplate;
        this.kafkaMessageTemplate = kafkaMessageTemplate;
        this.kafkaFileTemplate = kafkaFileTemplate;
        this.constants = constants;
        this.topic = topic;
    }

    @Override
    public void sendMessage(String msg) {
        log.info("sendMessage. topic name: {}, message: {}", constants.getTopicNameSimple(), msg);
        kafkaStringTemplate.send(topic.name(), 0, "ss", msg);
    }

    @Override
    public void sendMessagePartition(String msg) {
        log.info("sendMessagePartition. topic name: {}, message: {}", constants.getTopicNameSimple(), msg);

        kafkaStringTemplate.send(topic.name(), 1, "ssqq", msg);
    }

    @Override
    public void sendMessageWithCallback(String msg){
        log.info("sendMessageWithCallback. topic name: {}, message: {}", constants.getTopicNameCallback(), msg);

        ListenableFuture<SendResult<String, String>> future =
                kafkaStringTemplate.send(constants.getTopicNameSimple(), msg);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("sendMessageWithCallback.onSuccess. Send message=[{}] with offset=[{}]", msg, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("sendMessageWithCallback.onFailure. Unable to send message=[{}] due to : {}", msg, ex.getMessage());
            }
        });
    }

    @Override
    public List<Integer> sendMessageFilter(){
        List<Integer> intValues = new Random().ints(20, 0, 100)
                .boxed()
                .collect(Collectors.toList());
        log.info("sendMessageFilter. topic name: {}, message: {}", constants.getTopicNameFilter(), intValues);
        intValues.forEach(integer ->  kafkaIntegerTemplate.send(constants.getTopicNameFilter(), integer));
        return intValues;
    }

    @Override
    public void sendMessageCustomObject(Message message){
        log.info("sendMessageCustomObject. topic name: {}, message: {}", constants.getTopicNameObject(), message);
        kafkaMessageTemplate.send(constants.getTopicNameObject(), message);
    }

    @Override
    public String sendFile(MultipartFile file) throws IOException {
        log.info("sendFile. topic name: {}, message: {}", constants.getTopicNameFile(), file.getOriginalFilename());
        kafkaFileTemplate.send(constants.getTopicNameFile(), Objects.requireNonNull(file.getOriginalFilename()), file.getBytes());
        return file.getOriginalFilename();
    }

}
