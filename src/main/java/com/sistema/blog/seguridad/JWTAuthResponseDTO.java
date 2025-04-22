package com.sistema.blog.seguridad;

import lombok.Data;

@Data
public class JWTAuthResponseDTO {
    private String tokenDeAcceso;
    private String tipoDeToken = "Bearer";

    // Constructor con solo el token
    public JWTAuthResponseDTO(String tokenDeAcceso) {
        super();
        this.tokenDeAcceso = tokenDeAcceso;
    }

    // Constructor con token y tipo de token
    public JWTAuthResponseDTO(String tokenDeAcceso, String tipoDeToken) {
        super();
        this.tokenDeAcceso = tokenDeAcceso;
        this.tipoDeToken = tipoDeToken;
    }

    // Constructor sin argumentos (para Lombok y frameworks)
    public JWTAuthResponseDTO() {
        super();
    }
}