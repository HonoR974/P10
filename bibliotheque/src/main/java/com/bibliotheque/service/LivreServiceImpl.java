package com.bibliotheque.service;

import com.bibliotheque.dto.LivreDTO;
import com.bibliotheque.model.Examplaire;
import com.bibliotheque.model.Livre;
import com.bibliotheque.model.Pret;
import com.bibliotheque.model.Reservation;
import com.bibliotheque.model.Statut;
import com.bibliotheque.repository.LivreRepository;
import com.bibliotheque.repository.PretRepository;
import com.bibliotheque.repository.ReservationRepository;
import com.bibliotheque.repository.StatutRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service LivreServiceImpl
 */
@Service
public class LivreServiceImpl implements LivreService{



    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private StatutRepository statutRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PretRepository pretRepository;

    /**
     * Recupere touts les livres
     * @return liste livres
     */
    @Override
    public List<Livre> getAllLivres()
    {

        return livreRepository.findAll();
    }

    
    /**
     * Créer un livre
     * @param livre
     * @return livre
     */
    @Override
    public Livre createLivre(Livre livre)
    {
        livreRepository.save(livre);
        return livre;
    }


    /**
     * Recupere un livre selon l'id
     * @param id id-livre
     * @return l'id
     */
    @Override
    public Livre getLivreById(long id) {
        Livre livre =  livreRepository.findById(id);

        return livre;
    }

    /**
     * Modifie un livre
     * @param id id-livre
     * @param livreRequest
     * @return livre
     */
    @Override
    public Livre updateLivre(long id, Livre livreRequest)
    {
        Livre livre = livreRepository.findById(id);
        livre.setTitre(livreRequest.getTitre());
        livre.setAuteur(livreRequest.getAuteur());
        livreRepository.save(livre);
        return livre;
    }


    /**
     * Supprime un livre
     * @param id id-livre
     */
    @Override
    public void deleteLivre(long id) {
        livreRepository.deleteById(id);
    }

    /**
     * Recupere touts les exemplaires d'un livre
     * @param id id-livre
     * @return liste exemplaire
     */
    @Override
    public List<Examplaire> getAllExamplaireByIdLivre(long id) {
        Livre livre = livreRepository.findById(id);

        List<Examplaire> list = livre.getExamplaires();
        List<Examplaire> listFinal = new ArrayList<>();

        for (Examplaire examplaire : list)
        {
            if (!examplaire.isEmprunt())
            {
                listFinal.add(examplaire);
            }
        }

        return listFinal;
    }

    /**
     * Conversion Liste DTO
     * @param list
     * @return liste DTO
     */
    @Override
    public List<LivreDTO> convertListLivre(List<Livre> list)
    {
        List<LivreDTO> listFinal = new ArrayList<>();

        LivreDTO livreDTO ;
       for (Livre livre : list)
       {
           livreDTO = convertLivre(livre);
           listFinal.add(livreDTO);
       }

        return listFinal;
    }

    /**
     * Conversion DTO
     * @param livre
     * @return livreDTO
     */
    @Override
    public LivreDTO convertLivre(Livre livre) {

        LivreDTO livreDTO = new LivreDTO();
        List<Examplaire> examplaires = livre.getExamplaires();
        long nmbExamplaire = 0 ;

        livreDTO.setId(livre.getId());
        livreDTO.setAuteur(livre.getAuteur());
        livreDTO.setTitre(livre.getTitre());
        livreDTO.setDescription(livre.getDescription());

 


        for (Examplaire examplaire : examplaires)
        {
            if (!examplaire.isEmprunt() )
            {
                nmbExamplaire++;
            }
        }

        //si le livre est réservé 
        if (checkReserveLivre(livre.getId()))
        {
            //la date de retour la plus proche 
            livreDTO.setDateRetour(findClosestDate(livre.getId()));

            livreDTO.setNmbUserReserv(nmbUserReserv(livre));
        }

        livreDTO.setDisponible(checkDispo(livre.getId()));
     
        livreDTO.setExamplaires(nmbExamplaire);
        
        if(livre.getImage() != null)
        {
            livreDTO.setTitreImage(livre.getImage().getName());
        }
      
        livreDTO.setDescription(livre.getDescription());

        return livreDTO;
    }

    //trouve la date de fin du pret la plus proche pour le livre 
    private String findClosestDate(long id_livre) 
    {
        Livre livre = livreRepository.findById(id_livre);
        
        System.out.println("\n le livre " + livre.getTitre());

        List<Examplaire> list = new ArrayList<>();
        list = livre.getExamplaires();
        LocalDate dateBackOff = LocalDate.of(2000, 1, 1);
        List<Pret> lPrets = new ArrayList<>();
        Statut statut = statutRepository.findByNom("Valider");
      
     //   DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        for(int i = 0; i < list.size(); i++)
        {
            Examplaire e = list.get(i);
         
            System.out.println("\n examplaire " + e.getId());

            if(e.isEmprunt())
            {
                Pret pret = findPretActif(e);
                if(pret.getId() == null)
                {
                    System.out.println("\n  c null ");
                }
                else
                {
                    System.out.println("\n pret " + pret.getId());
                
                    if(pret.getDate_fin().isAfter(dateBackOff) )
                    {
                        dateBackOff = pret.getDate_fin();
                    }
                }
               

            }
        }
            
        System.out.println("\n dateBackOff " + dateBackOff.format(DateTimeFormatter.ofPattern("dd-MMM-yy")) );

        String reponse = dateBackOff.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return reponse;
    }

    private Pret findPretActif(Examplaire examplaire)
    {
        Pret pret = new Pret();

        for(Pret p : examplaire.getListeDePret())
        {
            if(p.getStatut().getNom().equals("Valider"))
            {

                pret = p;
            }
            else if( p.getStatut().getNom().equals("A Rendre"))
            {
                pret = p;
            }

        }


        return pret;
    }



    //obtient la date de retour par reservation 
    @Override
    public String dateRetourByLivre(Livre livre)
    {
        String reponseDate = "";

        Statut statut = statutRepository.findByNom("First");
        List<Reservation> listReserv = reservationRepository.findByStatutReservationAndLivreReservation(statut, livre);
        Date dateRetour = new Date();
        Date dateVerif = new Date();

        for (Reservation reservation : listReserv)
        {

            if (listReserv.size() <= 2 )
            {
                break;
            }
            else if (reservation.getDate_fin().before(dateRetour) )
            {
                dateRetour = reservation.getDate_fin();
            }

        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        System.out.println("\n last if ");

        if (dateRetour.equals(dateVerif))
        {
            System.out.println("\n il n'a pas de date de retour (livre) :  " + livre.getId());
            return "null";
        }
        return dateFormat.format(dateRetour);

    }

    @Override
    public int nmbUserReserv(Livre livre)
    {
        int nmbUser = 0;
        Statut statut1 = statutRepository.findByNom("First");
        Statut statut2 = statutRepository.findByNom("InList");

        List<Reservation> reservationList = reservationRepository.findByLivreReservationAndStatutReservation(livre, statut1);
        
        reservationList.addAll(reservationRepository.findByLivreReservationAndStatutReservation(livre, statut2));
        System.out.println("\n nmb de reservation est de " + reservationList.size() + "sur le livre a l'id " + livre.getId());

        nmbUser = reservationList.size();
        return nmbUser;
    }

    /**
     * Recupere touts les livres
     * @return
     */
    @Override
    public List<Livre> getAllLivre() {
        return livreRepository.findAll();
    }


    /**
     * Recherche des ouvrages par titre et auteur
     * @param recherche
     * @return liste livre
     */
    @Override
    public List<Livre> searchLivre(String recherche) {

        List<Livre> list4 = livreRepository.findByTitreContainsOrAuteurContains(recherche,recherche);

        System.out.println("\n list  " + list4);

        return list4;
    }

    //verifie si un livre est disponible
    @Override
    public boolean checkDispo(long id)
    {
        Livre livre = livreRepository.findById(id);
        boolean disponible = false;

        for (Examplaire examplaire : livre.getExamplaires())
        {
            if (!examplaire.isEmprunt())
            {
                disponible = true;
            }
        }


        //trouver si le livre est reservé 
        //true le livre est reservé 
        //false le livre n'est pas reserve 
        boolean reservé = checkReserveLivre(id);
       if(reservé)
       {
           disponible = false;
       }
      
        return disponible;
    }

    public boolean checkReserveLivre(long id)
    {
        boolean livreReserved = false;
        Livre livre = livreRepository.findById(id);
        Statut satut = statutRepository.findByNom("InList");
        Statut statut2 = statutRepository.findByNom("First");

        List<Reservation> list = reservationRepository.findByLivreReservationAndStatutReservation(livre, satut);
        list.addAll(reservationRepository.findByLivreReservationAndStatutReservation(livre,statut2));

        if(list.size() > 0 )
        {
            livreReserved = true;
            System.out.println("\n livreReserved " +  livreReserved);
        }

        return livreReserved;
    }
    

    @Override
    public void checkDispoAllLivres() {

        List<Livre> list = livreRepository.findAll();

        System.out.println("\n checkDispoAllLivres " );

        for (Livre livre : list)
        {

            livre.setDisponible(false);

            //si le livre posses des examplaires  & que le livre n'est pas reservé 
            if (livre.getExamplaires() != null && !checkReserveLivre(livre.getId()) )
            {

                for (Examplaire examplaire : livre.getExamplaires())
                {
                    if (!examplaire.isEmprunt())
                    {
                        livre.setDisponible(true);
                    }
                }
            }

           
            System.out.println("\n le livre " + livre.getId() + " est dispo : " + livre.getDisponible());

            livreRepository.save(livre);
        }
    }

  

}
