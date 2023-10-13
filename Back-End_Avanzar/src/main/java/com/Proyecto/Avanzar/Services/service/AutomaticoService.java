/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Proyecto.Avanzar.Services.service;

import com.Proyecto.Avanzar.Models.dto.EmailDto;
import com.Proyecto.Avanzar.Repository.PersonaRepository;
import com.Proyecto.Avanzar.Repository.PublicacionesRepository;
import com.Proyecto.Avanzar.Repository.VendedorRepository;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author gt618
 */
@Service
public class AutomaticoService {

    @Autowired
    private JavaMailSender mail;

    @Autowired
    private PersonaRepository repoPer;

    @Autowired
    private PublicacionesRepository repoPubli;

    @Autowired
    private VendedorRepository repoVen;

    @Scheduled(cron = "0 0 17 * * *")
    public void notificarSubscripCaducar() {
        List<String> correos = repoPer.obtenerCorreosNotificacionCadu();
        EmailDto e = new EmailDto();

        if (!e.sendAlertSuscrip()) {
            System.out.println("error al cargar html notificar");
        }

        for (String correo : correos) {

            MimeMessage basEmail = mail.createMimeMessage();
            try {

                MimeMessageHelper contEmail = new MimeMessageHelper(basEmail, true, "utf-8");
                contEmail.setSubject("Renovacion de suscripción");
                contEmail.setFrom("FundacionAvanzarW@gmail.com");
                contEmail.setTo(correo);
                contEmail.setText(e.getText(), true);

                mail.send(basEmail);

            } catch (MessagingException ex) {
                System.out.println("Error al enviar email renovación suscripción");
            }
        }
    }

    @Scheduled(cron = "0 0 23 * * *")
    public void reinciarSupcripción() {
        //vendedores que caduco su suscripcion
        //ver los id vendedores donde la fecha limite coincide con el currentDate
        //verificara hasta 3 dias despues de la caducidad por seguridad
        List<Long> listIdVenLimite = repoVen.listarUsuariosSuscripVencida();

        if (!listIdVenLimite.isEmpty()) {
            //buscar los id vendedor donde tenga mas 
            List<Long> listIdvenPublicExced = repoPubli.listarIdVenDesacPublicFree(listIdVenLimite);
            for (Long id : listIdvenPublicExced) {
                //desactivar y dejar solo 3 publicaciones activas
                List<Long> listIdPublicDesac = repoPubli.listarIdPublicDesac(id);

                if (listIdPublicDesac.size() > 3) {
                    // Eliminar los primeros 3 elementos
           
                    for (int i = 0; i < 3; i++) {
                        listIdPublicDesac.remove(0);
                    }
                        
                   repoPubli.updateStateSuscripCaducado(listIdPublicDesac);
                }

            }
        }

    }

}
