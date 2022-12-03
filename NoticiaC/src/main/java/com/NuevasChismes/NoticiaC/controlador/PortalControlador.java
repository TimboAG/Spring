package com.NuevasChismes.NoticiaC.controlador;

import com.NuevasChismes.NoticiaC.entidad.Usuario;
import com.NuevasChismes.NoticiaC.excepcion.MiException;
import com.NuevasChismes.NoticiaC.servicio.UsuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registro";
    }

    @PostMapping("/registro")
    public String regristro(@RequestParam("foto") MultipartFile foto, @RequestParam("nombre") String nombre, @RequestParam("email") String email, @RequestParam("pass") String pass, @RequestParam("pass2") String pass2, ModelMap modelo) throws MiException, Exception {
        try {
            usuarioServicio.registrar(foto, nombre, email, pass, pass2);
            modelo.put("exito", "El usuario fue registrado correctamente!");
        } catch (MiException ex) {
            System.out.println(ex);
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            modelo.put("pass", pass);
            modelo.put("pass2", pass2);
            modelo.put("foto", foto);
            return "registro";
        }
        return "login";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("Error", "El usuario o la contraseña es incorrecto");
        }
        return "login";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);
        return "perfil";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/perfilM")
    public String perfilModificar(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);
        return "perfilModificar";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/perfilM/{id}")
    public String actualizar(HttpSession session, @RequestParam("foto") MultipartFile archivo, @PathVariable String id, @RequestParam String nombre, @RequestParam String email,
            @RequestParam String pass, @RequestParam String pass2, ModelMap modelo) {
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuariosession");
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            modelo.put("pass", pass);
            modelo.put("pass2", pass2);
            modelo.put("foto", archivo);
            usuarioServicio.actualizar(archivo, usuario.getId(), nombre, email, pass, pass2);
            modelo.put("exito", "Usuario actualizado correctamente!");
            return "/";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "perfilModificar";
        }
    }
}