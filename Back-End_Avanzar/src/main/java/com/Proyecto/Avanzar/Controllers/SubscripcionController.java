package com.Proyecto.Avanzar.Controllers;

import com.Proyecto.Avanzar.Models.Rol;
import com.Proyecto.Avanzar.Models.Subscripcion;
import com.Proyecto.Avanzar.Services.service.SubscripcionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/subscripcion")
public class SubscripcionController {
    @Autowired
    SubscripcionService subscripcionService;

    @PostConstruct
    public void init() {

        Subscripcion subscripcion1 = new Subscripcion(1L, "FREE", 0, 3 );
        Subscripcion subscripcion2 = new Subscripcion(2L, "PREMIUM SILVER", 5, 7 );
        Subscripcion subscripcion3 = new Subscripcion(3L, "PREMIUM GOLD", 10, 12 );

        subscripcionService.save(subscripcion1);
        subscripcionService.save(subscripcion2);
        subscripcionService.save(subscripcion3);


    }
 @PostMapping("/registrar")
    public ResponseEntity<Subscripcion> crear(@RequestBody Subscripcion r) {
        try {
            return new ResponseEntity<>(subscripcionService.save(r), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Subscripcion>> obtenerLista() {
        try {
            return new ResponseEntity<>(subscripcionService.findByAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestBody Subscripcion subscripcion) {
        return subscripcionService.delete(id);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Subscripcion> actualizar(@PathVariable Long id,@RequestBody Subscripcion p) {
        Subscripcion subscripcion = subscripcionService.findById(id);
        if (subscripcion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                subscripcion.setNombreSubscripcion(p.getNombreSubscripcion());
                subscripcion.setPrecio(p.getPrecio());
                subscripcion.setNumPublicaciones(p.getNumPublicaciones());
                subscripcion.setListaDetalleSubscripcion(p.getListaDetalleSubscripcion());
                return new ResponseEntity<>(subscripcionService.save(subscripcion), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Subscripcion> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<Subscripcion>(subscripcionService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}