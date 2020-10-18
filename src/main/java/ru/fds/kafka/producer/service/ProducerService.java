package ru.fds.kafka.producer.service;

import ru.fds.kafka.producer.dto.Message;

import java.util.List;

public interface ProducerService {
    void sendMessage(String msg);
    void sendMessagePartition(String msg);
    void sendMessageWithCallback(String msg);
    List<Integer> sendMessageFilter();
    void sendMessageCustomObject(Message message);
}
