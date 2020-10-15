package ru.fds.kafka.producer.service;

public interface ProducerService {
    void sendMessage(String msg);
    void sendMessageWithCallback(String msg);
}
