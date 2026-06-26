package com.verassoft.evaluaciones.microservvicios.mapper;

import com.verassoft.evaluaciones.microservvicios.model.Producto;
import com.verassoft.evaluaciones.microservvicios.model.dto.ProductoDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-25T19:07:10-0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class ProductoMapperImpl implements ProductoMapper {

    @Override
    public ProductoDTO entityToDto(Producto entity) {
        if ( entity == null ) {
            return null;
        }

        long id = 0L;
        String nombre = null;
        String descripcion = null;
        BigDecimal precio = null;
        int cantidadStock = 0;
        String categoria = null;
        LocalDateTime fechaCreacion = null;
        boolean activo = false;

        id = entity.getId();
        nombre = entity.getNombre();
        descripcion = entity.getDescripcion();
        precio = entity.getPrecio();
        cantidadStock = entity.getCantidadStock();
        categoria = entity.getCategoria();
        fechaCreacion = entity.getFechaCreacion();
        activo = entity.isActivo();

        ProductoDTO productoDTO = new ProductoDTO( id, nombre, descripcion, precio, cantidadStock, categoria, fechaCreacion, activo );

        return productoDTO;
    }

    @Override
    public Producto dtoToEntity(ProductoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Producto producto = new Producto();

        producto.setId( dto.id() );
        producto.setNombre( dto.nombre() );
        producto.setDescripcion( dto.descripcion() );
        producto.setPrecio( dto.precio() );
        producto.setCantidadStock( dto.cantidadStock() );
        producto.setCategoria( dto.categoria() );
        producto.setFechaCreacion( dto.fechaCreacion() );
        producto.setActivo( dto.activo() );

        return producto;
    }

    @Override
    public ProductoDTO dtoToDto(ProductoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        long id = 0L;
        String nombre = null;
        String descripcion = null;
        BigDecimal precio = null;
        int cantidadStock = 0;
        String categoria = null;
        LocalDateTime fechaCreacion = null;
        boolean activo = false;

        id = dto.id();
        nombre = dto.nombre();
        descripcion = dto.descripcion();
        precio = dto.precio();
        cantidadStock = dto.cantidadStock();
        categoria = dto.categoria();
        fechaCreacion = dto.fechaCreacion();
        activo = dto.activo();

        ProductoDTO productoDTO = new ProductoDTO( id, nombre, descripcion, precio, cantidadStock, categoria, fechaCreacion, activo );

        return productoDTO;
    }
}
