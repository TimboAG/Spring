package com.NuevasChismes.NoticiaC.servicio;

import com.NuevasChismes.NoticiaC.entidad.Imagen;
import com.NuevasChismes.NoticiaC.excepcion.MiException;
import com.NuevasChismes.NoticiaC.repositorio.ImagenRepositorio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepositorio imagenRepositorio;

    @Transactional //impactan
    public Imagen guardar(MultipartFile archivo) throws MiException, Exception {
        validar(archivo);
        Imagen imagen = new Imagen();
        if (archivo != null) {
            try {
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                return null;
            }
        }
        return imagen;
    }

    @Transactional
    public Imagen actualizar(MultipartFile archivo, Long id) throws MiException {
        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();
                if (id != null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(id);
                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    @Transactional(readOnly = true) //Es de tipo lectura
    public List<Imagen> listarImagen() {
        List<Imagen> miLista = new ArrayList<>();
        miLista = imagenRepositorio.findAll();
        return miLista;
    }

    private void validar(MultipartFile archivo) throws MiException {
        if (archivo == null) {
            throw new MiException("El archivo no puede estar vacio");
        }
    }
}