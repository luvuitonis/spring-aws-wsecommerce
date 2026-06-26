package com.verassoft.evaluaciones.microservvicios.mapper;

import com.verassoft.evaluaciones.microservvicios.model.Producto;
import com.verassoft.evaluaciones.microservvicios.model.dto.ProductoDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true))
public interface ProductoMapper {

    ProductoDTO entityToDto(Producto entity);
    Producto dtoToEntity(ProductoDTO dto);
    ProductoDTO dtoToDto(ProductoDTO dto);

}
