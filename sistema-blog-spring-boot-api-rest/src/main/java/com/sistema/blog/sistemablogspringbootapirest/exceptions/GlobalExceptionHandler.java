package com.sistema.blog.sistemablogspringbootapirest.exceptions;

import com.sistema.blog.sistemablogspringbootapirest.dto.RespuestaError;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RespuestaError> manejarResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
        RespuestaError respuestaError = new  RespuestaError(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return  new ResponseEntity<>(respuestaError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAPPExceptions.class)
    public ResponseEntity<RespuestaError> manejarBlogAPPExceptions(BlogAPPExceptions exception, WebRequest webRequest){
        RespuestaError respuestaError = new  RespuestaError(new Date(), exception.getMensaje(), webRequest.getDescription(false));
        return  new ResponseEntity<>(respuestaError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespuestaError> manejarGlobalException(Exception exception, WebRequest webRequest){
        RespuestaError respuestaError = new  RespuestaError(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return  new ResponseEntity<>(respuestaError, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    //manejo de excepciones para los @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String nombreCampo = ((FieldError)error).getField();
            String mensaje = error.getDefaultMessage();

            errores.put(nombreCampo, mensaje);
        });

        return new ResponseEntity<>(errores,HttpStatus.BAD_REQUEST);
    }
}
