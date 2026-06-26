package com.verassoft.evaluaciones.microservvicios.service;

import com.verassoft.evaluaciones.microservvicios.controller.exception.UsuarioNoValidoException;
import com.verassoft.evaluaciones.microservvicios.exceptions.ResourceNotFoundException;
import com.verassoft.evaluaciones.microservvicios.mapper.ProductoMapper;
import com.verassoft.evaluaciones.microservvicios.model.Producto;
import com.verassoft.evaluaciones.microservvicios.model.dto.ProductoDTO;
import com.verassoft.evaluaciones.microservvicios.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.*;

@Service
@RequiredArgsConstructor
public class ProductoService implements IProductoServvice {

    private final ProductoRepository repository;
    private final ProductoMapper mapper;

    @Transactional
    public ProductoDTO crearProducto(ProductoDTO dto) {
        return usuarioValido(mapper.dtoToEntity(dto));
    }

    private String generarId(String nombre, int edad) {
        return nombre + "_id" + edad;
    }

    private String generarId(int edad, String nombre) {
        return edad + ":id" + nombre;
    }

    private ProductoDTO usuarioValido(Producto entity) {

        if (entity.getNombre() == null ||
                entity.getCategoria() == null ||
                entity.getNombre().length() < 3 ||
                100 < entity.getNombre().length() ||
                255 < entity.getDescripcion().length() ||
                entity.getPrecio().compareTo(BigDecimal.ZERO) < 0 ||
                entity.getCantidadStock() < 0 ||
                !entity.isActivo()) {
            throw new UsuarioNoValidoException("El usuario  no cumple  con las  restricciones solicitadas.");
        }

        return mapper
                .entityToDto(repository.save(entity));
    }

    public Page<ProductoDTO> obtenerProductosActivos(Pageable pageable) {

        return repository.findByActivo(true, pageable)
                .map(mapper::entityToDto);
    }

    public ProductoDTO obtenerProductoPorId(long id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow(() -> new ResourceNotFoundException("El producto: " + id + " buscado no existe, verificar los datos de búsqueda"));
    }

    @Transactional
    public void actualizaProductoPorId(ProductoDTO dto, long id) {

        ProductoDTO response = null;
        Producto entityProduucto = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El producto a actualizar no existe, validar datos de búsqueda."));

        // TODO validar con negocio o cliente, los parámetros que se pueden actualizar para la entidad.
        // Para evitar inconsistencia de datos y errores en el flujo(s).
        if (validarParametrosDeNegocio(dto, entityProduucto)) {
            long idEntidad;

            idEntidad = entityProduucto.getId();
            entityProduucto = mapper.dtoToEntity(dto);
            entityProduucto.setId(idEntidad);
            response = mapper.entityToDto(repository.save(entityProduucto));
        }

        // Verificar si se devuelve el objeto modificado
        //return null;
    }

    private boolean validarParametrosDeNegocio(ProductoDTO dto, Producto entidad) {
        // Validacion de parametros con negocio para los atribtos de la entidad.
        //Ejemplo: el nombre no debe de cambiar.

        // Actualizar la entidad para los atributos permitidos
        return entidad.getNombre().equals(dto.nombre()) && entidad.getCategoria().equals(dto.categoria());
    }

    @Transactional
    public void borrarProductoPorId(long id) {
        Producto entityProduucto = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("El producto: " + id + " a eliminar no existe, verificar los datos del producto."));

        entityProduucto.setActivo(false);
    }

    public Page<ProductoDTO> obtenerPorCategoria(String categoria, Pageable pageable) {

        return repository.findByCategoria(categoria, pageable)
                .map(mapper::entityToDto);
    }

    public Page<ProductoDTO> obtenerRangoPrecioMinMax(BigDecimal min, BigDecimal max, Pageable pageable) {

        return repository.findByPrecioBetween(min, max, pageable)
                .map(mapper::entityToDto);
    }

    public Page<ProductoDTO> obtenerPorNombreOrDescripcion(String nombreOrdescripcion, Pageable pageable) {

        return repository.findByNombreIsContainingOrDescripcionIsContaining(nombreOrdescripcion, nombreOrdescripcion, pageable)
                .map(mapper::entityToDto);
    }
}
