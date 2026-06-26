package com.verassoft.evaluaciones.microservvicios.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String nombre;
    String descripcion;
    BigDecimal precio;
    int cantidadStock;
    String categoria;
    LocalDateTime fechaCreacion;
    boolean activo;

}
