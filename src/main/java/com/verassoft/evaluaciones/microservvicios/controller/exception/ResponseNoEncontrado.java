package com.verassoft.evaluaciones.microservvicios.controller.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ResponseNoEncontrado {

    private LocalDateTime date;
    private int status;
    private String error;
    private String message;
    private String path;

}
