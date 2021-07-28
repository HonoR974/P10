package com.clientui.service;

import com.clientui.dto.LivreDTO;
import com.clientui.model.ImageGallery;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

@Service
public class ImageGaleryServiceImpl implements ImageGalleryService {

    @Autowired
    private AuthBiblioService authBiblioService;

    private String jwt;

    private final Path root = Paths.get("clientui/uploads/");

    private final HttpClient client =  HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();


    @Override
    public ImageGallery saveImage(ImageGallery imageGallery) throws IOException, InterruptedException {

        this.jwt = authBiblioService.getJwt();

        System.out.println("\n image " + imageGallery.toString());

        String image64 = Base64.encodeBase64String(imageGallery.getImage());

        System.out.println("\n image64 " + image64 );
        //je récupère les valeurs de l'user
        var values = new HashMap<String, String>() {{
            put("id", String.valueOf(imageGallery.getId()));
            put("name", imageGallery.getName());
            put("image", image64);
        }};


        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/image/upload"  ))
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString() );

        String reponse = response.body();

        System.out.println("\n response " + response );

        ObjectMapper mapper= new ObjectMapper();

        return  mapper.readValue(response.body().toString(),
                new TypeReference<ImageGallery>() {});
    }


    @Override
    public ImageGallery getImageByID(Long id) throws IOException, InterruptedException {


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/image/get/" + id  ))
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString() );

        String reponse = response.body();

        System.out.println("\n response " + response + "\n reponse " + reponse);

        ObjectMapper mapper= new ObjectMapper();

        return  mapper.readValue(response.body().toString(),
                new TypeReference<ImageGallery>() {});
    }

    @Override
    public List<ImageGallery> getAll() throws IOException, InterruptedException {

        this.jwt = authBiblioService.getJwt();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/image/get/all"))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString() );

        String reponse = response.body();

        System.out.println("\n response " + response + "\n reponse " + reponse);
        ObjectMapper mapper= new ObjectMapper();

        List<ImageGallery> list = mapper.readValue(response.body().toString(),
                new TypeReference<List<ImageGallery>>() {});


        return list;
    }
}
