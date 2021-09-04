package com.clientui.service;

import com.clientui.dto.ExamplaireDTO;
import com.clientui.dto.LivreDTO;
import com.clientui.dto.ReservationDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService{

    private final HttpClient client =  HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    private String jwt;

    @Autowired
    private AuthBiblioService authBiblioService;

    @Override
    public List<ReservationDTO> getReservByBook(Long id) throws IOException, InterruptedException {


        this.jwt = authBiblioService.getJwt();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/reservation/get/livre/" + id))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response " + response + "\n reponse " + reponse);


        ObjectMapper mapper= new ObjectMapper();

        return mapper.readValue(response.body().toString(),
                new TypeReference<List<ReservationDTO>>() {});
    }

    @Override
    public String createReserv(Long id_livre) throws IOException, InterruptedException {
        System.out.println("\n jwt " + jwt);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/reservation/create/" + id_livre))
               .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response " + response + "\n reponse " + reponse);


        return reponse;
    }

    @Override
    public List<ReservationDTO> getReserByUser() throws IOException, InterruptedException {

        this.jwt = authBiblioService.getJwt();
        System.out.println("\n jwt : " + jwt );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/reservation/get/user" ))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response " + response + "\n reponse " + reponse);

        ObjectMapper mapper= new ObjectMapper();

        return mapper.readValue(response.body().toString(),
                new TypeReference<List<ReservationDTO>>() {});
    }

    @Override
    public ReservationDTO getReservById(long id_reserv) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/reservation/get/" + id_reserv))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response " + response + "\n reponse " + reponse);

        ObjectMapper mapper= new ObjectMapper();

        return  mapper.readValue(response.body().toString(),
                new TypeReference<ReservationDTO>() {});
    }

    @Override
    public String cancelReservation(long id_reserv) throws IOException, InterruptedException {

        this.jwt = authBiblioService.getJwt();
        String message = "\n le cancel reserv : ";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/reservation/cancel/" + id_reserv))
                .DELETE()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response " + response + "\n reponse " + reponse);

        message += reponse;
        return message;
    }

    @Override
    public ExamplaireDTO finishReserv(long id_reserv) throws IOException, InterruptedException {



        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/reservation/finish/" + id_reserv))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response " + response + "\n reponse " + reponse);

        ObjectMapper mapper= new ObjectMapper();

        return mapper.readValue(response.body().toString(),
                new TypeReference<ExamplaireDTO>() {});
    }



}
