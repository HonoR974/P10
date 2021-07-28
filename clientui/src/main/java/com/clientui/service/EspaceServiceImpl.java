package com.clientui.service;

import com.clientui.dto.PretDTO;
import com.clientui.dto.UserDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * Service EspaceServiceImpl
 */
@Service
public class EspaceServiceImpl implements EspaceService
{

    private AuthBiblioService authBiblioService;

    private String jwt;


    private final HttpClient client =  HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();


    public EspaceServiceImpl(AuthBiblioService authBiblioServiceSend)
    {
        this.authBiblioService = authBiblioServiceSend;
    }

    /**
     * Recupere un userdto par l'id e
     * @param id_user
     * @return userdto
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public UserDTO getUserDTOByID(Long id_user) throws IOException, InterruptedException {
        this.jwt = authBiblioService.getJwt();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/espace/" + id_user))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE,"application/json")
                .setHeader(HttpHeaders.AUTHORIZATION,"Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        String reponse = response.body();

        System.out.println("\n response " + response + "\n reponse  :  " + reponse);

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(response.body().toString(), new TypeReference<UserDTO>() {});

    }

    /**
     * Recupere un eliste de pret par l'id user
     * @param id_user
     * @return liste pret
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public List<PretDTO> getListePretByIdUser(Long id_user) throws IOException, InterruptedException {
        this.jwt = authBiblioService.getJwt();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/espace/prets/" + id_user))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();


        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response : " + response + "\n reponse : " + reponse );

        ObjectMapper mapper= new ObjectMapper();

        return  mapper.readValue(response.body().toString(),
                new TypeReference<List<PretDTO>>() {});
    }

    /**
     * Recupere le pretdto par l'id
     * @param id_pret
     * @return pret dto
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public PretDTO getPretDTOByIdPret(Long id_pret) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/espace/pret/" + id_pret))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE,"application/json")
                .setHeader(HttpHeaders.AUTHORIZATION,"Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response : " + response + "\n reponse : " + reponse);


        ObjectMapper mapper= new ObjectMapper();

        return  mapper.readValue(response.body().toString(),
                new TypeReference<PretDTO>() {});
    }


}
