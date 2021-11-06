package com.batch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.batch.model.Email;
import com.batch.model.PretDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class PretServiceImpl implements PretService{

    private final HttpClient client =  HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    @Autowired
    private SecurityService securityService;

    private String jwt;

    private List<PretDTO> listPretRetard;

    private List<PretDTO> listePretEnCours;

    @Autowired
    public JavaMailSender emailSender;

    /**
     * Recupere les pret qui sont valider et non fini
     * @return liste pret dto
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public List<PretDTO> getPretEnCours() throws IOException, InterruptedException {

        this.jwt = securityService.authticate();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/batch/encours"))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE,"application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response : " +  response + "\n reponse : " + reponse);

        ObjectMapper mapper = new ObjectMapper();

        listePretEnCours = mapper.readValue(response.body().toString(), new TypeReference<List<PretDTO>>() {});

        return mapper.readValue(response.body().toString(), new TypeReference<List<PretDTO>>() {});

    }

    /** sendPret part-1
     * Une semaine avant la date de fin le statut du pret passe 'A Rendre'
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void sendPret() throws IOException, InterruptedException {

        if (listePretEnCours.isEmpty())
        {
            System.out.println("\n il n'y a pas de pret a envoyer ");
        }

        LocalDate date = LocalDate.now();
        LocalDate dateFin = null;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

        String statut = "A Rendre";

        List<PretDTO> listeAEnvoyer =  new ArrayList<>();
        for (PretDTO pretDTO : listePretEnCours)
        {

            dateFin = LocalDate.parse(pretDTO.getDate_fin(),formatter);

            System.out.println("\n la date de fin du pret " + dateFin);

            //si aujourd'hui est la date de fin du pret - 1 semaine
            //alors c'est le moment de le rendre
            System.out.println("\n la date d'aujourd'hui : "+ date.minusDays(7));

            System.out.println("si la date d'ajourd'hui est comprise dans les 7" +
                    "jours il faut le rendre ");
            if (date.isAfter(dateFin.minusDays(7)) || date.isEqual(dateFin.minusDays(7)))
            {
                System.out.println("\n faut le rendre ");
                pretDTO.setStatut(statut);
            }

            listeAEnvoyer.add(pretDTO);

        }

        sendList(listeAEnvoyer);

    }


    /**
     * sendPret part-1
     *
     * Envoie les prets a rendre
     *
     * @param list
     * @throws IOException
     * @throws InterruptedException
     */
    public void sendList(List<PretDTO> list) throws IOException, InterruptedException {

        System.out.println("\n on check le jwt " + jwt);

        //params a envoyer
        Map<Integer,PretDTO> parameters = new HashMap<>();

        int i = 0 ;
        for (PretDTO pretDTO : list)
        {
            parameters.put(i, pretDTO);
            i++;
        }

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(parameters);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/batch/encours"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody ))
                .setHeader(HttpHeaders.CONTENT_TYPE,"application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response : " +  response + "\n reponse : " + reponse);


    }

    /**
     * Les prets verifié sont récupérés
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public List<PretDTO> getPretRetard() throws IOException, InterruptedException {

        this.jwt = securityService.authticate();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/batch/retards"))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE,"application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        String reponse = response.body();

        System.out.println("\n response : " +  response + "\n reponse : " + reponse);


        ObjectMapper mapper = new ObjectMapper();

        listPretRetard = mapper.readValue(response.body().toString(), new TypeReference<List<PretDTO>>() {});

        return mapper.readValue(response.body().toString(), new TypeReference<List<PretDTO>>() {});
    }



    /**
     * Envoie les mails pour les pret à rendre
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void sendMailRetard() throws IOException, InterruptedException {

       // Email from = new Email(emailFrom);
        String subject = "Rappel : ";
           // Create a Simple MailMessage.
           SimpleMailMessage message = new SimpleMailMessage();


        for(int i = 0; i<= listPretRetard.size() -1 ; i++)
        {

            System.out.println("\n le pret avec l'id " + listPretRetard.get(i).getId() );

       
            //si le pret n'a pas recu d'email
            if( !listPretRetard.get(i).getEnvoieEmail() )
            {

               
        
                message.setTo(listPretRetard.get(i).getEmail());
                message.setSubject(subject + listPretRetard.get(i).getTitre());
                message.setText("Bonjour " + listPretRetard.get(i).getUsername()+"\n  la date de votre pret approche de sa fin ");
                

                // Send Message!
                emailSender.send(message);

                listPretRetard.get(i).setEnvoieEmail(true);
            }
            else
            {
                System.out.println("\n il a deja recu l'email ");
            }

            sendListPretRappel(listPretRetard);


        }
   

    }


    @Override
    public String sendMailPret(PretDTO pretDTO) {

        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setTo(pretDTO.getEmail());
        message.setSubject("Test Simple Email");
        message.setText("Hello, Im testing Simple Email");

        // Send Message!
        emailSender.send(message);
        return "Email Sent!";
    }


    /**
     * Renvoie tout les prets, Permet de ne recevoir qu'un mail par pret
     *
     *  fonction qui envoie les prets de sendMailRetard a l'api
     *     ceux qui ont recu un email n'en recevront plus
     * @param list
     */
    public void sendListPretRappel(List<PretDTO> list) throws IOException, InterruptedException {

        System.out.println("\n on check le jwt " + jwt);

        //params a envoyer
        Map<Integer,PretDTO> parameters = new HashMap<>();

        int i = 0 ;
        for (PretDTO pretDTO : list)
        {
            parameters.put(i, pretDTO);
            i++;
        }

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(parameters);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/batch/rappel"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody ))
                .setHeader(HttpHeaders.CONTENT_TYPE,"application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response : " +  response + "\n reponse : " + reponse);


    }




}
