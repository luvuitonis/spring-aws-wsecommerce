package com.verassoft.evaluaciones.microservvicios.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Modelo que representa un modelo con uno o más valores no permitidos.")
public record ProductoErrorDTO(

        @Size(min = 3, max = 100, message = "El nombre debe tener al menos 3 caracteres un máximo de 100.")
        @Schema(description = "El nombre debe de informarse, no puede ser vacío", example = "RG")
        String nombre,

        @Size(max = 255, message = "La longitud máxima es de 255 caracteres.")
        @Schema(description = "La longitud máxima es de 255 caracteres", example = "Teclado con switches red y distribución en español...256")
        String descripcion,

        @Positive(message = "El precio debe ser un número positivo")
        @Digits(integer = 10, fraction = 2, message = "El precio debe contener al menos 2 decimales como fracción.")
        @Schema(type = "number", format = "double", example = "-99.99", description = "El precio del producto no debe de ser negativo.")
        BigDecimal precio,

        @Positive(message = "La cantidad en Stock debe ser un número positivo.")
        @Schema(description = "La cantidad en Stock debe ser un número positivo", example = "-50")
        int cantidadStock,

        @NotBlank(message = "La categoría debe de informarse, no puede ser vacía.")
        @Schema(description = "La categoría debe de informarse, no puede ser vacía", example = "null/_")
        String categoria,

        @Schema(description = "Fecha y hora del registro del producto", example = "2026-05-18T10:15:30", accessMode = Schema.AccessMode.READ_ONLY)
        @NotNull
        @DateTimeFormat(pattern = "dd-MM-yyyyTHH:mm:ss")
        LocalDateTime fechaCreacion,

        @Schema(description = "Estado de visibilidad del producto. Debe ser 'true' para nuevos registros.", example = "false")
        @AssertTrue(message = "El producto debe de estar activo.")
        boolean activo
) {
}
