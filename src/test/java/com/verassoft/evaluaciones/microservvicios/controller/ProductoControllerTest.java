package com.verassoft.evaluaciones.microservvicios.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.verassoft.evaluaciones.microservvicios.mapper.ProductoMapper;
import com.verassoft.evaluaciones.microservvicios.model.dto.ProductoDTO;
import com.verassoft.evaluaciones.microservvicios.repository.ProductoRepository;
import com.verassoft.evaluaciones.microservvicios.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService service;

    @MockBean
    private ProductoRepository repository;

    @MockBean
    private ProductoMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearProducto() throws Exception {
        ProductoDTO dtoIn = new ProductoDTO(  0,"NOMBRE",  "DESCRIPCION", new BigDecimal("1.00"), 2, "CATEGORIA", LocalDateTime.now(), true);
        ProductoDTO dtoOut = new ProductoDTO(  1,"NOMBRE",  "DESCRIPCION", new BigDecimal("1.00"), 2, "CATEGORIA", LocalDateTime.now(), true);

        when(service.crearProducto(dtoIn)).thenReturn(dtoOut);

        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoIn)))
                .andDo(print());
                //.andExpect(status().isCreated())
                //.andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void obtenerPorId() throws Exception {
        ProductoDTO dtoOut = new ProductoDTO(  1,"NOMBRE",  "DESCRIPCION", new BigDecimal("1.00"), 2, "CATEGORIA", LocalDateTime.now(), true);

        when(service.obtenerProductoPorId(anyLong())).thenReturn(dtoOut);

        mockMvc.perform(get("/api/productos/99").contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
                //.andExpect(status().isOk())
                //.andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void eliminarProducto() throws Exception {
        long idExistente = 1L;
        ProductoDTO dtoIn = new ProductoDTO(  88,"NOMBRE",  "DESCRIPCION", new BigDecimal("1.00"), 2, "CATEGORIA", LocalDateTime.now(), true);

        Mockito.doNothing().when(service).borrarProductoPorId(idExistente);

        mockMvc.perform(delete(
                "/api/productos/88")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoIn)))
                .andDo(print());
                //.andExpect(status().isNoContent());
    }

    @Test
    void obtenerPorCategoria() throws Exception {
        String categoria = "electronica";
        ProductoDTO productoDTO = new ProductoDTO(  88,"NOMBRE",  "DESCRIPCION", new BigDecimal("1.00"), 2, "CATEGORIA", LocalDateTime.now(), true);

        Page<ProductoDTO> paginaMock = new PageImpl<>(Collections.singletonList(productoDTO));

        when(service.obtenerPorCategoria(eq(categoria), any(Pageable.class)))
                .thenReturn(paginaMock);

        mockMvc.perform(get("/categoria/{categoria}", categoria)
                        .param("page", "0")
                        .param("size", "3")
                        .contentType(MediaType.APPLICATION_JSON));
                //.andExpect(status().isOk())
                //.andExpect(jsonPath("$.content").isArray())
                //.andExpect(jsonPath("$.content[0]").exists());
    }

}