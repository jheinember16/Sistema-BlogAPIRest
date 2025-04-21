package com.sistema.blog.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ResponseDTO<T> {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private String message;
    private T data;
}