package com.api.platanitos.services.send;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.annotation.PostConstruct;

@Service
public class SmsService {
    @Value("${twilio.account-sid}")
    private String accountSid;
    @Value("${twilio.auth-token}")
    private String authToken;
    @Value("${twilio.phone-number}")
    private String twilioPhoneNumber;

    @PostConstruct
    public void initTwilio(){
        Twilio.init(accountSid, authToken);
    }

    @Async
    public void enviarSmsVerificacion(String numeroDestino, String token, Long usuarioId) {
        try {
            // construimos la URL que el usuario visitara desde su celular (solo sera via pc 🥀)
            String linkVerificacion = "http://localhost:3000/verificar-usuario?id=" + usuarioId + "&token=" + token;

            String cuerpoMensaje = String.format(
                "Platanitos: Para verificar tu cuenta de celular, ingresa al siguiente enlace: %s",
                linkVerificacion
            );

            // Creamos el mensaje y enviamos
            Message.creator(
                    new PhoneNumber(numeroDestino),     // Receptor
                    new PhoneNumber(twilioPhoneNumber), // Remitente
                    cuerpoMensaje                       // El texto del SMS
            ).create();
            System.out.println("SMS de verificación enviado con éxito a: " + numeroDestino);
        } catch (Exception e) {
            // Manejo de error
            System.err.println("Error al enviar el SMS de Twilio: " + e.getMessage());
        }
    }
}
