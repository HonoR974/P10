package com.clientui.service;

import com.clientui.dto.ExamplaireDTO;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Service LivreServiceImpl
 */
@Service
public class LivreServiceImpl implements  LivreService{

    private final HttpClient client =  HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    private String jwt;

    private BibliothequeService bibliothequeService;

    public LivreServiceImpl(BibliothequeService bibliothequeService) {
        this.bibliothequeService = bibliothequeService;
    }

    /**
     * Recupere touts les livres
     * @return liste livre
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public List<LivreDTO> getAll() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:9001/api/livre"))
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();


        ObjectMapper mapper = new ObjectMapper();

       HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

       String reponse = httpResponse.body();

       System.out.println("\n response " + httpResponse + "\n reponse " + reponse);



        List<LivreDTO> list = mapper.readValue(httpResponse.body().toString(), new TypeReference<List<LivreDTO>>(){});


        return list;
    }

    /**
     * Recupere touts les exemplaire par l'id livre
     * @param id id-livre
     * @return liste exemplaire
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public List<ExamplaireDTO> getAllExamplaireByIdLivre(Long id) throws IOException, InterruptedException {



        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/livre/examplaires?id=" + id))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response " + response + "\n reponse " + reponse);


        ObjectMapper mapper= new ObjectMapper();

        List<ExamplaireDTO> list = mapper.readValue(response.body().toString(),
                new TypeReference<List<ExamplaireDTO>>() {});
        return list;
    }


    /**
     * Recupere un livre par l'id
     * @param id
     * @return livre
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public LivreDTO getLivreByIdLivre(Long id) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/livre/detail/" + id))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response " +response + "\n reponse " + reponse);

        ObjectMapper mapper= new ObjectMapper();

        LivreDTO livreDTO = mapper.readValue(response.body().toString(),
                new TypeReference<LivreDTO>() {});

        return livreDTO;
    }

    /**
     * Retourne le jwt
     * @return jwt
     */
    @Override
    public String getJwt() {
        return jwt;
    }


    /**
     * Recupere des livres pour l'accueil
     * @return liste livre
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public List<LivreDTO> getLivreToAccueil() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:9001/api/livre/accueil"))
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();

        ObjectMapper mapper = new ObjectMapper();

        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        String reponse = httpResponse.body();

        System.out.println("\n response " + httpResponse + "\n reponse " + reponse);


        List<LivreDTO> list = mapper.readValue(httpResponse.body().toString(), new TypeReference<List<LivreDTO>>(){});

        return list;
    }


    /**
     * Recupere les livres pour l'accueil
     * @param list
     * @return liste livre
     */
    @Override
    public List<LivreDTO> getLivreToAccueil(List<LivreDTO> list) {

        List<LivreDTO> listeDTO = new ArrayList<>();

        for (int i = 0; i<5; i++)
        {
            listeDTO.add(list.get(i));

            System.out.println("\n id livre " + list.get(i).getId());
        }
        System.out.println("\n la longueur de liste pour accueil " + listeDTO.size());
        return listeDTO;
    }


    /**
     * Recherche d'ouvrages par le titre et l'auteur
     * @param search
     * @return liste livre dto
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public List<LivreDTO> recherche(String search) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:9001/api/search/" +search))
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();

        ObjectMapper mapper = new ObjectMapper();

        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        String reponse = httpResponse.body();

        System.out.println("\n response " + httpResponse + "\n reponse " + reponse);


        List<LivreDTO> list = mapper.readValue(httpResponse.body().toString(), new TypeReference<List<LivreDTO>>(){});

        return list;


    }
}