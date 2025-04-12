package com.sistema.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicacionDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String contenido;
}
