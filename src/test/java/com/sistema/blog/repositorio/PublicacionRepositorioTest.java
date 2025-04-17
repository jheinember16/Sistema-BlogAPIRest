package com.sistema.blog.repositorio;

import com.sistema.blog.entidades.Publicacion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PublicacionRepositorioTest {

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Test
    @DisplayName("Guardar publicación correctamente")
    void guardarPublicacion() {
        var publicacion = new Publicacion();
        publicacion.setTitulo("Primera publicación");
        publicacion.setDescripcion("Descripción de prueba");
        publicacion.setContenido("Contenido de la publicación");

        var guardada = publicacionRepositorio.save(publicacion);

        assertThat(guardada).isNotNull();
        assertThat(guardada.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Listar publicaciones correctamente")
    void listarPublicaciones() {
        var pub1 = new Publicacion();
        pub1.setTitulo("Título 1");
        pub1.setDescripcion("Desc 1");
        pub1.setContenido("Contenido 1");

        var pub2 = new Publicacion();
        pub2.setTitulo("Título 2");
        pub2.setDescripcion("Desc 2");
        pub2.setContenido("Contenido 2");

        publicacionRepositorio.save(pub1);
        publicacionRepositorio.save(pub2);

        List<Publicacion> publicaciones = publicacionRepositorio.findAll();

        assertThat(publicaciones).isNotNull();
        assertThat(publicaciones).hasSize(2);
    }

    @Test
    @DisplayName("Obtener publicación por ID existente")
    void obtenerPublicacionPorId() {
        var publicacion = new Publicacion();
        publicacion.setTitulo("Primera publicación");
        publicacion.setDescripcion("Descripción de prueba");
        publicacion.setContenido("Contenido de la publicación");

        var guardada = publicacionRepositorio.save(publicacion);

        Optional<Publicacion> resultado = publicacionRepositorio.findById(guardada.getId());

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getTitulo()).isEqualTo("Primera publicación");
    }

    @Test
    @DisplayName("Actualizar publicación existente")
    void actualizarPublicacion() {
        var publicacion = new Publicacion();
        publicacion.setTitulo("Título original");
        publicacion.setDescripcion("Desc");
        publicacion.setContenido("Contenido");

        var guardada = publicacionRepositorio.save(publicacion);

        guardada.setTitulo("Título actualizado");
        guardada.setContenido("Contenido actualizado");

        var actualizada = publicacionRepositorio.save(guardada);

        assertThat(actualizada.getTitulo()).isEqualTo("Título actualizado");
        assertThat(actualizada.getContenido()).isEqualTo("Contenido actualizado");
    }

    @Test
    @DisplayName("Eliminar publicación por ID")
    void eliminarPublicacion() {
        var publicacion = new Publicacion();
        publicacion.setTitulo("Publicación a eliminar");
        publicacion.setDescripcion("Desc");
        publicacion.setContenido("Contenido");

        var guardada = publicacionRepositorio.save(publicacion);

        publicacionRepositorio.deleteById(guardada.getId());

        Optional<Publicacion> resultado = publicacionRepositorio.findById(guardada.getId());

        assertThat(resultado).isEmpty();
    }
}
