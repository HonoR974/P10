package com.bibliotheque.web.controller;

import com.bibliotheque.dto.PretDTO;
import com.bibliotheque.model.Pret;
import com.bibliotheque.model.User;
import com.bibliotheque.service.EspaceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RestController EspaceController
 */
@RestController
@RequestMapping("/api/espace")
public class EspaceController
{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EspaceService espaceService;

    /**
     * Recupere un user par son id
     * @param id id-user
     * @return user
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "id")Long id)
    {
        User user = espaceService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    /**
     * Recupere les prets de l'user
     * @param id id-user
     * @return liste pret DTO
     */
    @GetMapping("/prets/{id}")
    public  ResponseEntity<?> getListePretById(@PathVariable(name = "id")Long id)
    {
        List<Pret> list = espaceService.getListPretByIdUser(id);

        List<PretDTO> pretDTOList = espaceService.giveListDTO(list);

        System.out.println("\n pretDTOlist " + pretDTOList);
        return new ResponseEntity<>(pretDTOList, HttpStatus.ACCEPTED);
    }

    /**
     * Recupere un pret par l'id
     * @param id id-pret
     * @return pret DTO
     */
    @GetMapping("/pret/{id}")
    public ResponseEntity<?> getPretById(@PathVariable(name = "id")Long id)
    {

        PretDTO pretDTO = espaceService.givePretDTO(espaceService.getPretById(id));


        return new ResponseEntity<>(pretDTO, HttpStatus.ACCEPTED);
    }
}
