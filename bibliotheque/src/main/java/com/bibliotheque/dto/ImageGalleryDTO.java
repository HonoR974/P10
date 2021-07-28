package com.bibliotheque.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageGalleryDTO {

    private Long id;
    private String name;
    private byte[] image;
    private String titreLivre;

}
