package com.NuevasChismes.NoticiaC.servicio;

import com.NuevasChismes.NoticiaC.entidad.ImagenUsuario;
import com.NuevasChismes.NoticiaC.excepcion.MiException;
import com.NuevasChismes.NoticiaC.repositorio.ImagenUsuarioRepositorio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenUsuarioServicio {

    @Autowired
    private ImagenUsuarioRepositorio imagenUsuarioRepositorio;

    @Transactional
    public ImagenUsuario guardar(MultipartFile archivo) throws MiException, Exception {
        validar(archivo);
        ImagenUsuario imagen = new ImagenUsuario();
        if (archivo != null) {
            try {
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenUsuarioRepositorio.save(imagen);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                return null;
            }
        }
        return imagen;
    }

    @Transactional
    public ImagenUsuario actualizar(MultipartFile archivo, Long id) throws MiException {
        if (archivo != null) {
            try {
                ImagenUsuario imagen = new ImagenUsuario();
                if (id != null) {
                    Optional<ImagenUsuario> respuesta = imagenUsuarioRepositorio.findById(id);
                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenUsuarioRepositorio.save(imagen);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    @Transactional(readOnly = true) //Es de tipo lectura
    public List<ImagenUsuario> listarImagen() {
        List<ImagenUsuario> miLista = new ArrayList<>();
        miLista = imagenUsuarioRepositorio.findAll();
        return miLista;
    }

    private void validar(MultipartFile archivo) throws MiException {
        if (archivo == null) {
            throw new MiException("El archivo no puede estar vacio");
        }
    }
}