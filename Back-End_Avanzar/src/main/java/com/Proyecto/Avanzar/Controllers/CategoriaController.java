package com.Proyecto.Avanzar.Controllers;


import com.Proyecto.Avanzar.Models.Categoria;


import com.Proyecto.Avanzar.Models.Rol;
import com.Proyecto.Avanzar.Models.Vendedor;
import com.Proyecto.Avanzar.Services.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@CrossOrigin(origins = {"http://164.90.153.70:4200"})
@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {
    @Autowired
    CategoriaService categoriaService;

    @PostMapping("/registrar")
    public ResponseEntity<Categoria> crear(@RequestBody Categoria r) {
        try {
            return new ResponseEntity<>(categoriaService.save(r), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/listar")
    public ResponseEntity<List<Categoria>> obtenerLista() {
        try {
            return new ResponseEntity<>(categoriaService.findByAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestBody Categoria categoria) {
        return categoriaService.delete(id);
    }


    @GetMapping("/buscar/{id}")
    public ResponseEntity<Categoria> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<Categoria>(categoriaService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostConstruct
    public void init() {
        Categoria categoria1 = new Categoria(1L, "Productos");
        Categoria categoria2 = new Categoria(2L, "Servicios");


        categoriaService.save(categoria1);
        categoriaService.save(categoria2);
    }

}
