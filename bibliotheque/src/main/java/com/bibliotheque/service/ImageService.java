package com.bibliotheque.service;

import com.bibliotheque.model.ImageGallery;

import java.util.List;
import java.util.Optional;

public interface ImageService {

    ImageGallery saveImage(ImageGallery imageGallery);

    ImageGallery getImageByID(long id);

    List<ImageGallery> getAll();

}
