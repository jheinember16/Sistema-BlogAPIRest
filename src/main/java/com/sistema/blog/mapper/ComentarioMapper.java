package com.sistema.blog.mapper;

import com.sistema.blog.dto.ComentarioDTO;
import com.sistema.blog.entidades.Comentario;
import com.sistema.blog.entidades.Publicacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ComentarioMapper {

    @Mapping(target = "publicacion", ignore = true)
    Comentario toEntity(ComentarioDTO dto);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicacion", source = "publicacion")
    Comentario mapearAEntidadConPublicacion(ComentarioDTO dto, Publicacion publicacion);

    default List<ComentarioDTO> toDtoList(List<Comentario> comentarios) {
        if (comentarios == null) return Collections.emptyList();
        return comentarios.stream().map(this::toDto).toList();
    }
    ComentarioDTO toDto(Comentario comentario);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicacion", ignore = true)
    void actualizarDesdeDto(ComentarioDTO dto, @MappingTarget Comentario entidad);
}