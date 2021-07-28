package com.bibliotheque.service;

import com.bibliotheque.model.ImageGallery;
import com.bibliotheque.repository.ImageGalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageGalleryRepository repository;

    @Override
    public ImageGallery saveImage(ImageGallery imageGallery) {

         return   repository.save(imageGallery);
    }

    @Override
    public ImageGallery getImageByID(long id) {
        return repository.findById(id);
    }

    @Override
    public List<ImageGallery> getAll() {
        return repository.findAll();
    }


}
