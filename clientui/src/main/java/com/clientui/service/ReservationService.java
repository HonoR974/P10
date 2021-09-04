package com.clientui.service;

import com.clientui.dto.ExamplaireDTO;
import com.clientui.dto.LivreDTO;
import com.clientui.dto.ReservationDTO;

import java.io.IOException;
import java.util.List;

public interface ReservationService {
    List<ReservationDTO> getReservByBook(Long id) throws IOException, InterruptedException;

    //renvoie un message succés ou un message d'error
    String createReserv(Long id_livre) throws IOException, InterruptedException;

    List<ReservationDTO> getReserByUser() throws IOException, InterruptedException;

    ReservationDTO getReservById(long id) throws IOException, InterruptedException;

    String cancelReservation(long id) throws IOException, InterruptedException;

    ExamplaireDTO finishReserv(long id) throws IOException, InterruptedException;

}
