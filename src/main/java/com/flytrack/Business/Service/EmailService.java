package com.flytrack.Business.Service;

import com.flytrack.Persistence.Model.Reserva;
import com.flytrack.Persistence.Model.Vuelo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import sibApi.TransactionalEmailsApi;
import sendinblue.Configuration;
import sibModel.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${app.mail.from}")
    private String from;

    private String loadTemplate(String name) {
        try {
            ClassPathResource resource = new ClassPathResource("email/" + name);
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error cargando plantilla", e);
        }
    }

    public void enviarCodigoVerificacion(String to, String codigo) {

        Configuration.getDefaultApiClient().setApiKey(apiKey);
        TransactionalEmailsApi api = new TransactionalEmailsApi();

        SendSmtpEmailSender sender = new SendSmtpEmailSender();
        sender.setEmail(from);
        sender.setName("FlyTrack ✈️");

        String template = loadTemplate("verificacion.html");

        String html = template
                .replace("{{CODE}}", codigo)
                .replace("{{TIME}}", "5 minutos")
                .replace("{{YEAR}}", String.valueOf(LocalDateTime.now().getYear()));

        SendSmtpEmail email = new SendSmtpEmail();
        email.setSender(sender);
        email.setTo(Collections.singletonList(
                new SendSmtpEmailTo().email(to)
        ));
        email.setSubject("Código de verificación - FlyTrack");
        email.setHtmlContent(html);

        try {
            api.sendTransacEmail(email);
            System.out.println("[MAIL] Código enviado a " + to);
        } catch (Exception e) {
            System.err.println("[MAIL] Error enviando correo: " + e.getMessage());
        }
    }

    public void enviarNotificacionVuelo(
            String to,
            Vuelo vuelo,
            Reserva reserva,
            boolean cambioHora,
            boolean cambioAerolinea,
            boolean cambioPuerta,
            boolean cambioEstado
    ) {

        Configuration.getDefaultApiClient().setApiKey(apiKey);
        TransactionalEmailsApi api = new TransactionalEmailsApi();

        SendSmtpEmailSender sender = new SendSmtpEmailSender();
        sender.setEmail(from);
        sender.setName("FlyTrack ✈️");

        String template = loadTemplate("notificacionVuelo.html");

        StringBuilder cambios = new StringBuilder();

        if (cambioPuerta) {
            cambios.append("<p>🛫 Nueva puerta de embarque: <strong>")
                    .append(vuelo.getPuertaEmbarque())
                    .append("</strong></p>");
        }

        if (cambioEstado) {
            cambios.append("<p>📢 Estado: <strong>")
                    .append(vuelo.getEstado())
                    .append("</strong></p>");
        }

        if (cambioHora) {
            cambios.append("<p>⏰ Nueva hora de salida: <strong>")
                    .append(vuelo.getHoraSalida())
                    .append("</strong></p>");
        }

        if (cambioAerolinea) {
            cambios.append("<p>✈️ Se ha reasignado de aerolínea: <strong>")
                    .append(vuelo.getAerolinea())
                    .append("</strong></p>");
        }


        String html = template
                .replace("{{CAMBIOS}}", cambios.toString())
                .replace("{{RESERVA}}", reserva.getCodigoReserva())
                .replace("{{YEAR}}", String.valueOf(LocalDateTime.now().getYear()));

        SendSmtpEmail email = new SendSmtpEmail();
        email.setSender(sender);
        email.setTo(Collections.singletonList(
                new SendSmtpEmailTo().email(to)
        ));
        email.setSubject("✈️ Cambios en tu vuelo - FlyTrack");
        email.setHtmlContent(html);

        try {
            api.sendTransacEmail(email);
            System.out.println("[MAIL] Notificación enviada a " + to);
        } catch (Exception e) {
            System.err.println("[MAIL] Error enviando correo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}