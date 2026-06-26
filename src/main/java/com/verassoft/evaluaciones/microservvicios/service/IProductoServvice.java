package com.verassoft.evaluaciones.microservvicios.service;

import com.verassoft.evaluaciones.microservvicios.model.dto.ProductoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductoServvice {
    Page<ProductoDTO> obtenerProductosActivos(Pageable pageable);
    ProductoDTO obtenerProductoPorId(long id);
    void actualizaProductoPorId(ProductoDTO dto, long id);
    void borrarProductoPorId(long id);
    Page<ProductoDTO> obtenerPorCategoria(String categoria, Pageable pageable);


    Page<ProductoDTO> obtenerPorNombreOrDescripcion(String nombreOrdescripcion, Pageable pageable);

}
