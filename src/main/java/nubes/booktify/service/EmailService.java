package nubes.booktify.service;

import java.io.IOException;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import nubes.booktify.model.TypeUser;
import nubes.booktify.model.User;
import nubes.booktify.model.request.UserRequest;

@Service
public class EmailService {
    
    @Value("${app.sendgrid.key}")
    private String sendgridKey;

    @Value("${email.sendgrid}")
    private String sendgridEmail;

    public void WelcomeEmail(String email, User user) throws IOException {
        String mensaje = "Bienvenido a la aplicación Booktify, " + user.getEmail();
        sendEMail(email, "Registro de usuario", mensaje);
    }

    public void loginAlert(String email, String userAgent) throws IOException {
        String mensaje = "Se ha iniciado sesión en el dispositivo " + userAgent;
        sendEMail(email, "Alerta de incio de sesión", mensaje);
    }

    public void changeUserTypeAlert(String email, TypeUser type) throws IOException{
        String mensaje = "Se ha cambiado su tipo de usuario a: " + type.getType().toString();
        sendEMail(email, "Cambio en tipo de usuario", mensaje);
    }

    public void deletedUserAlert(String email, User user) throws IOException{
        String mensaje = "Se ha eliminado su cuenta asociada a " + user.getEmail() + " de la aplicación Booftify";
        sendEMail(email, "Se ha eliminado su cuenta de Booktify", mensaje);
    }

    @Async
    public void editAlert(String email, User user, UserRequest editedUser) throws IOException {
        String mensaje = "Se ha realizado un cambio en su cuenta " + user.getEmail() + "\nde"
                + "\nNombre: " + user.getFullname() + "\nApellidos: " + user.getLastname() + "\nEmail: "
                + user.getEmail() + "\na" + "\nNombre: " + editedUser.getFullname() + "\nApellidos: "
                + editedUser.getLastname() + "\nEmail: " + editedUser.getEmail();

        sendEMail(email, "Actualización de Datos", mensaje);
    }

    @Async
    public void sendEMail(String email, String subject, String mensaje) throws IOException {
        Mail mail = prepareMail(email, subject, mensaje);

        SendGrid sg = new SendGrid(sendgridKey);

        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());

        } catch (IOException ex) {
            throw ex;
        }
    }

    private Mail prepareMail(String email, String subject, String mensaje) {
        Email from = new Email(sendgridEmail);
        Content content = new Content("text/plain", mensaje);
        Email to = new Email(email);
        Mail mail = new Mail(from, subject, to, content);
        return mail;
    }
}
