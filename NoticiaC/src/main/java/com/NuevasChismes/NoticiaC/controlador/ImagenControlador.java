package com.NuevasChismes.NoticiaC.controlador;

import com.NuevasChismes.NoticiaC.entidad.Noticia;
import com.NuevasChismes.NoticiaC.entidad.Usuario;
import com.NuevasChismes.NoticiaC.servicio.NoticiaServicio;
import com.NuevasChismes.NoticiaC.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {

    @Autowired
    private NoticiaServicio notiServ;

    @GetMapping("/imagen2/{id}")
    public ResponseEntity<byte[]> imagen2(@PathVariable Long id) {
        Noticia noticia = notiServ.getOne(id);
        byte[] imagen = noticia.getFoto().getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagen(@PathVariable String id) {
        Usuario usuario = usuarioServicio.getOne(id);
        byte[] imagen = usuario.getImagen().getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
}