package com.Proyecto.Avanzar.Controllers;

import com.Proyecto.Avanzar.Models.Persona;
import com.Proyecto.Avanzar.Models.Usuario;
import com.Proyecto.Avanzar.Services.service.PersonaService;
import com.Proyecto.Avanzar.Services.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/persona")
public class Persona_Controller {
    @Autowired
    PersonaService Service;
    @Autowired
    UsuarioService usuarioS;

    @PostMapping("/registrar")
    public ResponseEntity<Persona> crear(@RequestBody Persona r) {
        try {
            return new ResponseEntity<>(Service.save(r), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Persona>> obtenerLista() {
        try {
            return new ResponseEntity<>(Service.findByAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/buscarpersona/{username}")
    public ResponseEntity<Persona> obtenerPersona(@PathVariable("username") String username) {
        try {
            return new ResponseEntity<>(Service.obtenerPersona(username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByCedula/{cedula}")
    public ResponseEntity<Boolean> checkCedula(@PathVariable("cedula") String cedula) {
        try {
            Persona persona = Service.findByCedula(cedula);
            if (persona != null) {
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByCorreo/{correo}")
    public ResponseEntity<Boolean> checkCorreo(@PathVariable("correo") String correo) {
        try {
            Persona persona = Service.findByCorreo(correo);
            if (persona != null) {
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Persona> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestBody Persona persona) {
        return Service.delete(id);
    }
//Persona
    @PutMapping("/actualizarP/{id}")
    public ResponseEntity<Usuario> actualizarUser(@PathVariable Long id, @RequestBody Usuario p) {
        Usuario usuario = usuarioS.findById(id);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                if(p.getName() != null){
                    usuario.setName(p.getName());

                }
                return new ResponseEntity<>(usuarioS.save(usuario), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Persona> actualizar(@PathVariable Long id, @RequestBody Persona p) {
        Persona persona = Service.findById(id);
        if (persona == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                persona.setCedula(p.getCedula());
                persona.setCelular(p.getCelular());
                persona.setCorreo(p.getCorreo());
                persona.setDescripcion(p.getDescripcion());
                persona.setDireccion(p.getDireccion());
                persona.setFecha_nacimiento(p.getFecha_nacimiento());
                persona.setGenero(p.getGenero());
                persona.setNacionalidad(p.getNacionalidad());
                persona.setPrimer_apellido(p.getPrimer_apellido());
                persona.setPrimer_nombre(p.getPrimer_nombre());
                persona.setSegundo_apellido(p.getSegundo_apellido());
                persona.setSegundo_nombre(p.getSegundo_nombre());


                return new ResponseEntity<>(Service.save(persona), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }


    @GetMapping("/buscarpersonaId/{id}")
    public ResponseEntity<Persona> obtenerPersonaUsuarioId(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.obtenerPersonaPorIdUsuario(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/data1")
    public ResponseEntity<Map<String, Object>> obtenerResumen() {
        try {
            Map<String, Object> resumen = Service.contarRegistrosEnTablas();
            return new ResponseEntity<>(resumen, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/data2/{vendedorId}")
    public ResponseEntity<Map<String, Object>> obtenerResumen2(@PathVariable Long vendedorId) {
        try {
            Map<String, Object> resumen = Service.contarRegistrosEnTablasE(vendedorId);
            return new ResponseEntity<>(resumen, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}