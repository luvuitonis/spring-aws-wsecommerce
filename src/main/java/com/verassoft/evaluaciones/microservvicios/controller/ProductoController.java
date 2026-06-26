package com.verassoft.evaluaciones.microservvicios.controller;

import com.verassoft.evaluaciones.microservvicios.model.dto.ProductoDTO;
import com.verassoft.evaluaciones.microservvicios.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Controlador para la gestión y consultas de productos del e-commerce")
public class ProductoController {

    private static final Logger log = LoggerFactory.getLogger(ProductoController.class);
    private final ProductoService service;

    @PostMapping
    @Operation(summary = "Crear un nuevo producto",
            description = "Registra un producto en el sistema y lo devuelve con su ID generado.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Producto creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductoDTO.class),
                            examples = @ExampleObject(
                                    name = "Respuesta exitosa",
                                    summary = "Datos del producto válido",
                                    value = """
                                            {
                                              "id": 1,
                                              "nombre": "Teclado Mecánico RGB",
                                              "descripcion": "Teclado con switches red y distribución en español.",
                                              "precio": 99.99,
                                              "cantidadStock": 50,
                                              "categoria": "tecnologia",
                                              "fechaCreacion": "2026-06-09T02:18:00.569Z",
                                              "activo": true
                                            }
                                            """
                            )
                    )),
            @ApiResponse(
                    responseCode = "400",
                    description = "Producto no válido",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductoDTO.class),
                            examples = @ExampleObject(
                                    name = "Respuesta no exitosa",
                                    summary = "Datos del producto no válido",
                                    value = """
                                            {
                                              "nombre": "RG",
                                              "descripcion": "Teclado con switches red y distribución en español...256",
                                              "precio": -99.99,
                                              "cantidadStock": -50,
                                              "categoria": "null/_",
                                              "fechaCreacion": "2026-06-09T02:18:00.571Z",
                                              "activo": false
                                            }
                                            """
                            )
                    ))
    })
    public ResponseEntity<ProductoDTO> crearProducto(@Valid @RequestBody ProductoDTO dto) {

        log.info("## Creeacion de producto para ecommerce.");

        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearProducto(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto existente", description = "Modifica los datos de un producto existente utilizando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto actualizado exitosamente"),
            @ApiResponse(responseCode = "406", description = "Producto no encontrado")
    })
    public ResponseEntity<Void> actualiza(
            @Parameter(description = "ID del producto a modificar", example = "1")
            @Valid @RequestBody ProductoDTO dto,
            @PathVariable long id) {

        log.info("## Actualización de un producto para ecommerce.");
        service.actualizaProductoPorId(dto, id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar (o desactivar) un producto", description = "Remueve un producto del catálogo por su ID. Retorna un estado 204 sin contenido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Void> eliminarProducto(
            @Parameter(description = "ID del producto a eliminar", example = "1")
            @PathVariable long id) {

        log.info("## Eliminación de un producto para ecommerce.");
        service.borrarProductoPorId(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    @Operation(summary = "Obtiene un producto regisstrado", description = "Obtiene un producto regisstrado.")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<Page<ProductoDTO>> obtenerActivos(
            @Parameter(description = "Configuración de la paginación (page, size, sort)")
            @PageableDefault(size = 4) Pageable pageable) {

        log.info("## Búsqueda de productos activvos para ecommerce.");
        Page<ProductoDTO> listaProductos = service.obtenerProductosActivos(pageable);

        return ResponseEntity.ok(/*service.obtenerProductosActivos(pageable)*/ listaProductos);
    }
//
//    @GetMapping("/{id}")
//    @Operation(summary = "Buscar producto por ID", description = "Devuelve los detalles de un único producto basado en su identificador numérico.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
//            @ApiResponse(responseCode = "406", description = "Producto no encontrado")
//    })
//    public ResponseEntity<ProductoDTO> obtenerPorId(
//            @Parameter(description = "ID del producto a buscar", example = "1")
//            @PathVariable long id) {
//
//        log.info("## Búsqueda de producto por identificador para ecommerce.");
//
//        return ResponseEntity.status(HttpStatus.OK).body(service.obtenerProductoPorId(id));
//    }
//
//    @GetMapping("/categoria/{categoria}")
//    @Operation(summary = "Buscar productos por categoría", description = "Retorna una lista paginada de productos que pertenecen a una categoría específica.")
//    @ApiResponse(responseCode = "200", description = "Operación exitosa")
//    public ResponseEntity<Page<ProductoDTO>> obtenerPorCategoria(
//            @Parameter(description = "Nombre de la categoría", example = "electronica") @PathVariable String categoria,
//            @Parameter(description = "Configuración de la paginación") @PageableDefault(size = 3) Pageable pageable) {
//
//        log.info("## Búsqueda de un producto por categoría para ecommerce.");
//
//        return ResponseEntity.ok(service.obtenerPorCategoria(categoria, pageable));
//    }
//
//    @GetMapping("/buscar")
//    @Operation(summary = "Buscar productos por rango de precios", description = "Filtra de manera paginada los productos cuyo precio se encuentre entre el mínimo y máximo indicados.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Operación exitosa")
//    })
//    public ResponseEntity<Page<ProductoDTO>> obtenerPorPrecios(
//            @Parameter(description = "Precio mínimo del filtro", example = "10.50") @RequestParam BigDecimal precioMin,
//            @Parameter(description = "Precio máximo del filtro", example = "99.99") @RequestParam BigDecimal precioMax,
//            @Parameter(description = "Configuración de la paginación") @PageableDefault(size = 2) Pageable pageable) {
//
//        log.info("## Búsqueda de un producto entre rango de precios para ecommerce.");
//
//        return ResponseEntity.ok( service.obtenerRangoPrecioMinMax(precioMin, precioMax, pageable));
//    }

//    @GetMapping("/busqueda/global/{nombreOrDescripcion}")
//    public ResponseEntity<Page<ProductoDTO>> obtenerPorNoombreOrDescripcion(
//            @PathVariable String nombreOrDescripcion,
//            @PageableDefault(size = 2) Pageable pageable
//    ) {
//
//        return ResponseEntity.ok(
//                service.obtenerPorNombreOrDescripcion(nombreOrDescripcion, pageable)
//        );
//    }

}
