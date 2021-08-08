package com.batch.service;

import com.batch.model.PretDTO;
import com.batch.model.ReservationDTO;
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
import java.util.ArrayList;
import java.util.List;

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
    public List<ReservationDTO> getFirstReserv() throws IOException, InterruptedException {
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

    private List<ReservationDTO> checkFirstReserv(List<ReservationDTO> list)
    {
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

    private void sendMail(ReservationDTO reservationDTO)
    {
        
    }





}

