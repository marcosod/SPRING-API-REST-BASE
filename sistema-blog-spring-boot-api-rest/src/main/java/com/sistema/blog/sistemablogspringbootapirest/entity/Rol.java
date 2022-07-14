package com.sistema.blog.sistemablogspringbootapirest.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 60)
    private String nombre;
}
