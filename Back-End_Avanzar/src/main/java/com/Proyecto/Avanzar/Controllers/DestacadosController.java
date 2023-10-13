package com.Proyecto.Avanzar.Controllers;

import com.Proyecto.Avanzar.Models.Destacados;
import com.Proyecto.Avanzar.Models.Publicaciones;
import com.Proyecto.Avanzar.Repository.DestacadoRepository;
import com.Proyecto.Avanzar.Services.implement.DestacadoServiceImpl;
import com.Proyecto.Avanzar.Services.service.PublicacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.Proyecto.Avanzar.Services.service.DestacadoService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/likes")
public class DestacadosController {

    @Autowired
    DestacadoService likesService;
    @Autowired
    DestacadoServiceImpl destacadoRepository;

    @Autowired
    PublicacionesService publicacionesService;

    @Autowired
    DestacadoRepository destacadoRep;

    @PostMapping("/registrar")
    public ResponseEntity<?> crear(@RequestBody Destacados r) {
        // Verificar si ya existe un registro con el mismo publicacion_id y usuario_id
        boolean yaDestacado = destacadoRepository.existeDestacado(r.getPublicaciones().getIdPublicacion(), r.getUsuario().getId());

        if (yaDestacado) {
            return new ResponseEntity<>("El usuario ya ha destacado esta publicación.", HttpStatus.BAD_REQUEST);
        }

        try {
            // Guardar el registro en Destacados
            Destacados nuevoDestacado = likesService.save(r);

            // Cargar la entidad Publicaciones que deseas actualizar
            Publicaciones publicacion = publicacionesService.findById(r.getPublicaciones().getIdPublicacion());

            if (publicacion != null) {
                // Actualizar el campo visible en la entidad Publicaciones para que quede en el fronted el icono de color rojo
                publicacion.setVisible(true);
                // Guardar la entidad Publicaciones actualizada en la base de datos
                publicacionesService.save(publicacion);
            }

            return new ResponseEntity<>(nuevoDestacado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /*
    @PostMapping("/registrar")
    public ResponseEntity<Destacados> crear(@RequestBody Destacados r) {
        try {
            return new ResponseEntity<>(likesService.save(r), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @GetMapping("/listar")
    public ResponseEntity<List<Destacados>> obtenerLista() {
        try {
            return new ResponseEntity<>(likesService.findByAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listConId/{id}")

    public ResponseEntity<List<Destacados>> listarDestacados(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(destacadoRep.listDestacados(id), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> borrarFavorito(@PathVariable Long id) {

        return new ResponseEntity<>(likesService.borrarFavorito(id), HttpStatus.OK);

    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Destacados> actualizar(@PathVariable Long id, @RequestBody Destacados p) {
        Destacados likes = likesService.findById(id);
        if (likes == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                likes.setFecha(p.getFecha());
                likes.setPublicaciones(p.getPublicaciones());
                likes.setIdDestacado(p.getIdDestacado());
                likes.setUsuario(p.getUsuario());
                likes.setEstadoDestacado(p.isEstadoDestacado());

                return new ResponseEntity<>(likesService.save(likes), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }
}
