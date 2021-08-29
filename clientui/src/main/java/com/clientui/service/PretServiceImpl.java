package com.clientui.service;

import com.clientui.model.PretBean;
import com.clientui.dto.PretDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Service PretServiceImpl
 */
@Service
public class PretServiceImpl implements PretService
{
    private final HttpClient client =  HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    private String jwt;

    private AuthBiblioService authBiblioService;

    public PretServiceImpl(AuthBiblioService service)
    {
        this.authBiblioService = service;
    }


    /**
     * Creation d'un pret
     * @param id_examplaire
     * @return pret
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public PretDTO createPret(Long id_examplaire) throws IOException, InterruptedException {
        this.jwt = authBiblioService.getJwt();
        System.out.println("\n jwt : " + jwt );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/pret/create/" + id_examplaire))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE,"application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response = " + response + "\n reponse : " + reponse );

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(response.body().toString(), new TypeReference<PretDTO>() {});
    }

    /**
     * Conversion de dto a pretBean
     * @param pretDTO
     * @return pretBean
     */
    @Override
    public PretBean givePretBean(PretDTO pretDTO) throws ParseException {


        PretBean pretBean = new PretBean();

        pretBean.setId(pretDTO.getId());

        //ajouter les dates
        Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(pretDTO.getDate_debut());
        Date date2=new SimpleDateFormat("yyyy-MM-dd").parse(pretDTO.getDate_fin());
        pretBean.setDate_debut(date1);
        pretBean.setDate_fin(date2);


        pretBean.setUsername(pretDTO.getUsername());
        pretBean.setTitre(pretDTO.getTitre());
        pretBean.setStatut(pretDTO.getStatut());
        pretBean.setTitreImage(pretDTO.getTitreImage());

        return pretBean;
    }


    /**
     * Validation d'un pret
     * @param id_pret
     * @return pret
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public PretDTO validePret(Long id_pret) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/pret/validate/" + id_pret))
                .POST(HttpRequest.BodyPublishers.noBody())
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer "+jwt)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response : " + response + "\n reponse : " + reponse);


        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(response.body().toString(), new TypeReference<PretDTO>() {});

    }

    /**
     * Recupere un pret dto par l'id
     * @param id_pret
     * @return pret dto
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public PretDTO getPretDTOById(Long id_pret) throws IOException, InterruptedException {


        this.jwt = authBiblioService.getJwt();



        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/pret/" + id_pret))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response : " + response + "\n reponse : " + reponse);

        ObjectMapper mapper = new ObjectMapper();

        return  mapper.readValue(response.body().toString(), new TypeReference<PretDTO>() {});

    }


    /**
     * Finalise un pret
     * @param id_pret
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void finishPret(Long id_pret) throws IOException, InterruptedException {
        this.jwt = authBiblioService.getJwt();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/pret/finish/" + id_pret))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response : " + response + "\n reponse : " + reponse);

    }


    /**
     * Prolonge un pret
     * @param id_pret
     * @return pret dto
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public PretDTO prolongPret(Long id_pret) throws IOException, InterruptedException {

        this.jwt = authBiblioService.getJwt();


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/pret/prolong/" + id_pret))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response : " + response + "\n reponse : " + reponse);

        ObjectMapper mapper = new ObjectMapper();

        return  mapper.readValue(response.body().toString(), new TypeReference<PretDTO>() {});

    }

    /**
     * Recupere tout les prets en cours
     * @return liste prets
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public List<PretDTO> getPretEmprunter() throws IOException, InterruptedException {

        this.jwt = authBiblioService.getJwt();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/pret/admin/prets"))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response : " + response + "\n reponse : " + reponse);

        ObjectMapper mapper = new ObjectMapper();

        return  mapper.readValue(response.body().toString(), new TypeReference<List<PretDTO>>() {});

    }

    @Override
    public List<PretBean> convertList(List<PretDTO> list) throws ParseException {

        List<PretBean> listBean = new ArrayList<>();

        for (PretDTO pretDTO : list)
        {
            listBean.add(givePretBean(pretDTO));
        }

        return listBean;
    }
}
