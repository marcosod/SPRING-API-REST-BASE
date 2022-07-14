package com.sistema.blog.sistemablogspringbootapirest.dto;

import com.sistema.blog.sistemablogspringbootapirest.exceptions.BlogAPPExceptions;
import jdk.jfr.StackTrace;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
@Data
public class RespuestaError {

    private String fecha;
    private String mensaje;
    private String detalles;

    public RespuestaError(Date fecha, String mensaje, String detalles) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);

        this.fecha = strDate;
        this.mensaje = mensaje;
        this.detalles = detalles;
    }
}
