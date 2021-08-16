package com.bibliotheque.web.controller.batch;


import com.bibliotheque.dto.ReservationDTO;
import com.bibliotheque.model.Reservation;
import com.bibliotheque.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/batch/reservation")
public class BReservationController {

    @Autowired
    private ReservationService reservationService;



    //get all first reservation by book
    @GetMapping("/firstAll")
    public ResponseEntity<?> getFirstReserveByBook()
    {
        List<Reservation> list = reservationService.getAllFirstReserve();
        if (list==null)
        {
            return new ResponseEntity<String>("Aucun n'a le statut first", HttpStatus.CONFLICT);
        }
        List<ReservationDTO> listDTO  =reservationService.giveListDTO(list);

        return new ResponseEntity<List<ReservationDTO>>(listDTO, HttpStatus.ACCEPTED);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveList(@RequestBody HashMap<Integer,ReservationDTO> list) throws ParseException {
        reservationService.saveList(list);

        String reponse = "Correct";
        return new ResponseEntity<String>(reponse, HttpStatus.ACCEPTED);
    }

}
