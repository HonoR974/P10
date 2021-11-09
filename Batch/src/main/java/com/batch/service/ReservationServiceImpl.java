package com.batch.service;

import com.batch.model.ReservationDTO;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.mail.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.*;

import javax.mail.MessagingException;

@Service
public class ReservationServiceImpl implements ReservationService{

    private final HttpClient client =  HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();


    @Autowired
    private SecurityService securityService;

  
    private String templateID;


    private String sgID;


    private String from;

    private String jwt;

    private List<ReservationDTO> listReservation;

    
    @Autowired
    public JavaMailSender emailSender;


    //recupere toutes les reservations
    // qui sont premieres de leurs
    //liste d'attente
    @Override
    public List<ReservationDTO> getFirstReserv() throws IOException, InterruptedException, MessagingException {
        List<ReservationDTO> list = new ArrayList<>();

        this.jwt = securityService.authticate();


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/batch/reservation/firstNoSendMail"))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE,"application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response : " +  response + "\n reponse : " + reponse);

        //il n'y a pas de reservation envoyé 
        if(response.statusCode() != 202 )
        {
            return null;
        }
        else
        {
            ObjectMapper mapper = new ObjectMapper();

            list = mapper.readValue(response.body().toString(), new TypeReference<List<ReservationDTO>>() {});
    
            return checkFirstReserv(list);
        }

       

    }
    
    //verfication si un mail leurs a été envoyé
    //un mail par reserv
    private List<ReservationDTO> checkFirstReserv(List<ReservationDTO> list) throws MessagingException, IOException {
        List<ReservationDTO> listFinal = new ArrayList<>();

        for (ReservationDTO reservationDTO : list)
        {
            System.out.println("\n ------ verfication ---------- ");
            System.out.println("\n user : " + reservationDTO.getUsername());
            if (!reservationDTO.isSendMail())
            {
                //envoie de l'eamil
                sendMail(reservationDTO);
                reservationDTO.setSendMail(true);

                //ajout des dates de la reservation
                reservationDTO.setDate_debut(putNewDate().getDate_debut());
                reservationDTO.setDate_fin(putNewDate().getDate_fin());

            }


            System.out.println("\n ------ Fin verfication ---------- ");
            listFinal.add(reservationDTO);
        }

        listReservation = listFinal;

        return listFinal;
    }

    //envoie du mail
    @Override
    public String sendMail(ReservationDTO reservationDTO) throws MessagingException, IOException {

          // Create a Simple MailMessage.
          SimpleMailMessage message = new SimpleMailMessage();
        
          message.setTo(reservationDTO.getMail());
          message.setSubject("Votre Livre " + reservationDTO.getTitre() + " est disponible ");
          message.setText("Bonjour " + reservationDTO.getUsername() + ", votre attente est fini," + "\n "+ reservationDTO.getTitre() + " n'attend plus que vous. ");
  
          // Send Message!
          emailSender.send(message);
   
        return " mail envoyé  ";
    }



    //les dates de la reservation
    private ReservationDTO putNewDate()
    {
        //la date d'aujourd'hui
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date dateBegin = new Date();
        System.out.println("\n date de mnt : " + dateFormat.format(dateBegin));

        //Ajout du temps ( 48 h )
        Date dateEnd = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateBegin);
        calendar.add(Calendar.DATE,2);
        dateEnd  = calendar.getTime();
        System.out.println("\n date de fin de la reservation : "  + dateEnd);

        //convertion en string pour le dto
        String dateD = dateFormat.format(dateBegin);
        String dateF = dateFormat.format(dateEnd);
        System.out.println("\n la date de debut est " + dateD );
        System.out.println("\n la date de fin est " + dateF);

        ReservationDTO reservationDTO1 = new ReservationDTO();
        reservationDTO1.setDate_debut(dateD);
        reservationDTO1.setDate_fin(dateF);

        return reservationDTO1;
    }

    //envoie une liste recu
    @Override
    public void sendListReservation() throws IOException, InterruptedException {

        System.out.println("\n liste enregistré " + listReservation);
 
        if(listReservation != null )
        {
            HashMap<Integer,ReservationDTO> param = new HashMap<>();
            int i = 0;
            for (ReservationDTO reservationDTO : listReservation)
            {
                param.put(i, reservationDTO);
                i++;
            }
            var objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(param);
    
            System.out.println("\n les params a save " + requestBody);
    
    
    
            this.jwt = securityService.authticate();
            System.out.println("\n jwt send list " + jwt);
    
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:9001/api/batch/reservation/save"))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .setHeader(HttpHeaders.CONTENT_TYPE,"application/json")
                    .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                    .build();
    
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
    
            String reponse = response.body();
    
            System.out.println("\n response : " +  response + "\n reponse : " + reponse);
    
        }


       

    }


    public void checkDelai() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/batch/reservation/checkDelai"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .setHeader(HttpHeaders.CONTENT_TYPE,"application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response : " +  response + "\n reponse : " + reponse);


    }

    @Override
    public void checkListReservForStatut() throws IOException, InterruptedException {


        System.out.println("\n jwt checkListForStatut " + jwt);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/batch/reservation/checkListeReserv"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .setHeader(HttpHeaders.CONTENT_TYPE,"application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response : " +  response + "\n reponse : " + reponse);


    }




}

