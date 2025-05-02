package com.sistema.blog.dto;

import com.sistema.blog.entidades.Comentario;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicacionDTO {

    private Long id;

    @NotEmpty
    @Size(min = 2,message = "El titulo de la publicación deberia tener al menos 2 caracteres")
    private String titulo;

    @NotEmpty
    @Size(min = 10,message = "La descripción de la publicación deberia tener al menos 10 caracteres")
    private String descripcion;

    @NotEmpty
    @Size(min = 15,message = "El contenido de la publicación deberia tener al menos 15 caracteres")
    private String contenido;

    private Set<Comentario> comentarios;

}