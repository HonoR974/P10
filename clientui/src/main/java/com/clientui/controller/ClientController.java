package com.clientui.controller;

import com.clientui.convert.BASE64DecodedMultipartFile;
import com.clientui.dto.LivreDTO;
import com.clientui.dto.UserDTO;
import com.clientui.model.ImageGallery;
import com.clientui.model.TesterUser;
import com.clientui.service.AuthBiblioService;
import com.clientui.service.FileUploadUtil;
import com.clientui.service.ImageGalleryService;
import com.clientui.service.LivreService;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Controller ClientController
 */
@Controller
public class ClientController {


    @Autowired
    private ImageGalleryService imageGalleryService;

    @Autowired
    private AuthBiblioService authBiblioService;

    @Autowired
    private LivreService livreService;

    /**
     * Donne la page d'accueil
     * @param model
     * @return accueil
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping("/")
    public String accueil(Model model) throws IOException, InterruptedException {

        List<LivreDTO> listAPI = livreService.getLivreToAccueil();

        List<LivreDTO> listCarousel = livreService.getLivreToAccueil(listAPI);

        System.out.println("\n liste API " + listAPI.toString() + "\n ------------ \n liste Carousel " + listCarousel.toString());

        model.addAttribute("listeCarousel", listCarousel);
        model.addAttribute("user", authBiblioService.testConnection());

        return "Accueil";
    }


    /**
     * Recherche d'ouvrages par le titre ou l'auteur
     * @param recherche
     * @param model
     * @return livres/Recherche
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping("/search")
    public String search(@RequestParam("search")String recherche,Model model) throws IOException, InterruptedException {


        List<LivreDTO> list = livreService.recherche(recherche);
        TesterUser user = authBiblioService.testConnection();

        model.addAttribute("liste", list);
        model.addAttribute("user", user);
        return "livres/Recherche";
    }






















    @GetMapping("/index")
    public String index(Model model)
    {
        ImageGallery img = new ImageGallery();
        model.addAttribute("img", img);
        return "index";
    }


    @PostMapping("/image/saveImageDetails")
    public RedirectView createProduct(Model model,
                                     @RequestParam("image") MultipartFile file)
            throws IOException, InterruptedException {


        if (file.isEmpty())
        {
            System.out.println("\n image vide ");
        }


            ImageGallery imageGallery = new ImageGallery();
            imageGallery.setName(file.getOriginalFilename());
            imageGallery.setImage(file.getBytes());


        String uploadDir = "image/" + imageGallery.getName();

        System.out.println("\n uploadDir " + uploadDir );

        //le telechargement
        FileUploadUtil.saveFile(uploadDir, imageGallery.getName(), imageGallery.getImage());

        ImageGallery imgLast  = imageGalleryService.saveImage(imageGallery);

        return new RedirectView("/image/get/" + imgLast.getId() , true);
    }


    @GetMapping("/image/get/{id}")
    public String imageGetById(@PathVariable(name = "id")Long id,
                               Model model) throws IOException, InterruptedException {

        //l'ajoute de la classe
        ImageGallery imageGallery = imageGalleryService.getImageByID(id);

        String uploadDir = "image/" + imageGallery.getName();

        //conversion
        BASE64DecodedMultipartFile decode = new BASE64DecodedMultipartFile();
        decode.setImage(imageGallery.getImage());
        decode.setFileName(imageGallery.getName());

        //le telechargement
         FileUploadUtil.saveFile(uploadDir, imageGallery.getName(), imageGallery.getImage());


        model.addAttribute("img", imageGallery);
        return "image";

    }

    @GetMapping("/images")
    public String getAllImage(Model model) throws IOException, InterruptedException {

        List<ImageGallery> list = imageGalleryService.getAll();

        model.addAttribute("liste" , list);
        return "listeImage";

    }


}