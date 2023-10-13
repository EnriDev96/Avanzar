package com.Proyecto.Avanzar.Controllers;

import com.Proyecto.Avanzar.Excepciones.ResourceNotFoundException;
import com.Proyecto.Avanzar.Models.Detalle_Subscripcion;
import com.Proyecto.Avanzar.Models.Publicaciones;
import com.Proyecto.Avanzar.Models.Subscripcion;
import com.Proyecto.Avanzar.Models.Vendedor;
import com.Proyecto.Avanzar.Models.dto.mensajeAlertasDto;
import com.Proyecto.Avanzar.Repository.Detalle_SubscripcionRepository;
import com.Proyecto.Avanzar.Services.implement.Detalle_SubcripcionService;
import com.Proyecto.Avanzar.Services.service.Detalle_SubscripcionService;
import com.Proyecto.Avanzar.Services.service.SubscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/detalleSubscripcion")
public class Detalle_SubcripcionController {

    @Autowired
    Detalle_SubscripcionService DetallesubscripcionService;

    @Autowired
    private Detalle_SubscripcionRepository detalleSubscripcionRepository;

    @Autowired
    private SubscripcionService subscripcionService;

    @GetMapping("/listar")
    public ResponseEntity<List<Detalle_Subscripcion>> obtenerLista() {
        try {
            return new ResponseEntity<>(DetallesubscripcionService.findByAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Metodo para restablecer la membresia Patch
    /*
    @PutMapping("/actualizarDetalleSubscripcion/{idDetalleSubscripcion}")
    public ResponseEntity<Detalle_Subscripcion> actualizarDetalleSubscripcion(
            @PathVariable Long idDetalleSubscripcion) {
        try {
            Detalle_Subscripcion detalleSubscripcionExistente = detalleSubscripcionRepository.findById(idDetalleSubscripcion)
                    .orElseThrow(() -> new ResourceNotFoundException("Detalle_Subscripcion no encontrado con el id: " + idDetalleSubscripcion));

            // Actualizar las fechas de inicio y fin automáticamente
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            detalleSubscripcionExistente.setFechaInicio(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 30); // Sumar 30 días
            detalleSubscripcionExistente.setFechaFin(calendar.getTime());

            // Guardar los cambios en el detalleSubscripcionExistente
            detalleSubscripcionRepository.save(detalleSubscripcionExistente);

            return new ResponseEntity<>(detalleSubscripcionExistente, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @PutMapping("/actualizarDetalleSubscripcion/{idDetalleSubscripcion}/{idNuevaSubscripcion}")
    public ResponseEntity<Detalle_Subscripcion> actualizarDetalleSubscripcion(
            @PathVariable Long idDetalleSubscripcion,
            @PathVariable Long idNuevaSubscripcion) {
        try {
            Detalle_Subscripcion detalleSubscripcionExistente = detalleSubscripcionRepository.findById(idDetalleSubscripcion)
                    .orElseThrow(() -> new ResourceNotFoundException("Detalle_Subscripcion no encontrado con el id: " + idDetalleSubscripcion));

            // Actualizar las fechas de inicio y fin automáticamente
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            detalleSubscripcionExistente.setFechaInicio(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 30); // Sumar 30 días
            detalleSubscripcionExistente.setFechaFin(calendar.getTime());

            // Obtener la nueva subscripción
            Subscripcion nuevaSubscripcion = subscripcionService.findById(idNuevaSubscripcion);
            detalleSubscripcionExistente.setSubscripcion(nuevaSubscripcion);

            // Guardar los cambios en el detalleSubscripcionExistente
            detalleSubscripcionRepository.save(detalleSubscripcionExistente);

            return new ResponseEntity<>(detalleSubscripcionExistente, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Detalle_Subscripcion> actualizar(@PathVariable Long id, @RequestBody Detalle_Subscripcion p) {
        Detalle_Subscripcion subscripcion = DetallesubscripcionService.findById(id);
        if (subscripcion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                subscripcion.setVendedor(p.getVendedor());
                return new ResponseEntity<>(DetallesubscripcionService.save(subscripcion), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    //limite de pulbicaciones
    //segun el paquete que tenga free gold premiun
    @GetMapping("/comprobarLimite")
    public ResponseEntity<mensajeAlertasDto> limitPost(Authentication aut) {
        UserDetails uDet = (UserDetails) aut.getPrincipal();
        return DetallesubscripcionService.dataSuscripUser(uDet.getUsername());
       
    }
    
    //limite de publicaciones activas 
    //segun en paquete que tenga 
    @GetMapping("/comprobarPubAct")
    public ResponseEntity<mensajeAlertasDto>limitActiPost(Authentication aut){
        UserDetails uDet = (UserDetails) aut.getPrincipal();
        return DetallesubscripcionService.limitPubAct(uDet.getUsername());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Detalle_Subscripcion> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<Detalle_Subscripcion>(DetallesubscripcionService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/extraerMembresia/{vendedorId}")
    public ResponseEntity<Detalle_Subscripcion> obtenerDetallePorVendedorId(@PathVariable Long vendedorId) {
        Detalle_Subscripcion detalleSubscripcion = DetallesubscripcionService.obtenerDetalleSubscripcionPorVendedorId(vendedorId);

        if (detalleSubscripcion != null) {
            return ResponseEntity.ok(detalleSubscripcion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
