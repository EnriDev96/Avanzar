package com.Proyecto.Avanzar.Models;

import java.util.Date;

public class TiempoTranscurridoUtil {

    public static String calcularTiempoTranscurridoFormateado(Date fechaPublicacion) {
        Date ahora = new Date(); // Fecha y hora actual

        long diferenciaEnMilisegundos = ahora.getTime() - fechaPublicacion.getTime();
        long segundosTranscurridos = diferenciaEnMilisegundos / 1000;
        long minutosTranscurridos = segundosTranscurridos / 60;
        long horasTranscurridas = minutosTranscurridos / 60;
        long diasTranscurridos = horasTranscurridas / 24;
        long semanasTranscurridas = diasTranscurridos / 7;
        long mesesTranscurridos = semanasTranscurridas / 4;
        long añosTranscurridos = mesesTranscurridos / 12;

        if (añosTranscurridos > 0) {
            return "Hace " + añosTranscurridos + (añosTranscurridos == 1 ? " año" : " años");
        } else if (mesesTranscurridos > 0) {
            return "Hace " + mesesTranscurridos + (mesesTranscurridos == 1 ? " mes" : " meses");
        } else if (semanasTranscurridas > 0) {
            return "Hace " + semanasTranscurridas + (semanasTranscurridas == 1 ? " semana" : " semanas");
        } else if (diasTranscurridos > 0) {
            return "Hace " + diasTranscurridos + (diasTranscurridos == 1 ? " día" : " días");
        } else if (horasTranscurridas > 0) {
            return "Hace " + horasTranscurridas + (horasTranscurridas == 1 ? " hora" : " horas");
        } else if (minutosTranscurridos > 0) {
            return "Hace " + minutosTranscurridos + (minutosTranscurridos == 1 ? " minuto" : " minutos");
        } else {
            return "Hace menos de 1 minuto";
        }
    }
}
