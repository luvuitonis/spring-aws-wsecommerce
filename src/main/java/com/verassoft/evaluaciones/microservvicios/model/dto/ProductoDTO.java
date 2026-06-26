package com.verassoft.evaluaciones.microservvicios.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Modelo que representa los datos de un Producto")
public record ProductoDTO(

        @Schema(description = "Identificador único autogenerado del producto", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
        long id,

        @NotBlank(message = "El nombre debe de informarse, no puede ser vacío.")
        @Size(min = 3, max = 100, message = "El nombre debe tener al menos 3 caracteres un máximo de 100.")
        @Schema(description = "Nombre comercial del producto", example = "Teclado Mecánico RGB")
        String nombre,

        @Size(max = 255, message = "La longitud máxima es de 255 caracteres.")
        @Schema(description = "Descripción detallada de las características del producto", example = "Teclado con switches red y distribución en español.")
        String descripcion,

        @Positive(message = "El precio debe ser un número positivo")
        @Digits(integer = 10, fraction = 2, message = "El precio debe contener al menos 2 decimales como fracción.")
        @Schema(type = "number", format = "double", example = "99.99", description = "El precio del producto no debe de ser negativo.")
        BigDecimal precio,

        @Positive(message = "La cantidad en Stock debe ser un número positivo.")
        @Schema(description = "Unidades disponibles en el inventario", example = "50")
        int cantidadStock,

        @NotBlank(message = "La categoría debe de informarse, no puede ser vacía.")
        @Schema(description = "Categoría o sección a la que pertenece el producto", example = "tecnologia")
        String categoria,

        @Schema(description = "Fecha y hora del registro del producto", example = "2026-05-18T10:15:30", accessMode = Schema.AccessMode.READ_ONLY)
        @NotNull
        @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime fechaCreacion,

        @Schema(description = "Estado de visibilidad del producto. Debe ser 'true' para nuevos registros.", example = "true")
        @AssertTrue(message = "El producto debe de estar activo.")
        boolean activo
) { }
