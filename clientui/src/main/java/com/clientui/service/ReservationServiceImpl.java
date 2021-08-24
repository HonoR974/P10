package com.clientui.service;

import com.clientui.dto.ExamplaireDTO;
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

        System.out.println("\n getReservByBook");

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

        List<ReservationDTO> list = mapper.readValue(response.body().toString(),
                new TypeReference<List<ReservationDTO>>() {});
        return list;
    }

}
