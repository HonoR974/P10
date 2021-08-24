package com.clientui.service;

import com.clientui.dto.ReservationDTO;

import java.io.IOException;
import java.util.List;

public interface ReservationService {
    List<ReservationDTO> getReservByBook(Long id) throws IOException, InterruptedException;
}
