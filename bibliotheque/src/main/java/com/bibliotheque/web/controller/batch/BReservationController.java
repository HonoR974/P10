package com.bibliotheque.web.controller.batch;


import com.bibliotheque.dto.LivreDTO;
import com.bibliotheque.dto.ReservationDTO;
import com.bibliotheque.model.Livre;
import com.bibliotheque.model.Reservation;
import com.bibliotheque.service.LivreService;
import com.bibliotheque.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api/batch/reservation")
public class BReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private LivreService livreService;


    //get all first reservation by book
    @GetMapping("/firstAll")
    public ResponseEntity<?> getFirstReserveByBook()
    {
        List<Reservation> list = reservationService.getAllFirstReserve();

        if (list==null)
        {
            return new ResponseEntity<String>("Aucun n'a le statut first", HttpStatus.CONFLICT);
        }

        System.out.println("\n list : " + list.toString());
        List<ReservationDTO> listDTO  =reservationService.giveListDTO(list);

        return new ResponseEntity<List<ReservationDTO>>(listDTO, HttpStatus.ACCEPTED);
    }

    //get all first reservation by book
    //wich doesn't send mail
    //& le livre a un examplaire de disponible 
    @GetMapping("/firstNoSendMail")
    public ResponseEntity<?> getFirstReserveByBookNoSendMail()
    {
        //verifie les livres qui sont dispo
        livreService.checkDispoAllLivres();
        List<Reservation> list = reservationService.getAllFirstReserveNoSendMail();

        System.out.println("\n la taille de la liste " + list.size());

        if (list.size() < 1 )
        {
            return new ResponseEntity<String>("Aucune Réservation a le statut first sans avoir envoyé de mail ou  Aucun livre reservé n'est disponible ", HttpStatus.CONFLICT);
        }
        else
        {
            List<ReservationDTO> listDTO  =reservationService.giveListDTO(list);

            return new ResponseEntity<List<ReservationDTO>>(listDTO, HttpStatus.ACCEPTED);
        }
    }


    @PostMapping("/save")
    public ResponseEntity<?> saveList(@RequestBody HashMap<Integer,ReservationDTO> list) throws ParseException {

    
        reservationService.saveList(list);

        String reponse = "Correct";
        return new ResponseEntity<String>(reponse, HttpStatus.ACCEPTED);
    }

    //check le delai de 48 h apres l'envoie de l'email
    @PostMapping("/checkDelai")
    public ResponseEntity<?> checkDelai ()
    {
        List<Reservation> list = reservationService.checkDelai();
        List<ReservationDTO> dtoList = reservationService.giveListDTO(list);

        System.out.println("\n la liste des reservation annuler apres le dela de 48 h "
        + dtoList.toString());

        return new ResponseEntity<List<ReservationDTO>>(dtoList, HttpStatus.ACCEPTED);
    }



    //verifie les listes de reservations des livres
    //si la liste n'a plus de first
    //en elire une nouvelle reserv
    @PostMapping("/checkListeReserv")
    public ResponseEntity<?> checkListeResev()
    {
       List<Livre> livreChange =  reservationService.checkListeReservForAllBook();

        System.out.println("\n la liste des livres qui ont eu une réservation changé au statut first "
        + livreChange.toString());

        List<LivreDTO> listeDTO;

        if (livreChange.isEmpty())
        {
            return new ResponseEntity<String>("Aucun livre n'a eu une nouvelle " +
                    "reservation ", HttpStatus.CONFLICT);
        }
        else {
            listeDTO = livreService.convertListLivre(livreChange);

            return new ResponseEntity<List<LivreDTO>>(listeDTO, HttpStatus.ACCEPTED);
        }

    }

}
