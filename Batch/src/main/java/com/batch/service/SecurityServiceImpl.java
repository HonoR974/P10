package com.batch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Service SecurityServiceImpl
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    private final HttpClient client =  HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    private String jwt;

    @Value("${batch.username}")
    private String username;
    @Value("${batch.password}")
    private String password;

    /**
     * Return un jwt avec les id du batch
     * @return jwt
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public String authticate() throws IOException, InterruptedException {

        var values = new HashMap<String, String>() {{
            put("username", username);
            put ("password", password);
        }};

        System.out.println("\n username / password " + username + " / " + password);
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);



        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/authenticate"))
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        //jwt
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        String reponse  = response.body();

        System.out.println("\n response : " + response + "\n reponse : " +reponse);


        String jwt = parseJwt(reponse);


        return jwt;
    }

    /**
     * Parse la jwt
     * @param jwtBrut
     * @return jwt
     * @throws JsonProcessingException
     */
    public String parseJwt(String jwtBrut ) throws JsonProcessingException
    {

        Map<String,Object> result =
                new ObjectMapper().readValue(jwtBrut, HashMap.class);
        Object jwtObject = result.get("jwt");

        this.jwt = jwtObject.toString();
        return jwtObject.toString();

    }
}
