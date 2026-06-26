package com.verassoft.evaluaciones.microservvicios.repository;

import com.verassoft.evaluaciones.microservvicios.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Page<Producto> findByActivo(boolean activo, Pageable pageable);
    Page<Producto> findByCategoria(String categoria, Pageable pageable);
    Page<Producto> findByPrecioBetween(BigDecimal precioMin, BigDecimal precioMax, Pageable pegeable);


    Page<Producto> findByNombreIsContainingOrDescripcionIsContaining(String nombre, String  descripcion, Pageable pageable);


}
