package com.clientui.service;

import com.clientui.dto.BibliothequeDTO;
import com.clientui.dto.LivreDTO;
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


@Service
public class BibliothequeServiceImpl implements BibliothequeService{

    private String jwt;

    private AuthBiblioService authBiblioService;


    private final HttpClient client =  HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    /**
     * Recupere toutes les bibliotheque
     * @param jwtSend
     * @return liste bibliotheque
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public List<BibliothequeDTO> getAllBibliotheque(String jwtSend ) throws IOException, InterruptedException {
        this.jwt = jwtSend;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/bibliotheque"))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwtSend)
                .build();

        HttpResponse<String> response = client.send(request,
                            HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        ObjectMapper mapper = new ObjectMapper();

        List<BibliothequeDTO> list = mapper.readValue(response.body().toString(),
                new TypeReference<List<BibliothequeDTO>>(){});

        return list;
    }

    /**
     * Recupere touts les livres d'une bibliotheque
     * @param id
     * @return liste livres
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public List<LivreDTO> getAllLivreByIdBiblio(Long id) throws IOException, InterruptedException {


        HttpRequest request = HttpRequest.newBuilder()
                      .uri(URI.create("http://localhost:9001/api/bibliotheque/Livres?id=" + id))
                      .GET()
                        .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                        .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                      .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString() );

        String reponse = response.body();

        System.out.println("\n response " + response + "\n reponse " + reponse);
        ObjectMapper mapper= new ObjectMapper();

        List<LivreDTO> list = mapper.readValue(response.body().toString(),
                new TypeReference<List<LivreDTO>>() {});

        return list;
    }

    /**
     * Recupere le jwt
     * @return jwt
     */
    @Override
    public String getJwt()
    {
        return jwt;
    }


}