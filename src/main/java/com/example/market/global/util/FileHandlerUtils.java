package com.example.market.global.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
public class FileHandlerUtils {
    public String saveImage(MultipartFile image) {
        String imgDir = "media/img/profiles/";
        String imgName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path imgPath = Path.of(imgDir + imgName);

        try {
            Files.createDirectories(Path.of(imgDir));
            image.transferTo(imgPath);
            log.info(image.getName());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return imgPath.toString();
    }

    public void deleteImage(String imagePath) {
        try {
            Files.deleteIfExists(Path.of(imagePath));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
