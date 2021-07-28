package com.bibliotheque.web.controller;

import com.bibliotheque.dto.LivreDTO;
import com.bibliotheque.dto.PretBatchDTO;
import com.bibliotheque.dto.PretDTO;
import com.bibliotheque.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Recontroller BatchController
 */
@RestController
@RequestMapping("/api/batch")
public class BatchController {

    @Autowired
    private BatchService batchService;


    /**
     * Recupere les pret valider
     * @return
     * @throws IOException
     */
    @GetMapping("/encours")
    public ResponseEntity<List<PretBatchDTO>> getPretEnCours() throws IOException {

        System.out.println("\n methode en cours GET  ");

        List<PretBatchDTO> list = batchService.getPretEnCours();

        System.out.println( "\n en cours  liste dans le controller  " +  list.toString() );

        return new ResponseEntity<List<PretBatchDTO>>(list, HttpStatus.ACCEPTED);
    }

    /**
     * Envoie les prets à rendre
     * @param listeMap
     * @return
     */
    @PostMapping("/encours")
    public HttpStatus sendPretEnCours(@RequestBody Map<Integer,PretBatchDTO> listeMap)
    {
        System.out.println("\n la hasmap recut est " + listeMap);

        batchService.sendPretEnCours(listeMap);

        return  HttpStatus.ACCEPTED;

    }

    /**
     * Recupere les prets à rendre
     * @return
     */
    @GetMapping("/retards")
    public ResponseEntity<List<PretBatchDTO>> getPretRetards()
    {

        List<PretBatchDTO> list = batchService.getPretRetard();

        System.out.println( "\n en retard  liste dans le controller  " +  list.toString() );

        return new ResponseEntity<List<PretBatchDTO>>(list, HttpStatus.ACCEPTED);
    }

    @PostMapping("/rappel")
    public ResponseEntity<List<PretBatchDTO>> sendListPretRappel(@RequestBody Map<Integer, PretBatchDTO> list)
    {

        List<PretBatchDTO> listBatch = batchService.sendPretRappel(list);

        System.out.println("\n listeBatch " + listBatch);

        return new ResponseEntity<List<PretBatchDTO>>(listBatch, HttpStatus.ACCEPTED);
    }

}
