package com.batch.service;

import com.batch.model.ReservationDTO;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface ReservationService {


    //verification des premiers de la liste
    List<ReservationDTO> getFirstReserv() throws IOException, InterruptedException, MessagingException;

    void sendListReservation() throws IOException, InterruptedException;


}