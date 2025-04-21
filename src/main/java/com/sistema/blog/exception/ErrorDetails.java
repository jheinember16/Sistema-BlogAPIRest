package com.sistema.blog.exception;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter  // Genera automáticamente todos los getters
@AllArgsConstructor  // Genera un constructor con todos los campos
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
}