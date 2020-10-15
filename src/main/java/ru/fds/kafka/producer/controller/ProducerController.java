package ru.fds.kafka.producer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.fds.kafka.producer.service.ProducerService;

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

}
