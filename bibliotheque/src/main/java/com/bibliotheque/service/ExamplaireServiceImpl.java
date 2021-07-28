package com.bibliotheque.service;

import com.bibliotheque.dto.ExamplaireDTO;
import com.bibliotheque.model.Examplaire;
import com.bibliotheque.model.Livre;
import com.bibliotheque.repository.ExamplaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service ExemplaireServiceImpl
 */
@Service
public class ExamplaireServiceImpl implements ExamplaireService{

    @Autowired
    private ExamplaireRepository examplaireRepository;

    @Override
    public List<Examplaire> getAllExamplaire() {
        return examplaireRepository.findAll();
    }

    /**
     * Créer un exemplaire
     * @param examplaire
     * @return exemplaire
     */
    @Override
    public Examplaire createExamplaire(Examplaire examplaire) {
        examplaireRepository.save(examplaire);
        return examplaire;
    }


    /**
     * Modifie un exemplaire
     * @param id id-exemplaire
     * @param examplaireRequest
     * @return exemplaire
     */
    @Override
    public Examplaire updateExamplaire(long id, Examplaire examplaireRequest) {
        Examplaire examplaire = examplaireRepository.findById(id);
        examplaire.setEdition(examplaireRequest.getEdition());
        examplaireRepository.save(examplaire);
        return examplaire;
    }

    /**
     * Supprime un exemplaire
     * @param id id-exemplaire
     */
    @Override
    public void deleteExamplaire(long id) {
            examplaireRepository.deleteById(id);
    }

    /**
     * Recupere un exemplaire
     * @param id id-exemplaire
     * @return exemplaire
     */
    @Override
    public Examplaire getExamplaireById(long id) {
        return examplaireRepository.findById(id);
    }

    /**
     * Recupere un livre par son id
     * @param id id-livre
     * @return
     */
    @Override
    public Livre getLivreById(long id) {
        Examplaire examplaire = examplaireRepository.findById(id);

        return examplaire.getLivre();
    }

    /**
     * Conversion DTIO
     * @param examplaire
     * @return exemplaire DTO
     */
    @Override
    public ExamplaireDTO convertExamplaire(Examplaire examplaire) {

        ExamplaireDTO examplaireDTO = new ExamplaireDTO();

        examplaireDTO.setId(examplaire.getId());
        examplaireDTO.setEdition(examplaire.getEdition());


        return examplaireDTO;
    }
}
