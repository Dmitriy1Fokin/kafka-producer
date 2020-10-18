package ru.fds.kafka.producer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Getter
@AllArgsConstructor
public class Message {
    private String text;
    private BigDecimal bigDecimal;
    private LocalDate localDate;
    private LocalDateTime localDateTime;
    private Double aDouble;
    private Integer integer;
}
