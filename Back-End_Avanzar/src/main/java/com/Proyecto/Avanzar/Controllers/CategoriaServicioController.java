package com.Proyecto.Avanzar.Controllers;


import com.Proyecto.Avanzar.Models.CategoriaProducto;
import com.Proyecto.Avanzar.Models.CategoriaServicio;
import com.Proyecto.Avanzar.Models.Rol;
import com.Proyecto.Avanzar.Services.service.CategoriaServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/categoriaServicio")
public class CategoriaServicioController {
    @Autowired
    CategoriaServicioService categoriaServicioService;
    @PostMapping("/registrar")
    public ResponseEntity<CategoriaServicio> crear(@RequestBody CategoriaServicio r) {
        try {
            return new ResponseEntity<>(categoriaServicioService.save(r), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CategoriaServicio>> obtenerLista() {
        try {
            return new ResponseEntity<>(categoriaServicioService.findByAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestBody CategoriaServicio categoriaServicio) {
        return categoriaServicioService.delete(id);
    }


    @GetMapping("/buscar/{id}")
    public ResponseEntity<CategoriaServicio> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<CategoriaServicio>(categoriaServicioService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostConstruct
    public void init() {
        CategoriaServicio categoriaServicio1 = new CategoriaServicio(1L, "Belleza");
        CategoriaServicio categoriaServicio2 = new CategoriaServicio(2L, "Cocina");
        CategoriaServicio categoriaServicio3 = new CategoriaServicio(3L, "Costura");
        CategoriaServicio categoriaServicio4 = new CategoriaServicio(4L, "Salud");
        CategoriaServicio categoriaServicio5 = new CategoriaServicio(5L, "Asesoria");
        CategoriaServicio categoriaServicio6 = new CategoriaServicio(6L, "Limpieza");
        CategoriaServicio categoriaServicio7 = new CategoriaServicio(7L, "Eventos");
        CategoriaServicio categoriaServicio8 = new CategoriaServicio(8L, "Otros");


        categoriaServicioService.save(categoriaServicio1);
        categoriaServicioService.save(categoriaServicio2);
        categoriaServicioService.save(categoriaServicio3);
        categoriaServicioService.save(categoriaServicio4);
        categoriaServicioService.save(categoriaServicio5);
        categoriaServicioService.save(categoriaServicio6);
        categoriaServicioService.save(categoriaServicio7);
        categoriaServicioService.save(categoriaServicio8);
    }
}
