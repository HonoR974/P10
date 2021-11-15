package com.bibliotheque.dto;

import lombok.Data;

import java.util.Arrays;


@Data
public class ImageGalleryDTO {

    private Long id;
    private String name;
    private byte[] image;
    private String titreLivre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getTitreLivre() {
        return titreLivre;
    }

    public void setTitreLivre(String titreLivre) {
        this.titreLivre = titreLivre;
    }

    @Override
    public String toString() {
        return "ImageGalleryDTO [id=" + id + ", image=" + Arrays.toString(image) + ", name=" + name + ", titreLivre="
                + titreLivre + "]";
    }

    
}
