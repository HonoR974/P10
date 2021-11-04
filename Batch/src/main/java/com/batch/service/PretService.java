package com.batch.service;

import com.batch.model.PretDTO;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

/**
 * Interface PretService
 */
public interface PretService {

    /**
     * Recupere les pret valider
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    List<PretDTO> getPretEnCours() throws IOException, InterruptedException;

    /**
     * Recupere des prets avec le statut A rendre
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    List<PretDTO> getPretRetard() throws IOException, InterruptedException;

    /**
     * Envoie les mails pour les pret à rendre
     * @throws IOException
     * @throws InterruptedException
     */
    void sendMailRetard() throws IOException, InterruptedException;

    /**
     * Verifie les dates de prets et envoie la nouvelle liste a l'api
     * @throws IOException
     * @throws InterruptedException
     */
    void sendPret() throws IOException, InterruptedException;

    String sendMailPret(PretDTO pretDTO) throws MessagingException, IOException;
}
