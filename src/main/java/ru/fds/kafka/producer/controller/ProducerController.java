package ru.fds.kafka.producer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.fds.kafka.producer.dto.Message;
import ru.fds.kafka.producer.service.ProducerService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/producer")
public class ProducerController {

    private final ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("send")
    public String sendSimpleMessage(@RequestParam("msg") String message){
        producerService.sendMessage(message);
        producerService.sendMessagePartition(message);
        return HttpStatus.OK.toString();
    }

    @GetMapping("send/callback")
    public String sendSimpleMessageWithCallback(@RequestParam("msg") String message){
        producerService.sendMessageWithCallback(message);
        return HttpStatus.OK.toString();
    }

    @GetMapping("send/filter")
    public List<Integer> sendMessageFilter() {
        return producerService.sendMessageFilter();
    }

    @GetMapping("send/messageObject")
    public HttpStatus sendMessageObject(@RequestBody Message message){
        producerService.sendMessageCustomObject(message);
        return HttpStatus.OK;
    }

    @GetMapping("send/file")
    public String sendFile(@RequestParam("file") MultipartFile file){
        try {
            return producerService.sendFile(file);
        } catch (IOException e) {
            return e.getLocalizedMessage();
        }
    }
}
