package com.verassoft.evaluaciones.microservvicios.service;

import com.verassoft.evaluaciones.microservvicios.controller.exception.UsuarioNoValidoException;
import com.verassoft.evaluaciones.microservvicios.exceptions.ResourceNotFoundException;
import com.verassoft.evaluaciones.microservvicios.mapper.ProductoMapper;
import com.verassoft.evaluaciones.microservvicios.model.Producto;
import com.verassoft.evaluaciones.microservvicios.model.dto.ProductoDTO;
import com.verassoft.evaluaciones.microservvicios.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @InjectMocks
    private ProductoService service;

    @Mock
    private ProductoRepository repository;

    @Mock
    private ProductoMapper mapper;

    @Captor
    private ArgumentCaptor<ProductoDTO> productoDTOCaptor;

    @Test
    void crearProductoExitosamente() {
        ProductoDTO dto = new ProductoDTO(1,  "NOMBRE",  "DESCRIPCION", new BigDecimal("1.00"), 2, "CATEGORIA", LocalDateTime.now(), true);
        Producto entity = new Producto();
        entity.setId(1);
        entity.setNombre("NOMBRE");
        entity.setDescripcion("DESCRIPCION");
        entity.setCategoria("CATEGORIA");
        entity.setPrecio(new BigDecimal("99.32"));
        entity.setCantidadStock(845);
        entity.setFechaCreacion(LocalDateTime.now());
        entity.setActivo(true);

        when(mapper.dtoToEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(service.crearProducto(dto)).thenReturn(dto);

        ProductoDTO response = service.crearProducto(dto);

        assertNotNull(response);
    }

    @Test
    void crearProductoDatosInvalidos() {
        ProductoDTO dto = new ProductoDTO(1,  "NO",  "DESCRIPCION", new BigDecimal("1.00"), 2, "CATEGORIA", LocalDateTime.now(), true);
        Producto entity = new Producto();
        entity.setId(1);
        entity.setNombre("NO");
        entity.setDescripcion("DESCRIPCION");
        entity.setCategoria("CATEGORIA");
        entity.setPrecio(new BigDecimal("99.32"));
        entity.setCantidadStock(845);
        entity.setFechaCreacion(LocalDateTime.now());
        entity.setActivo(true);

        when(mapper.dtoToEntity(dto)).thenReturn(entity);

        UsuarioNoValidoException exception = assertThrows(
                UsuarioNoValidoException.class,
                () -> service.crearProducto(dto),
                "El usuario  no  cumple con las restricciones solicitadas.");

        assertEquals("El usuario  no cumple  con las  restricciones solicitadas.", exception.getMessage());
    }

    @Test
    void buscarProductoIdExistente() throws Exception {
        long idProducto = 99;
        ProductoDTO dto = new ProductoDTO(1,  "NOMBRE",  "DESCRIPCION", new BigDecimal("1.00"), 2, "CATEGORIA", LocalDateTime.now(), true);
        Producto entity = new Producto();
        entity.setId(1);
        entity.setNombre("NOMBRE");
        entity.setDescripcion("DESCRIPCION");
        entity.setCategoria("CATEGORIA");
        entity.setPrecio(new BigDecimal("99.32"));
        entity.setCantidadStock(845);
        entity.setFechaCreacion(LocalDateTime.now());
        entity.setActivo(true);

        when(mapper.entityToDto(entity)).thenReturn(dto);
        when(repository.findById(idProducto)).thenReturn(Optional.of(entity));

        ProductoDTO response = service.obtenerProductoPorId(idProducto);

        assertNotNull(response);
    }

    @Test
    void buscarProductoIdNoExistente() {
        long idProducto = 99;

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.obtenerProductoPorId(idProducto),
                "El producto no existe, verificar los datos de búsqueda.");

        assertEquals("El producto: " + idProducto + " buscado no existe, verificar los datos de búsqueda", exception.getMessage());
    }

    @Test
    void actalizarProductoExitosamente() {
        long idProducto = 99;
        ProductoDTO dto = new ProductoDTO(1,  "NOMBRE",  "DESCRIPCION", new BigDecimal("1.00"), 2, "CATEGORIA", LocalDateTime.now(), true);
        Producto entity = new Producto();
        entity.setId(1);
        entity.setNombre("NOMBRE");
        entity.setDescripcion("DESCRIPCION");
        entity.setCategoria("CATEGORIA");
        entity.setPrecio(new BigDecimal("99.32"));
        entity.setCantidadStock(845);
        entity.setFechaCreacion(LocalDateTime.now());
        entity.setActivo(true);

        when(repository.findById(idProducto)).thenReturn(Optional.of(entity));
        when(mapper.dtoToEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.entityToDto(entity)).thenReturn(dto);

        service.actualizaProductoPorId(dto, idProducto);
    }

//    @Test
//    void eliminarProductoVerificarInactivo() {
//        long idProducto = 99;
//        ProductoDTO dto = new ProductoDTO(1,  "NOMBRE",  "DESCRIPCION", new BigDecimal("1.00"), 2, "CATEGORIA", LocalDateTime.now(), true);
//        Producto entity = new Producto();
//        entity.setId(1);
//        entity.setNombre("NOMBRE");
//        entity.setDescripcion("DESCRIPCION");
//        entity.setCategoria("CATEGORIA");
//        entity.setPrecio(new BigDecimal("99.32"));
//        entity.setCantidadStock(845);
//        entity.setFechaCreacion(LocalDateTime.now());
//        entity.setActivo(true);
//
//        when(repository.findById(idProducto)).thenReturn(Optional.of(entity));
//        when(mapper.entityToDto(entity)).thenReturn(dto);
//        when(mapper.dtoToEntity(dto)).thenReturn(entity);
//        when(repository.save(any(Producto.class))).thenAnswer(i-> i.getArguments());
//
//        service.borrarProductoPorId(idProducto);
//        verify(service).crearProducto(productoDTOCaptor.capture());
//
//        ProductoDTO productoEliminado = productoDTOCaptor.getValue();
//
//        assertEquals(false, productoEliminado.activo());
//    }

    @Test
    void listarProductosFiltradosCategoria() {
        String categoria = "gamma";
        List<Producto> listaEntidades = new ArrayList<>();
        Producto entity = new Producto();
        entity.setId(1);
        entity.setNombre("NOMBRE");
        entity.setDescripcion("DESCRIPCION");
        entity.setCategoria("gamma");
        entity.setPrecio(new BigDecimal("99.32"));
        entity.setCantidadStock(845);
        entity.setFechaCreacion(LocalDateTime.now());
        entity.setActivo(true);
        Producto entityTwo = new Producto();
        entityTwo.setId(1);
        entityTwo.setNombre("NOMBRE");
        entityTwo.setDescripcion("DESCRIPCION");
        entityTwo.setCategoria("al");
        entityTwo.setPrecio(new BigDecimal("99.32"));
        entityTwo.setCantidadStock(845);
        entityTwo.setFechaCreacion(LocalDateTime.now());
        entityTwo.setActivo(true);
        ProductoDTO dto = new ProductoDTO(1, "NOMBRE", "DESCRIPCION", new BigDecimal("1.00"), 2, "CATEGORIA", LocalDateTime.now(), true);
        listaEntidades.add(entity);
        listaEntidades.add(entityTwo);

        Pageable pageableProdducto = PageRequest.of(0, 2);

        Page<Producto> paginaProducto = new PageImpl<>(listaEntidades, pageableProdducto, listaEntidades.size());

        when(repository.findByCategoria(categoria, pageableProdducto)).thenReturn(paginaProducto);
        when(mapper.entityToDto(entity)).thenReturn(dto);

        Page<ProductoDTO> resultado = service.obtenerPorCategoria(categoria, pageableProdducto);

        assertNotNull(resultado);
        assertEquals(2, resultado.getTotalElements(), "La página debería contener exactamente 1 elemento");
        assertEquals(2, resultado.getContent().size());
    }
}