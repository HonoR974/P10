package com.bibliotheque.service;

import com.bibliotheque.dto.ReservationDTO;
import com.bibliotheque.model.*;
import com.bibliotheque.repository.LivreRepository;
import com.bibliotheque.repository.ReservationRepository;
import com.bibliotheque.repository.StatutRepository;
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
        System.out.println("\n create ");
        Livre livre = livreRepository.findById(id_livre);

        //si le le livre n'a pas de reservation avec le statut first
        Statut statut = statutDisponible(livre);
        System.out.println("\n statut a donner à la reserv " + statut.getNom());

//        System.out.println("\n creation le statut " + statut.getNom());

        //l'user avec le jwt
        User user = securityService.getUser();

        //la date d'aujourd'hui
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date dateDemande = new Date();


        Reservation reservation = new Reservation();
        reservation.setLivreReservation(livre);
        reservation.setUserReservation(user);
        reservation.setStatutReservation(statut);
        reservation.setDateDemande(dateDemande);
        reservation.setMailSend(false);
 

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

        System.out.println("\n statutDispo " );

        if(!reservationList.isEmpty())
        {
            for (Reservation reservation : reservationList)
            {
    
                //si la reservation a le statut first il ne peut pas en avoir d'autre
                //donc InList
                if (reservation.getStatutReservation().getNom().equals("First") )
                {
                    statut = statutRepository.findByNom("InList");
                    return statut;
                }
             
    
            }
    
        }
      
        System.out.println("\n statut a retourner " + statut.getNom());

        return statut;
    }

    //- verifie si l'user emprunte deja le livre, par l'id
    //le titre du livre ne doit pas correspondre a ceux deja emprunté
    //par l'user
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
            //verifie avec le titre
            if (livreEmprunter.getTitre().equals(livre.getTitre())) {
                condition = false;
                break;
            }

        }

        System.out.println("\n checkEmruntUser " + condition);
        return condition;
    }

    @Override
    public List<Reservation> getAll() {

        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservById(long id) {


        return reservationRepository.findById(id);
    }

    @Override
    public ReservationDTO giveReservationDTO(Reservation reservation) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date_demande = dateFormat.format(reservation.getDateDemande());


        ReservationDTO reservationDTO = new ReservationDTO();

        reservationDTO.setId(reservation.getId());

        reservationDTO.setUsername(reservation.getUserReservation().getUsername());
        reservationDTO.setTitre(reservation.getLivreReservation().getTitre());
        reservationDTO.setStatut(reservation.getStatutReservation().getNom());

        reservationDTO.setMail(reservation.getUserReservation().getEmail());
        reservationDTO.setSendMail(reservation.isMailSend());
        reservationDTO.setDate_demande(dateFormat.format(reservation.getDateDemande()));
        reservationDTO.setTitreImage(reservation.getLivreReservation().getImage().getName());

        return reservationDTO;
    }

    @Override
    public List<ReservationDTO> giveListDTO(List<Reservation> reservationList) {

        System.out.println("\n give listeDTO ");

        List<ReservationDTO> list = new ArrayList<>();

        for (Reservation reservation : reservationList)
        {
            list.add(giveReservationDTO(reservation));
            
        }
        return list;
    }

    @Override
    public List<Reservation> giveList(List<ReservationDTO> listeDTO) throws ParseException {

      
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
             //System.out.println("\n la date a convertire : " + reservationDTO.getDate_debut());


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


    //verifie la taille de la liste d'attente des reserv
    //elle ne doit pas etre superieur à aux double d'examplaire  du livre
    @Override
    public boolean checkPlaceListe(long id_livre) {
        boolean condition = false;

        Livre livre = livreRepository.findById(id_livre);
        Statut statut = statutRepository.findByNom("En Attente");

        long max = (long)livre.getExamplaires().size() * 2L;



        long liste = reservationRepository.findByStatutReservationAndLivreReservation(statut,livre).size();
        if (liste < max)
        {
            condition = true;
        }

        return condition;
    }

    //verifie si l'user ne possede pas deja une reservation sur le livre
    @Override
    public boolean checkReservDispo(long id_livre) {

        Livre livre = livreRepository.findById(id_livre);
        User user = securityService.getUser();
        boolean reservDispo = true;

        System.out.println("\n check Reserv Dispo ");
        System.out.println("\n test ");

        if (!livre.getReservations().isEmpty())
        {
            System.out.println("\n le livre a des reserv");
            for (Reservation reservation : livre.getReservations())
            {
                System.out.println("\n la reservation " + reservation.getId());

                if (reservation.getUserReservation().getUsername().equals(user.getUsername()))
                 {
                    System.out.println("\n l'user :" + user.getUsername() + " possede deja le livre ");
                    reservDispo = false;
                    break;
                }
            }
        }
        else
        {
            System.out.println("\n le livre n' a pas de reservation ");
        }

        System.out.println("\n checkReserv Dispo  FIN " + reservDispo);

        return reservDispo;

    }

    @Override
    public List<Reservation> getByUser() {
        User user = securityService.getUser();
        Statut statut = statutRepository.findByNom("First");
        Statut statut2 = statutRepository.findByNom("InList");

        List<Reservation> list1 = reservationRepository.findByStatutReservationAndUserReservation(statut, user);

    
        list1.addAll(reservationRepository.findByStatutReservationAndUserReservation(statut2, user));


        return list1;
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

        System.out.println("\n ------- get all first reserve ---------- ");

    //    System.out.println("\n le repos simple " + reservationRepository.findAll());
      //  System.out.println("\n getAllFirstReserver " + reservationRepository.findByStatutReservation(statut).toString() );


        return reservationRepository.findByStatutReservation(statut);
    }

    //recupere tout les reserve first qui n'ont pas envoyé leur mail 
    //et qui n'ont pas recu d'email
    @Override
    public List<Reservation> getAllFirstReserveNoSendMail() {
        Statut statut = statutRepository.findByNom("First");

        List<Reservation> listFirst = reservationRepository.findByStatutReservation(statut);
        List<Reservation> listFinal = new ArrayList<>();

        System.out.println("\n list First size " + listFirst.size());

        for (Reservation reservation : listFirst)
        {

            System.out.println("\n reservation " + reservation.getId() + " / " + reservation.getLivreReservation().getDisponible());
            boolean examplaireDisponible = livreService.checkDispo(reservation.getLivreReservation().getId());

            //si la reservation n'a pas envoyé de mail et qu'un examplaire est dispo 
            if ( !reservation.isMailSend() && examplaireDisponible)
            {
                listFinal.add(reservation);
            }

        }

        System.out.println("\n la liste First NoSendMail " + listFinal.size());

        return listFinal;
    }

    @Override
    public void saveList(HashMap<Integer, ReservationDTO> list) throws ParseException {

        List<ReservationDTO> listDTO = new ArrayList<>();

        for (int i = 0;  i <= list.size(); i++)
        {

            listDTO.add(list.get(i));
        }



        List<Reservation> listReserv = giveList(listDTO);

        reservationRepository.saveAll(listReserv);

    }


    //verifie le delai des reservation statut first
    //si la reserv est first, a recu un mail et le delai
    //est depassé alors la reserv est annulé
    @Override
    public List<Reservation> checkDelai() {

        Date dateNow = new Date(System.currentTimeMillis());
        Date dateFinReserv = new Date();

        Statut statut = statutRepository.findByNom("Annuler");
        Statut statutFirst = statutRepository.findByNom("First");

        List<Reservation> liste = reservationRepository.findByStatutReservation(statutFirst);

        System.out.println("\n liste " + liste.size());
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
    //si le livre n'en a pas la plus ancienne des reservation en attente
    //devient first
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


    //verifie que la liste ne contient pas de reservation first
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

    //cherche la reserve ayant la date de demande la plus ancienne
    //pour la faire passer first
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

        List<Reservation>list = reservationRepository.findByLivreReservationAndStatutReservation(livre, statut1);
        list.addAll(reservationRepository.findByLivreReservationAndStatutReservation(livre, statut2));
        return  list;
    }


    //retourne un examplaire vide
    @Override
    public Examplaire finishReservation(long id_reserv) {

        Reservation reservation = reservationRepository.findById(id_reserv);
        Statut statut = statutRepository.findByNom("Fini");

        Livre livre = reservation.getLivreReservation();

        Examplaire examplairetest = new Examplaire();

        for (Examplaire examplaire : livre.getExamplaires())
        {
            if (!examplaire.isEmprunt())
            {
                reservation.setStatutReservation(statut);
                reservationRepository.save(reservation);

                return examplaire;
            }
        }



   //     System.out.println("\n examplaire " + examplairetest.getId());

        return examplairetest;
    }


}


