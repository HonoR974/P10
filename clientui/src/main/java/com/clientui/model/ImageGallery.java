package com.clientui.model;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class ImageGallery {

    private Long id;

    private String name;

    private byte[] image;

    private String titreLivre;

    public ImageGallery() {}

    public ImageGallery(String name, byte[] image) {
        this.name = name;
        this.image = image;
    }

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
        return "ImageGallery{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image=" + Arrays.toString(image) +
                ", titreLivre='" + titreLivre + '\'' +
                '}';
    }
}
