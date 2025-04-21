package com.sistema.blog.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Data
public class ComentarioDTO {

    private Long id;

    @NotEmpty(message = "El nombre no debe estar vacío")
    private String nombre;

    @NotEmpty(message = "El email no debe estar vacío")
    @Email(message = "El email debe ser válido")
    private String email;

    @NotEmpty(message = "El cuerpo del comentario debe tener al menos 10 caracteres")
    private String cuerpo;
}