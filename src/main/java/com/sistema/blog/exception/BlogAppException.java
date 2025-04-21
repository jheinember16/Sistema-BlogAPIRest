package com.sistema.blog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BlogAppException extends RuntimeException {
    private final HttpStatus status;

    public BlogAppException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public BlogAppException(HttpStatus status, String message, String detail) {
        super(message + ": " + detail);
        this.status = status;
    }
}