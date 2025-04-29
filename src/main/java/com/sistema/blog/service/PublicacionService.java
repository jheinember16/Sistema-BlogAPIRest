package com.sistema.blog.service;
import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.dto.PublicacionRespuesta;

import java.util.List;

public interface PublicacionService {

    PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO);
    public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagina,int medidaDePagina,String ordenarPor,String sortDir);
    PublicacionDTO obtenerPublicacionPorId(Long id);
    PublicacionDTO actualizarPublicacion(Long id, PublicacionDTO publicacionDTO);
    void eliminarPublicacion(Long id);
}