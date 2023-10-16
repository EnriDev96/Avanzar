/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Proyecto.Avanzar.Controllers;

import com.Proyecto.Avanzar.Models.dto.EmailDto;
import com.Proyecto.Avanzar.Services.service.PersonaService;
import com.Proyecto.Avanzar.Services.service.UsuarioService;

import java.security.SecureRandom;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://164.90.153.70:4200"})
@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private JavaMailSender mail;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PersonaService persoService;

    @PostMapping("/sendCodeVerification")
    public ResponseEntity<EmailDto> enviarCorreo(@RequestBody EmailDto e) {

        MimeMessage basEmail = mail.createMimeMessage();
        String code = "";
        try {

            MimeMessageHelper contEmail = new MimeMessageHelper(basEmail, true, "utf-8");
            contEmail.setSubject(e.getSubject());
            contEmail.setFrom("FundacionAvanzarW@gmail.com");
            contEmail.setTo(e.getTo());
            code = ramdomCode(5, 5, "0123456789");
            if (!e.sendCodeVeri(code)) {
                return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            contEmail.setText(e.getText(), true);
            mail.send(basEmail);
            e.setText(code);
            return new ResponseEntity<>(e, HttpStatus.OK);
        } catch (MessagingException ex) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/resetPass")
    public ResponseEntity<Boolean> changePass(@Valid @RequestBody EmailDto e) {

        String code = "";

        if (persoService.findByCorreo(e.getTo()) != null) {
            
            code = ramdomCode(8, 8, "abcdefghijklmnopqrstuvwxyz0123456789");
            
            if (usuarioService.resetPass(e.getTo(), code) > 0) {
                try {

                    MimeMessage basEmail = mail.createMimeMessage();
                    MimeMessageHelper contEmail = new MimeMessageHelper(basEmail, true, "utf-8");
                    contEmail.setSubject("Cambio de contraseña");
                    contEmail.setFrom("FundacionAvanzarW@gmail.com");
                    contEmail.setTo(e.getTo());
                    if (!e.sendResetPass(code)) {
                        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                    
                    contEmail.setText(e.getText(), true);
                    mail.send(basEmail);
                    e.setText(code);
                    return new ResponseEntity<>(true, HttpStatus.OK);
                } catch (MessagingException ex) {
                    return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>(false,HttpStatus.SERVICE_UNAVAILABLE);
            }

        } else {
            return new ResponseEntity<>( false,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private String ramdomCode(int max, int min, String carac) {
        Random ran = new Random();

        int let = ran.nextInt((max - min) + 1) + min;
        //updateFechaPasByEmail(email.getTo(),new Date)
        //generar un numero aleatorio de 7 a 13 cifras
        SecureRandom sec = new SecureRandom();
        StringBuilder ramBui = new StringBuilder();

        for (int i = 0; i < let; i++) {
            int randomIndex = sec.nextInt(carac.length());
            char randomChar = carac.charAt(randomIndex);
            ramBui.append(randomChar);
        }
        return ramBui.toString();
    }
}
