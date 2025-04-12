package com.sistema.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import com.sistema.blog.entidades.Publicacion;
import com.sistema.blog.dto.PublicacionDTO;

@Mapper
public interface PublicacionMapper {

    PublicacionMapper mapper = Mappers.getMapper(PublicacionMapper.class);

    PublicacionDTO toDto(Publicacion publicacion);

    Publicacion toEntity(PublicacionDTO dto);

    @Mapping(target = "id", ignore = true)
    void actualizarDesdeDto(PublicacionDTO dto, @MappingTarget Publicacion entidad);
}
