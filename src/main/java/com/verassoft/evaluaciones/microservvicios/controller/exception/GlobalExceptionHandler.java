package com.verassoft.evaluaciones.microservvicios.controller.exception;

import com.verassoft.evaluaciones.microservvicios.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String DELIMITER = " at ";
    private static final String STRING_FORMAT = "%s.%s[%d]";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseNoEncontrado> handlerProductoNoEncontrado(ResourceNotFoundException noEncontrado, HttpServletRequest request) {
        return buildErrorException(noEncontrado, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<ResponseNoEncontrado> handlerArithmeticException(ArithmeticException noEncontrado, HttpServletRequest request) {
        return buildErrorException(noEncontrado, HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(UsuarioNoValidoException.class)
    public ResponseEntity<ResponseNoEncontrado> handlerUsuarioNoValido(UsuarioNoValidoException ex, HttpServletRequest request) {
        return buildErrorException(ex, HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(RuntimeException.class)
    public  ResponseEntity<ResponseNoEncontrado> handlerRuntimeException(RuntimeException noEncontrado, HttpServletRequest request) {
        return buildErrorException(noEncontrado, HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseNoEncontrado> handlerException(Exception exception, HttpServletRequest request) {
        return buildErrorException(exception, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseNoEncontrado> handlerArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> camposNoValidos = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                camposNoValidos.put(error.getField(), error.getDefaultMessage())
                );

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String packageList = getPackageList(ex);
        ResponseNoEncontrado error = new ResponseNoEncontrado();

        error.setDate(LocalDateTime.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.setMessage(camposNoValidos.toString());
        error.setPath(request.getRequestURI());

        log.error("## Method : {}", method);
        log.info("## Error :   {}", packageList);
        log.error("## URI :    {}", uri);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ResponseNoEncontrado> buildErrorException(Exception ex, HttpStatus status, HttpServletRequest request) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String packageList = getPackageList(ex);
        ResponseNoEncontrado error = new ResponseNoEncontrado();

        error.setDate(LocalDateTime.now());
        error.setStatus(status.value());
        error.setError(status.getReasonPhrase());
        StringBuffer stringMessage = new StringBuffer();
        if (!packageList.isEmpty()) {
            stringMessage.append(packageList).append("::");
        }
        stringMessage.append(ex.getMessage().split("\n")[0]);
        error.setMessage(stringMessage.toString());
        error.setPath(request.getRequestURI());

        log.info("## Error:   {}", packageList);
        log.error("## Method: {}", method);
        log.error("## URI:    {}", uri);

        return new ResponseEntity<>(error, status);
    }

    private String getPackageList(Exception ex) {
        String packagePrefix = this.getClass().getPackage().getName().split("\\.")[0];

        return String.join(DELIMITER,
                Arrays.stream(ex.getStackTrace())
                .filter(element -> element.getClassName().startsWith(packagePrefix))
                .map(element -> String.format(STRING_FORMAT,
                        element.getClassName(),
                        element.getMethodName(),
                        element.getLineNumber()))
                .toList());
    }
}
