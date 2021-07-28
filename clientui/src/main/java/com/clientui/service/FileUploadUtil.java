package com.clientui.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {


    private static String FILE_NAME = "clientui/src/main/resources/static/image/";

    public static void saveFile(String uploadDir, String fileName,
                                byte[] file) throws IOException {

        System.out.println("\n save file ");
        Path uploadPath = Paths.get(uploadDir);

        System.out.println("\n uploadPath   " + uploadPath.toString());


        if (!Files.exists(uploadPath)) {

            System.out.println("\n uploadPath " + uploadPath.toString());
            Files.createDirectories(uploadPath);
        }


         try (FileOutputStream fos = new FileOutputStream(FILE_NAME + fileName)) {

         fos.write(file);


         }

    }




}