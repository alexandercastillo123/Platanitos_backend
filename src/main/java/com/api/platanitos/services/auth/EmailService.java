package com.api.platanitos.services.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.api.platanitos.models.Usuario;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username")
    private String remitente;

    @Async
    public void enviarTokenVerificacion(String correoDestino, String token, Usuario usuario){
        String asunto = "Verificar cuenta Platanitos";
        String urlFrontend = "http://localhost:3000";
        String nombreCompleto = usuario.getNombres() + " " + usuario.getApellidos();
        String contenidoHTML = """
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; border: 1px solid #eee; padding: 20px;">
                    <h2 style="color: #00529C; text-align: center;">Platanitos</h2>
                    <p>Hola, <strong>%s</strong>:</p>
                    <p>Para culminar la creacion de su cuenta y validar su autenticidad, por favor haga clic en el siguiente boton:</p>
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="%s/verificar-usuario?id=%s&token=%s" 
                        style="font-size: 18px; font-weight: bold; background-color: #00529C; padding: 14px 30px; border-radius: 5px; color: #ffffff; display: inline-block; text-decoration: none;">
                            Verificar Mi Cuenta
                        </a>
                    </div>
                    <p>Saludos.</p>
                    <hr style="border: none; border-top: 1px solid #eee; margin-top: 20px;" />
                    <p style="color: #777; font-size: 12px;">Este enlace expirara en 15 minutos. Si tu no solicitaste este registro, puedes ignorar este mensaje de forma segura.</p>
                </div>
            """.formatted(nombreCompleto, urlFrontend, usuario.getId(), token);
        enviarCorreo(correoDestino, asunto, contenidoHTML);
    }

    public void enviarCorreo(String correoDestino, String asunto, String contenidoHTML){
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(remitente);
            helper.setTo(correoDestino);
            helper.setSubject(asunto);
            helper.setText(contenidoHTML, true);
            mailSender.send(message);
        } catch(MessagingException e){
            throw new RuntimeException("Error al enviar el correo a: " + correoDestino, e);
        }
    }
}
