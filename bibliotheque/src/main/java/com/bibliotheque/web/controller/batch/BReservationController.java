package com.bibliotheque.web.controller.batch;


import com.bibliotheque.dto.ReservationDTO;
import com.bibliotheque.model.Reservation;
import com.bibliotheque.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/batch/reservation")
public class BReservationController {

    @Autowired
    private ReservationService reservationService;



    //get all first reservation by book
    @GetMapping("/firstAll")
    public ResponseEntity<List<ReservationDTO>> getFirstReserveByBook()
    {
        List<Reservation> list = reservationService.getAllFirstReserve();
        List<ReservationDTO> listDTO  =reservationService.giveListDTO(list);

        return new ResponseEntity<List<ReservationDTO>>(listDTO, HttpStatus.ACCEPTED);
    }

    @PostMapping("/save")
    public HttpStatus saveList(@RequestBody HashMap<Integer,ReservationDTO> list)
    {
        saveList(list);

        return HttpStatus.ACCEPTED;
    }

}
