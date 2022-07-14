package com.sistema.blog.sistemablogspringbootapirest.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class BlogAPPExceptions extends  RuntimeException{
    private HttpStatus estado;
    private String mensaje;
    private String detalle;
    private Date fecha;
    public BlogAPPExceptions(HttpStatus estado, String mensaje) {
        super(mensaje);
        this.estado = estado;
        this.mensaje = mensaje;
    }
    public BlogAPPExceptions(HttpStatus estado, String mensaje,  String detalle, Date fecha) {
        super(mensaje);
        this.fecha = fecha;
        this.estado = estado;
        this.mensaje = mensaje;
        this.detalle = detalle;
    }


}
