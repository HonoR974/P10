package com.bibliotheque.web.controller;

import com.bibliotheque.dto.ExamplaireDTO;
import com.bibliotheque.dto.LivreDTO;
import com.bibliotheque.model.Examplaire;
import com.bibliotheque.model.Livre;
import com.bibliotheque.service.ExamplaireService;
import com.bibliotheque.service.LivreService;
import com.bibliotheque.web.exception.ExamplaireIntrouvableException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.mockito.Mockito.never;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RestController ExemplaireController
 * Chaque methode renvoie un objet DTO
 */

@RestController
@RequestMapping("/api/examplaire")
public class ExamplaireController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ExamplaireService examplaireService;

    @Autowired
    private LivreService livreService;

    /**
     * Recupere touts les exemplaires
     * @return liste exemplaires
     */
    @GetMapping
    public List<ExamplaireDTO> getAllExamplaire()
    {
        return examplaireService.getAllExamplaire().stream().map(
                examplaire -> modelMapper.map(examplaire, ExamplaireDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Créer un exemplaire
     * @param examplaireDTO
     * @return exemplaire
     */
    @PostMapping
    public ResponseEntity<ExamplaireDTO> createExamplaire(@RequestBody ExamplaireDTO examplaireDTO)
    {
        Examplaire examplaireRequest = modelMapper.map(examplaireDTO,Examplaire.class);

        Examplaire examplaire = examplaireService.createExamplaire(examplaireRequest);

        ExamplaireDTO examplaireResponse = modelMapper.map(examplaire, ExamplaireDTO.class);

        return new ResponseEntity<ExamplaireDTO>(examplaireResponse, HttpStatus.CREATED);
    }

    /**
     * Recupere un exemplaire par l'id
     * @param id id-exemplaire
     * @return exemplaire
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExamplaireDTO> getExamplaireById(@PathVariable(name = "id")Long id)
    {
        Examplaire  examplaire = examplaireService.getExamplaireById(id);

        ExamplaireDTO examplaireDTO = examplaireService.convertExamplaire(examplaire);

        return new ResponseEntity<ExamplaireDTO>(examplaireDTO,HttpStatus.ACCEPTED);
    }

    /**
     * Modifie un exemplaire
     * @param id id-exemplaire
     * @param examplaireDTO
     * @return exemplaire
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExamplaireDTO> updateExamplaire(@PathVariable(name = "id") Long id,
                                                       @RequestBody ExamplaireDTO examplaireDTO)
    {   


        Examplaire examplaireRequest = modelMapper.map(examplaireDTO,Examplaire.class);

        Examplaire examplaire = examplaireService.updateExamplaire(id, examplaireRequest);

        if(examplaire==null) throw new ExamplaireIntrouvableException ( "L'examplaire avec " +
        " l'id " + id + " n'existe pas ");
      
        ExamplaireDTO examplaireResponse = modelMapper.map(examplaire,ExamplaireDTO.class);

        return new ResponseEntity<ExamplaireDTO>(examplaireResponse,HttpStatus.ACCEPTED);
    }

    /**
     * Supprime un exemplaire
     * @param id
     * @return Statut Http
     */
    @DeleteMapping("/{id}")
    public HttpStatus deleteExamplaire(@PathVariable(name = "id")Long id)
    {
        Examplaire examplaire = examplaireService.getExamplaireById(id);
        if(examplaireService.getExamplaireById(id)==null) throw new ExamplaireIntrouvableException (  "L'examplaire avec " +
        " l'id " + id + " n'existe pas ");

       try
       {

        examplaireService.deleteExamplaire(id);
        return HttpStatus.ACCEPTED;
       
        }
        catch(Exception e)
        {
            throw new ExamplaireIntrouvableException("l'examplaire "  + id + " ne peut etre supprimé pour le moment ");
        }
        


      
    }

    /**
     * Recupere le livre par l'id
     * @param id id-livre
     * @return livre
     */
    @GetMapping("/livre/{id}")
    public ResponseEntity<LivreDTO> getLivreByIdExamplaire(@PathVariable(name = "id")Long id)
    {

        Livre livre = examplaireService.getLivreById(id);
        LivreDTO livreDTO = livreService.convertLivre(livre);

        return new ResponseEntity<LivreDTO>(livreDTO,HttpStatus.ACCEPTED);

    }


}
