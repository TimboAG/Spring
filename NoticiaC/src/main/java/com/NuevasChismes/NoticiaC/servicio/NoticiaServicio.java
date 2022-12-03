package com.NuevasChismes.NoticiaC.servicio;

import com.NuevasChismes.NoticiaC.entidad.Imagen;
import com.NuevasChismes.NoticiaC.entidad.Noticia;
import com.NuevasChismes.NoticiaC.excepcion.MiException;
import com.NuevasChismes.NoticiaC.repositorio.NoticiaRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NoticiaServicio {

    @Autowired(required = true)
    private NoticiaRepositorio noticiaRepositorio;
    @Autowired(required = true)
    private ImagenServicio miserv;

    @Transactional
    public void crearNoticia(String titulo, String cuerpo, MultipartFile archivo, String alta) throws MiException, Exception {
        validar(titulo, cuerpo, archivo);
        Noticia miNoticia = new Noticia();
        miNoticia.setCuerpo(cuerpo);
        miNoticia.setTitulo(titulo);
        if (alta.equalsIgnoreCase("1")) {
            miNoticia.setAlta(true);
            miNoticia.setBaja(false);
        } else {
            miNoticia.setAlta(false);
            miNoticia.setBaja(true);
        }
        Imagen miImagen = miserv.guardar(archivo);
        miNoticia.setFoto(miImagen);
        noticiaRepositorio.save(miNoticia);
    }

    @Transactional
    public Noticia actualizarNoticia(String titulo, Long id, String cuerpo, MultipartFile archivo, String alta) throws Exception {
        Optional<Noticia> respuesta = noticiaRepositorio.findById(id);
        try {
            Noticia miNoticia = new Noticia();
            if (respuesta.isPresent()) {
                miNoticia = respuesta.get();
            }
            miNoticia.setTitulo(titulo);
            Imagen miImagen = miserv.guardar(archivo);
            miNoticia.setFoto(miImagen);
            miNoticia.setCuerpo(cuerpo);
            if (alta.equalsIgnoreCase("1")) {
                miNoticia.setAlta(true);
                miNoticia.setBaja(false);
            } else {
                miNoticia.setAlta(false);
                miNoticia.setBaja(true);
            }
            return noticiaRepositorio.save(miNoticia);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Transactional
    public List<Noticia> listarNoticias() {
        List<Noticia> miLista = new ArrayList();
        miLista = noticiaRepositorio.findAll();
        return miLista;
    }

    private void validar(String titulo, String cuerpo, MultipartFile archivo) throws MiException {
        if (titulo == null) {
            throw new MiException("El titulo no puede estar vacio");
        }
        if (cuerpo == null) {
            throw new MiException("El cuerpo no puede estar vacio");
        }
        if (archivo == null) {
            throw new MiException("El archivo no puede estar vacio");
        }
    }

    public Noticia getOne(Long id) {
        return noticiaRepositorio.getOne(id);
    }

    @Transactional
    public Noticia eliminar(Long id) throws Exception {
        Optional<Noticia> respuesta = noticiaRepositorio.findById(id);
        try {
            Noticia miNoticia = new Noticia();
            if (respuesta.isPresent()) {
                miNoticia = respuesta.get();
                noticiaRepositorio.deleteById(id);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Transactional
    public Noticia alta(Long id) throws Exception {
        Optional<Noticia> respuesta = noticiaRepositorio.findById(id);
        try {
            Noticia miNoticia = new Noticia();
            if (respuesta.isPresent()) {
                miNoticia = respuesta.get();
            }
            miNoticia.setAlta(true);
            miNoticia.setBaja(false);
            return noticiaRepositorio.save(miNoticia);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Transactional
    public Noticia baja(Long id) throws Exception {
        Optional<Noticia> respuesta = noticiaRepositorio.findById(id);
        try {
            Noticia miNoticia = new Noticia();
            if (respuesta.isPresent()) {
                miNoticia = respuesta.get();
            }
            miNoticia.setAlta(false);
            miNoticia.setBaja(true);
            return noticiaRepositorio.save(miNoticia);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Transactional
    public List<Noticia> listaAlta() {
        List<Noticia> miLista = new ArrayList();
        Noticia noticia = new Noticia();
        List<Noticia> noticiaAlta = noticiaRepositorio.alta();
        for (int i = 0; i < noticiaAlta.size(); i++) {
            noticia = noticiaAlta.get(i);
            miLista.add(noticia);
        }
        return miLista;
    }

    @Transactional
    public List<Noticia> listaBaja() {
        List<Noticia> miLista = new ArrayList();
        Noticia noticia = new Noticia();
        List<Noticia> noticiaBaja = noticiaRepositorio.baja();
        for (int i = 0; i < noticiaBaja.size(); i++) {
            noticia = noticiaBaja.get(i);
            miLista.add(noticia);
        }
        return miLista;
    }

}