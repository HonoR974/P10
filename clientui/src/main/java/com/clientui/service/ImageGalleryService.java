package com.clientui.service;

import com.clientui.model.ImageGallery;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageGalleryService {

    ImageGallery saveImage(ImageGallery imageGallery) throws IOException, InterruptedException;

    ImageGallery getImageByID(Long id) throws IOException, InterruptedException;

    List<ImageGallery> getAll() throws IOException, InterruptedException;
}
