package com.bibliotheque.repository;

import com.bibliotheque.model.ImageGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageGalleryRepository extends JpaRepository<ImageGallery, Long> {

    ImageGallery findById(long id);

}