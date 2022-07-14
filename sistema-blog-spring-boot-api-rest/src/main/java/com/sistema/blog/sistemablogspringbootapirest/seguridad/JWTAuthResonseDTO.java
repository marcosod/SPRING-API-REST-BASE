package com.sistema.blog.sistemablogspringbootapirest.seguridad;

import lombok.Data;

@Data
public class JWTAuthResonseDTO {


    private String tokenDeAcceso;
    private String tipoDeToken = "Bearer";

    public JWTAuthResonseDTO(String tokenDeAcceso) {
        this.tokenDeAcceso = tokenDeAcceso;
    }
}
