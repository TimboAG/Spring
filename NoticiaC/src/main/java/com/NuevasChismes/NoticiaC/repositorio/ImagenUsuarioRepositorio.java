package com.NuevasChismes.NoticiaC.repositorio;

import com.NuevasChismes.NoticiaC.entidad.ImagenUsuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenUsuarioRepositorio extends JpaRepository<ImagenUsuario, Long> {

    @Override
    public Optional<ImagenUsuario> findById(Long id);

}