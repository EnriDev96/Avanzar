package com.Proyecto.Avanzar.Services.implement;

import com.Proyecto.Avanzar.Models.Detalle_Subscripcion;
import com.Proyecto.Avanzar.Models.dto.mensajeAlertasDto;
import com.Proyecto.Avanzar.Repository.Detalle_SubscripcionRepository;
import com.Proyecto.Avanzar.Services.service.Detalle_SubscripcionService;
import com.Proyecto.Avanzar.Services.service.PublicacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class Detalle_SubcripcionService extends GenericServiceImpl<Detalle_Subscripcion, Long> implements Detalle_SubscripcionService {

    @Autowired
    private Detalle_SubscripcionRepository repository;

    @Autowired
    PublicacionesService servPub;

    @Override
    public CrudRepository<Detalle_Subscripcion, Long> getDao() {
        return repository;
    }

    //limite de publicaciones
    @Override
    public ResponseEntity<mensajeAlertasDto> dataSuscripUser(String username) {
        mensajeAlertasDto mensAlert = new mensajeAlertasDto();

        Detalle_Subscripcion subscripcion = repository.dataSuscripUser(username);

        if (subscripcion == null) {

            mensAlert.setBanderaBol(false);
            mensAlert.setTitle("Alerta");
            mensAlert.setBody("No tiene una Suscripción asignada");
            return new ResponseEntity<>(mensAlert, HttpStatus.OK);

        } else {

            switch (Integer.parseInt(subscripcion.getSubscripcion().getIdSubscripcion() + "")) {
                case 1 -> {
                    if (servPub.countPubli(subscripcion.getVendedor().getIdVendedor()) >= 12) {
                        mensAlert.setBanderaBol(false);
                        mensAlert.setTitle("Usuario");
                        mensAlert.setBody("Solo puede crear 12 publicaciones");

                    } else {
                        //se crea
                        mensAlert.setBanderaBol(true);

                    }
                }

                case 2 -> {
                    if (servPub.countPubli(subscripcion.getVendedor().getIdVendedor()) >= 12) {
                        mensAlert.setBanderaBol(false);
                        mensAlert.setTitle("Usuario");
                        mensAlert.setBody("Solo puede crear 12 publicaciones");
                    } else {
                        mensAlert.setBanderaBol(true);
                    }
                }

                case 3 -> {
                    if (servPub.countPubli(subscripcion.getVendedor().getIdVendedor()) >= 12) {
                        mensAlert.setBanderaBol(false);
                        mensAlert.setTitle("Usuario");
                        mensAlert.setBody("Solo puede crear 12 publicaciones");
                    } else {
                        mensAlert.setBanderaBol(true);
                    }
                }
            }

            return new ResponseEntity<>(mensAlert, HttpStatus.OK);

        }

    }

    @Override
    public ResponseEntity<mensajeAlertasDto> limitPubAct(String username) {
        mensajeAlertasDto mensAlert = new mensajeAlertasDto();

        Detalle_Subscripcion subscripcion = repository.dataSuscripUser(username);

        if (subscripcion == null) {

            mensAlert.setBanderaBol(false);
            mensAlert.setTitle("Alerta");
            mensAlert.setBody("No tiene una Suscripción asignada");
            return new ResponseEntity<>(mensAlert, HttpStatus.OK);

        } else {

            if (subscripcion.getSubscripcion().getNumPublicaciones() <= servPub.countPubliEstatus(subscripcion.getVendedor().getIdVendedor())) {
                switch (subscripcion.getSubscripcion().getNumPublicaciones()) {
                    case 3 -> {
                        mensAlert.setBanderaBol(false);
                        mensAlert.setTitle("Suscripción FREE");
                        mensAlert.setBody("Solo puede activar 3 publicaciones,<br> actualize la suscripción para activar más.");
                    }
                    case 6 -> {
                        mensAlert.setBanderaBol(false);
                        mensAlert.setTitle("Suscripción GOLD");
                        mensAlert.setBody("Solo puede activar 7 publicaciones,<br> actualize la suscripción para activar más.");
                    }
                    case 10 -> {
                        mensAlert.setBanderaBol(false);
                        mensAlert.setTitle("Suscripción PREMIUM");
                        mensAlert.setBody("Solo puede activar 10 publicaciones.");
                    }
                }

            } else {

                mensAlert.setBanderaBol(true);
            }

            return new ResponseEntity<>(mensAlert, HttpStatus.OK);

        }

    }

    @Override
    public Detalle_Subscripcion obtenerDetalleSubscripcionPorVendedorId(Long vendedorId) {
        return repository.obtenerDetalleSubscripcionPorVendedorId(vendedorId);
    }

}
