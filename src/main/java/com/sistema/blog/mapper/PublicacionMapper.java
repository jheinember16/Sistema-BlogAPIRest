package com.sistema.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.sistema.blog.entidades.Publicacion;
import com.sistema.blog.dto.PublicacionDTO;

import java.util.List;

@Mapper(componentModel = "spring", uses = ComentarioMapper.class)
public interface PublicacionMapper {

        @Mapping(target = "comentarios", source = "comentarios")
        PublicacionDTO toDto(Publicacion publicacion);

        @Mapping(target = "comentarios", ignore = true)
        Publicacion toEntity(PublicacionDTO dto);

        List<PublicacionDTO> toDto(List<Publicacion> publicaciones);
        List<Publicacion> toEntity(List<PublicacionDTO> dtos);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "comentarios", ignore = true)
        void actualizarDesdeDto(PublicacionDTO dto, @MappingTarget Publicacion entidad);
}