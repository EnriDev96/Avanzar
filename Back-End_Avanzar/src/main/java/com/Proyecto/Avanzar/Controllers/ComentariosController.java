package com.Proyecto.Avanzar.Controllers;

import com.Proyecto.Avanzar.Models.Comentarios;
import com.Proyecto.Avanzar.Models.ComentariosDto;
import com.Proyecto.Avanzar.Models.Productos;
import com.Proyecto.Avanzar.Services.service.ComentariosService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/comentarios")
public class ComentariosController {

    @Autowired
    ComentariosService comentariosService;

    @PostMapping("/registrar")
    public ResponseEntity<Comentarios> crear(@RequestBody Comentarios r) {

        try {
            r.setFecha(new Date());
            return new ResponseEntity<>(comentariosService.save(r), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Comentarios>> obtenerLista() {
        try {
            return new ResponseEntity<>(comentariosService.findByAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestBody Comentarios comentarios) {
        return comentariosService.delete(id);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Comentarios> actualizar(@PathVariable Long id, @RequestBody Comentarios p) {
        Comentarios comentarios = comentariosService.findById(id);
        if (comentarios == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                comentarios.setTexto(p.getTexto());
                comentarios.setFecha(p.getFecha());
                comentarios.setPublicaciones(p.getPublicaciones());
                comentarios.setUsuario(p.getUsuario());
                return new ResponseEntity<>(comentariosService.save(comentarios), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    @GetMapping("/listCommentP/{id}/{page}")
    public ResponseEntity<List<ComentariosDto>> listCommentsP(@PathVariable Long id, @PathVariable int page) {
        return comentariosService.listCommentPost(id, page);
    }

    @DeleteMapping("/deleteComment/{id}")
    public ResponseEntity<?> deletComment(@PathVariable Long id) {

        return new ResponseEntity<>(comentariosService.borrarComentario(id), HttpStatus.OK);

    }

    @GetMapping("/Comentarioxus/{id}")
    public ResponseEntity<List<Comentarios>> ComentariosxUsuario(@PathVariable("id") Long id) {
        List<Comentarios> prod = comentariosService.ComentariosxUsuario(id);
        return new ResponseEntity<>(prod, HttpStatus.OK);
    }
}
 //el comentario si se debe eliminar
