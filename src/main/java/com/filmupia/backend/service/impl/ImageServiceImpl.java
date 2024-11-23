package com.filmupia.backend.service.impl;

import com.filmupia.backend.entity.Image;
import com.filmupia.backend.entity.enums.ImageType;
import com.filmupia.backend.exception.FilmupiaApiException;
import com.filmupia.backend.repository.ImageRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl {

    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private final ImageRepository imageRepository;

    @Value("${google.service-account-key-path}")
    private String serviceAccountKeyPath;

    @Value("${google.folders.user}")
    private String userFolderId;

    @Value("${google.folders.blog}")
    private String blogFolderId;

    public Image uploadImageToDrive(File file, ImageType imageType) {
        Image res = new Image();

        try {
            String mimeType = Files.probeContentType(file.toPath());

            if (!"image/png".equals(mimeType) && !"image/jpeg".equals(mimeType)) {
                throw new FilmupiaApiException("Only PNG and JPG files are allowed.");
            }

            String folderId = getFolderIdForImageType(imageType);

            Drive drive = createDriveService();

            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));

            FileContent mediaContent = new FileContent(mimeType, file);

            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id")
                    .execute();

            String imageUrl = "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
            System.out.println("IMAGE URL: " + imageUrl);

            if (uploadedFile != null) {
                file.delete();
            }

            res.setImageName(file.getName());
            res.setImageUrl(imageUrl);
            res.setImageType(imageType);
            imageRepository.save(res);
        } catch (Exception e) {
            throw new FilmupiaApiException(e.getMessage());
        }
        return res;
    }

    public List<Long> uploadImagesToDrive(List<File> files, ImageType imageType) {
        return files.stream().map(file -> {
            Image image = uploadImageToDrive(file, imageType);
            return image.getId();
        }).collect(Collectors.toList());
    }


    public void deleteImageFromDrive(Image image) throws IOException {
        try {
            Drive drive = createDriveService();

            String fileId = extractFileIdFromUrl(image.getImageUrl());

            if (fileId != null) {
                drive.files().delete(fileId).execute();
                System.out.println("File deleted successfully: " + fileId);
            } else {
                throw new FilmupiaApiException("Invalid image URL format.");
            }
            imageRepository.delete(image);
        } catch (Exception e) {
            System.out.println("Error deleting file: " + e.getMessage());
            throw new FilmupiaApiException("Failed to delete image from Google Drive.");
        }
    }

    private String extractFileIdFromUrl(String url) {
        String[] parts = url.split("id=");
        return (parts.length == 2) ? parts[1] : null;
    }

    private String getFolderIdForImageType(ImageType imageType) {
        switch (imageType) {
            case USER:
                return userFolderId;
            case BLOG:
                return blogFolderId;
            default:
                throw new FilmupiaApiException("Unknown image type: " + imageType);
        }
    }

    private Drive createDriveService() throws GeneralSecurityException, IOException {

        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(serviceAccountKeyPath))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential)
                .build();
    }
}
