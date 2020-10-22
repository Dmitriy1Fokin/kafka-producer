package ru.fds.kafka.producer.service;

import org.springframework.web.multipart.MultipartFile;
import ru.fds.kafka.producer.dto.Message;

import java.io.IOException;
import java.util.List;

public interface ProducerService {
    void sendMessage(String msg);
    void sendMessagePartition(String msg);
    void sendMessageWithCallback(String msg);
    List<Integer> sendMessageFilter();
    void sendMessageCustomObject(Message message);
    String sendFile(MultipartFile file) throws IOException;
}
