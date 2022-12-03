package com.NuevasChismes.NoticiaC.repositorio;

import com.NuevasChismes.NoticiaC.entidad.Noticia;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticiaRepositorio extends JpaRepository<Noticia, Long> {

    @Override
    public Optional<Noticia> findById(Long id);
    
    @Query("SELECT n FROM Noticia n WHERE n.alta = 1")
    public List<Noticia> alta();

     @Query("SELECT n FROM Noticia n WHERE n.baja = 1")
    public List<Noticia> baja();
}