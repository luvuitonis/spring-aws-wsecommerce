package com.verassoft.evaluaciones.microservvicios.controller.exception;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "productos-ms", url = "http://localhost:8088/products")
public class UsuarioNoValidoException extends RuntimeException {
    public UsuarioNoValidoException(String message) {
        super(message);
    }

}
