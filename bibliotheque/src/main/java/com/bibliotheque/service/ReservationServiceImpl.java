package com.bibliotheque.service;

import com.bibliotheque.dto.LivreDTO;
import com.bibliotheque.dto.ReservationDTO;
import com.bibliotheque.model.*;
import com.bibliotheque.repository.LivreRepository;
import com.bibliotheque.repository.ReservationRepository;
import com.bibliotheque.repository.StatutRepository;
import com.bibliotheque.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService{


    @Autowired
    private SecurityService securityService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private StatutRepository statutRepository;

    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private LivreService livreService;

    /**
     * Creer une reservation avec l'id du livre
     * @param id_livre
     * @return reservation
     */
    @Override
    public Reservation createReservation(long id_livre)
    {
        Livre livre = livreRepository.findById(id_livre);

        //si le le livre n'a pas de reservation avec le statut first
        Statut statut = statutDisponible(livre);

        //l'user avec le jwt
        User user = securityService.getUser();

        //la date d'aujourd'hui
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date dateDemande = new Date();
        System.out.println("\n date de mnt : " + dateFormat.format(dateDemande));


        Reservation reservation = new Reservation();
        reservation.setLivreReservation(livre);
        reservation.setUserReservation(user);
        reservation.setStatutReservation(statut);
        reservation.setDateDemande(dateDemande);
        System.out.println("\n la reservation crée " + reservation.toString());

        reservationRepository.save(reservation);
        return reservation;
    }

    //si le livre a deja une reservation avec le statut first
    //renvoie statut InLine
    //sinon renvoie statut First
    private Statut statutDisponible(Livre livre)
    {
        Statut statut = statutRepository.findByNom("First");
        List<Reservation> reservationList = livre.getReservations();

        for (Reservation reservation : reservationList)
        {
            //si la reservation a le statut first il ne peut pas en avoir d'autre
            //donc InList
            if (reservation.getStatutReservation().getNom().equals("First"))
            {
                System.out.println("\n la reservation : " + reservation.getId() + " est la First ");
                statut = statutRepository.findByNom("InList");
                break;
            }

        }
        return statut;
    }

    @Override
    public boolean checkEmpruntUser(long id_livre)
    {
        boolean condition = true;

        Livre livre = livreRepository.findById(id_livre);
        User user = securityService.getUser();

        for(Pret pret : user.getListeDePret())
        {
            Livre livreEmprunter = pret.getExamplaire().getLivre();

            //si le livre emprunter à le meme titre que le livre demandé à reservé
            if (livreEmprunter.getTitre().equals(livre.getTitre())) {
                condition = false;
                break;
            }

        }
        return condition;
    }

    @Override
    public List<Reservation> getAll() {

        return reservationRepository.findAll();
    }

    @Override
    public ReservationDTO giveReservationDTO(Reservation reservation) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date_demande = dateFormat.format(reservation.getDateDemande());


        System.out.println("\n la reservation a convertir " + reservation.toString());
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setUsername(reservation.getUserReservation().getUsername());
        reservationDTO.setTitre(reservation.getLivreReservation().getTitre());
        reservationDTO.setStatut(reservation.getStatutReservation().getNom());
        reservationDTO.setSendMail(reservationDTO.isSendMail());
        reservationDTO.setMail(reservation.getUserReservation().getEmail());

        reservationDTO.setDate_demande(dateFormat.format(reservation.getDateDemande()));

        return reservationDTO;
    }

    @Override
    public List<ReservationDTO> giveListDTO(List<Reservation> reservationList) {

        List<ReservationDTO> list = new ArrayList<>();

        for (Reservation reservation : reservationList)
        {
            list.add(giveReservationDTO(reservation));
        }
        return list;
    }

    @Override
    public List<Reservation> giveList(List<ReservationDTO> listeDTO) throws ParseException {

        System.out.println("\n give List  " + listeDTO.toString());

        List<Reservation> list = new ArrayList<>();
        Reservation reservation;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date debut;
        Date fin;

        //recupere le statut et les dates du dto
        for (ReservationDTO reservationDTO : listeDTO)
        {

            if (reservationDTO ==null)
            {
                break;
            }
            //recupere la reserve
            reservation = reservationRepository.findById(reservationDTO.getId());


            //statut
            Statut statut = statutRepository.findByNom(reservationDTO.getStatut());
            reservation.setStatutReservation(statut);

            //date
            System.out.println("\n la date a convertire : " + reservationDTO.getDate_debut());


            debut = format.parse(reservationDTO.getDate_debut());
            fin = format.parse(reservationDTO.getDate_fin());
            reservation.setDate_debut(debut);
            reservation.setDate_fin(fin);

            //mail
            reservation.setMailSend(reservationDTO.isSendMail());

            list.add(reservation);
        }

        return list;
    }


    @Override
    public boolean checkPlaceListe(long id_livre) {
        boolean condition = false;

        Livre livre = livreRepository.findById(id_livre);
        Statut statut = statutRepository.findByNom("En Attente");

        long max = livre.getExamplaires().size();

        long liste = reservationRepository.findByStatutReservationAndLivreReservation(statut,livre).size();


        System.out.println("\n max " + max + "\n liste " + liste);

        if (liste < max)
        {
            condition = true;
        }

        return condition;
    }

    @Override
    public List<Reservation> getByUser() {
        User user = securityService.getUser();
        Statut statut = statutRepository.findByNom("En Attente");

        return reservationRepository.findByStatutReservationAndUserReservation(statut,user);
    }

    @Override
    public void cancelReservation(long id_reservation)
    {
        Reservation reservation = reservationRepository.findById(id_reservation);
        Statut statut = statutRepository.findByNom("Annuler");
        reservation.setStatutReservation(statut);
        reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getAllFirstReserve() {
        Statut statut = statutRepository.findByNom("First");

        System.out.println("\n le repos simple " + reservationRepository.findAll());
        System.out.println("\n getAllFirstReserver " + reservationRepository.findByStatutReservation(statut).toString() );
        return reservationRepository.findByStatutReservation(statut);
    }

    @Override
    public List<Reservation> getAllFirstReserveNoSendMail() {
        Statut statut = statutRepository.findByNom("First");

        List<Reservation> listFirst = reservationRepository.findByStatutReservation(statut);
        List<Reservation> listFinal = new ArrayList<>();

        for (Reservation reservation : listFirst)
        {
            if (!reservation.isMailSend())
            {
                listFinal.add(reservation);
            }
        }

        System.out.println("\n la liste First NoSendMail " + listFinal.toString());

        return listFinal;
    }

    @Override
    public void saveList(HashMap<Integer, ReservationDTO> list) throws ParseException {

        List<ReservationDTO> listDTO = new ArrayList<>();

        for (int i = 0;  i <= list.size(); i++)
        {

            listDTO.add(list.get(i));
        }

        System.out.println("\n listDTO " + listDTO.toString());

        List<Reservation> listReserv = giveList(listDTO);

        reservationRepository.saveAll(listReserv);

    }


    //verifie le delai des reservation statut first
    @Override
    public List<Reservation> checkDelai() {
        Date dateNow = new Date(System.currentTimeMillis());
        Date dateFinReserv = new Date();
        Statut statut = statutRepository.findByNom("Annuler");
        Statut statutFirst = statutRepository.findByNom("First");
        List<Reservation> liste = reservationRepository.findByStatutReservation(statutFirst);
        List<Reservation> reservChanged = new ArrayList<>();



        for (Reservation reservation : liste)
        {
            dateFinReserv = reservation.getDate_fin();

            //mail envoyé et delai depassé = annulation
            if (reservation.isMailSend() && dateFinReserv.before(dateNow))
            {
                System.out.println("\n la reservation est dépassé de 48 h ID : " + reservation.getId());
                reservation.setStatutReservation(statut);
                reservationRepository.save(reservation);
                reservChanged.add(reservation);
            }
        }


        return reservChanged;
    }


   //cherche les reservations qui n'ont pas de statut first
    @Override
    public List<Livre> checkListeReservForAllBook()
    {

        List<Livre> listLivre = livreRepository.findAll();
        List<Livre> listeSend = new ArrayList<>();
        List<Reservation> listReservation;

        for (Livre livre : listLivre)
        {
            listReservation = livre.getReservations();


            //savoir si il a une reserve first
            if ( !listReservation.isEmpty() && !checkNewFirst(listReservation) )
            {
                System.out.println("\n le livre " + livre.getId() +
                        " n' a pas de reservation first  first");

                //si il n'en a pas
                //la plus vielle devient first

                findNewReserv(livre.getReservations());
                listeSend.add(livre);
            }

        }
        return listeSend;
    }


    public boolean checkNewFirst(List<Reservation> list)
    {
        boolean containFirst = false;

        for (Reservation reservation : list)
        {
            if (reservation.getStatutReservation().getNom().equals("First"))
            {
                System.out.println("\n la reservation : " + reservation.getId() +
                        " a le statut first ");
                containFirst = true;
            }
        }

        return containFirst;
    }

    public void findNewReserv(List<Reservation> list)
    {
        System.out.println("\n Find New Reserv ");
        int index = 0;
        Date date = new Date(System.currentTimeMillis());

        Statut statut = statutRepository.findByNom("First");

        Reservation reservation;
        System.out.println("\n taille de la liste " + list.size());


        for (int i =0; i < list.size(); i++)
        {
            reservation = list.get(i);
            if (reservation.getDateDemande().before(date))
            {
                date = reservation.getDateDemande();
                index = i;
            }
        }

        List<ReservationDTO> dtoList = giveListDTO(list);
        System.out.println("\n index " + index);
        System.out.println("\n la liste avant l acces  a la nouvelle reservation  "
        +  dtoList.toString());
        reservation = list.get(index);
        reservation.setStatutReservation(statut);
        reservationRepository.save(reservation);

        System.out.println("\n la nouvelle reservation first est " + reservation.toString());

        System.out.println("\n --------------------FIN DU JOB --------------------------");
    }


    @Override
    public List<Reservation> getReservByBook(Long id_livre)
    {
        long id = id_livre;
        Livre livre = livreRepository.findById(id);
        Statut statut1 = statutRepository.findByNom("First");
        Statut statut2 = statutRepository.findByNom("InList");

        return reservationRepository.findByLivreReservationAndStatutReservationOrStatutReservation(livre,statut1,statut2);
    }
}


