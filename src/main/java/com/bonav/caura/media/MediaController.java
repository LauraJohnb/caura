package com.bonav.caura.media;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.nio.file.Files;

@Controller
@AllArgsConstructor
@Slf4j
public class MediaController {
    private final Environment env;
    @GetMapping(path="media/{image}",produces=MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity getMediaFile(@PathVariable String image){
        try {
            File _file = new File(env.getProperty("files_location") + image);
            return ResponseEntity.ok().body(Files.readAllBytes(_file.toPath()));
        }catch(Exception e){
            log.error(e.getMessage(),e.getClass().getSimpleName(),e);
            return ResponseEntity.badRequest().body("No such file");
        }

    }
}
