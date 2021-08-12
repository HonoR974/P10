package com.batch.service;

import com.batch.model.ReservationDTO;
import com.batch.model.UserEmail;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class ReservationServiceImpl implements ReservationService{

    private final HttpClient client =  HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();


    @Autowired
    private SecurityService securityService;

    private String jwt;

    @Override
    public String test() {

        System.out.println("\n test ");
        return "test";
    }

    @Override
    public List<ReservationDTO> getFirstReserv() throws IOException, InterruptedException, MessagingException {
        List<ReservationDTO> list = new ArrayList<>();

        this.jwt = securityService.authticate();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/batch/reservation/firstAll"))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE,"application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response : " +  response + "\n reponse : " + reponse);

        ObjectMapper mapper = new ObjectMapper();

        list = mapper.readValue(response.body().toString(), new TypeReference<List<ReservationDTO>>() {});



        return checkFirstReserv(list);

    }

    private List<ReservationDTO> checkFirstReserv(List<ReservationDTO> list) throws MessagingException {
        List<ReservationDTO> listFinal = new ArrayList<>();

        for (ReservationDTO reservationDTO : list)
        {
            if (!reservationDTO.isSendMail())
            {
                sendMail(reservationDTO);
                reservationDTO.setSendMail(true);
            }

            listFinal.add(reservationDTO);
        }
        return listFinal;
    }

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    private void sendMail(ReservationDTO reservationDTO) throws MessagingException {
        /**
        System.out.println("l'envoie du mail ");
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(reservationDTO.getMail());
        msg.setFrom("damien.dorval1@gmail.com");
        msg.setSubject("test");
        msg.setText("Le premier mail ");
        System.out.println("\n msg " + msg.toString());
        javaMailSender.send(msg);
**/

        //sans corps html
        /*
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setTo(reservationDTO.getMail());
                helper.setSubject("deuxieme test ");
                helper.setText("htmlBody de qualité !!!", true);
                helper.setFrom("damien.dorval1@gmail.com");

                System.out.println("\n message " + message.toString());

                System.out.println("\n helper " + helper.toString());

                javaMailSender.send(message);
        */

        //avec un corps html dans un string
        /*
                javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
                helper.setSubject("Welcome " + reservationDTO.getUsername());


                //le corps de l'email
                String html = "<!doctype html>\n" +
                        "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
                        "      xmlns:th=\"http://www.thymeleaf.org\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta name=\"viewport\"\n" +
                        "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                        "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                        "    <title>Email</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<div>Welcome <b>" + reservationDTO.getUsername() + "</b></div>\n" +
                        "\n" +
                        "<div> Your Book is ready : <b>" + reservationDTO.getTitre() + "</b></div>\n" +
                        "</body>\n" +
                        "</html>\n";



                helper.setText(html, true);
                helper.setTo("honore.guillaudeau1@gmail.com");

                System.out.println("\n helper " + helper.toString());

                System.out.println("\n mail destinataire " + reservationDTO.getMail());
                javaMailSender.send(mimeMessage);

                System.out.println("\n mail envoyé ");

        */


        //avec thymeleaf

        System.out.println("\n debut ");
        UserEmail user = new UserEmail();
        Context context = new Context();

        user.setUsername(reservationDTO.getUsername());
        user.setLivre(reservationDTO.getTitre());
        context.setVariable("user", user);

        System.out.println("\n context fait  ");


        String process = templateEngine.process("welcome", context);
        System.out.println("\n process fait  ");

        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom("damien.dorval1@gmail.com");
        helper.setSubject("Welcome " + reservationDTO.getUsername());
        helper.setText(process, true);
        helper.setTo(reservationDTO.getMail());
        javaMailSender.send(mimeMessage);

    }



}

