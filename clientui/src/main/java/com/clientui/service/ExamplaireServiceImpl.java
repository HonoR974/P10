package com.clientui.service;

import com.clientui.dto.ExamplaireDTO;
import com.clientui.dto.LivreDTO;
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


/**
 * Service ExamplaireServiceImpl
 */
@Service
public class ExamplaireServiceImpl implements ExamplaireService
{
    private final HttpClient client =  HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();


    @Autowired
    private AuthBiblioService authBiblioService;

    private String jwt;


    /**
     * Recupere un exemplaire par l'id
     * @param id
     * @return exemplaire
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public ExamplaireDTO getExamplaire(Long id) throws IOException, InterruptedException {

        System.out.println("\n methode getExamplaire");

        this.jwt = authBiblioService.testConnection().getJwt();

        System.out.println("\n jwt  " + jwt);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/examplaire/"+ id))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,
                                    HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response " + response + "\n reponse " + reponse);

        ObjectMapper mapper= new ObjectMapper();

       ExamplaireDTO examplaireDTO = mapper.readValue(response.body().toString(),
                new TypeReference<ExamplaireDTO>() {});

        return examplaireDTO;
    }

    /**
     * Recupere un livre par l'id exemplaire
     * @param id id-exemplaire
     * @return livre
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public LivreDTO getLivreByIdExamplaire(Long id) throws IOException, InterruptedException {

        this.jwt = authBiblioService.getJwt();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/examplaire/livre/" + id))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE,"application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response " + response + "\n reponse " + reponse);

        ObjectMapper mapper= new ObjectMapper();

       LivreDTO livreDTO = mapper.readValue(response.body().toString(),
                new TypeReference<LivreDTO>() {});

        return livreDTO;
    }

    /**
     * Retourne le jwt
     * @return
     */
    @Override
    public String getJwt()
    {
        return jwt;
    }
}
