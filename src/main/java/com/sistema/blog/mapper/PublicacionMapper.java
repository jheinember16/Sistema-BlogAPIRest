package com.sistema.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.sistema.blog.entidades.Publicacion;
import com.sistema.blog.dto.PublicacionDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PublicacionMapper {

        PublicacionDTO toDto(Publicacion publicacion);
        Publicacion toEntity(PublicacionDTO dto);

        List<PublicacionDTO> toDto(List<Publicacion> publicaciones);
        List<Publicacion> toEntity(List<PublicacionDTO> dtos);

        @Mapping(target = "id", ignore = true)
        void actualizarDesdeDto(PublicacionDTO dto, @MappingTarget Publicacion entidad);
}
