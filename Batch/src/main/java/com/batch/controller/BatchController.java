package com.batch.controller;

import com.batch.model.PretDTO;
import com.batch.service.PretService;
import com.batch.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * RestController BatchController
 *
 */

@RestController
public class BatchController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PretService pretService;

    /**
     *
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping("/authenticate")
    public ResponseEntity<String> authenticate() throws IOException, InterruptedException {
        System.out.println("\n authenticate ");
        String jwt = securityService.authticate();

        return new ResponseEntity<String>(jwt, HttpStatus.ACCEPTED);
    }

    @GetMapping("/")
    public String test()
    {
        System.out.println("\n test ");
        return "salut";
    }

    @GetMapping("/Prets")
    public ResponseEntity<List<PretDTO>> getPretsRetard() throws IOException, InterruptedException {

        List<PretDTO> list = pretService.getPretRetard();

        return new ResponseEntity<List<PretDTO>>(list,HttpStatus.ACCEPTED);
    }
}
