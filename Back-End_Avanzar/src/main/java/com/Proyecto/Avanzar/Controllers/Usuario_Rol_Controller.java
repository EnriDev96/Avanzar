package com.Proyecto.Avanzar.Controllers;

import com.Proyecto.Avanzar.Models.Persona;
import com.Proyecto.Avanzar.Models.Rol;
import com.Proyecto.Avanzar.Models.UsuarioRol;
import com.Proyecto.Avanzar.Repository.RolRepository;
import com.Proyecto.Avanzar.Services.implement.RolServiceImpl;
import com.Proyecto.Avanzar.Services.service.RolService;
import com.Proyecto.Avanzar.Services.service.UsuarioRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuariorol")
@CrossOrigin(origins = {"http://164.90.153.70:4200"})
public class Usuario_Rol_Controller {
    @Autowired
    private UsuarioRolService usuarioService;
    private RolService roleservice;

    @Autowired
    private RolServiceImpl rolRepository;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @GetMapping("/listarol")
    public ResponseEntity<List<Rol>> obtenerRoles() {
        try {
            return new ResponseEntity<>(roleservice.findByAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/lista_us_rol")
    public ResponseEntity<List<UsuarioRol>> obtenerLista() {
        try {
            System.out.println(usuarioService.findByAll());
            return new ResponseEntity<>(usuarioService.findByAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/actualizar/{usuarioRolId}")
    public ResponseEntity<UsuarioRol> actualizarRol(@RequestBody UsuarioRol usuarioRol, @PathVariable Long usuarioRolId) {
        try {
            UsuarioRol usuarioRolExistente = usuarioService.findById(usuarioRolId);
            if (usuarioRolExistente != null) {
                String nuevaContraseña = usuarioRol.getUsuario().getPassword();
                // Actualizar la contraseña en el usuario existente
                if (!nuevaContraseña.equals(usuarioRolExistente.getUsuario().getPassword())) {
                    usuarioRolExistente.getUsuario().setPassword(bCryptPasswordEncoder.encode(nuevaContraseña));
                }
                usuarioRolExistente.setRol(usuarioRol.getRol());
                usuarioRolExistente.setUsuario(usuarioRol.getUsuario());
                UsuarioRol usuarioRolActualizado = usuarioService.save(usuarioRolExistente);
                return new ResponseEntity<>(usuarioRolActualizado, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //Query para obtener el nombre del rol del usuario para el inicio de sesion
    @GetMapping("/nombreRol/{usuarioId}")
    public ResponseEntity<?> obtenerRolDeUsuario(@PathVariable Long usuarioId) {
        String rolNombre = rolRepository.obtenerRolNombreDeUsuario(usuarioId);

        if (rolNombre != null) {
            // Construye un objeto Map con el nombre del rol
            Map<String, String> jsonResponse = new HashMap<>();
            jsonResponse.put("nombre", rolNombre);
            return ResponseEntity.ok(jsonResponse);
        } else {

            return ResponseEntity.notFound().build();
        }
    }

}
