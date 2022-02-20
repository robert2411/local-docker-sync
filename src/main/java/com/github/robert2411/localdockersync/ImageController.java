package com.github.robert2411.localdockersync;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

@RestController
public class ImageController {
//Content-Type: 
    @GetMapping("/images")
    List<ImageModel> getImages(){
        return DockerUtils.getTags(DockerUtils.getDefaultDockerClient());
    }

    @GetMapping(value = "/images/tar/", produces = "application/x-tar")
    public byte[] getImage(@RequestParam(name = "name") String name, @RequestParam(name = "version") String version){
        File file = new File("./tmp/" + UUID.randomUUID() + ".tar");
        
        try {
            DockerUtils.saveImageToFile(DockerUtils.getDefaultDockerClient(), file, name, version);
            return Files.readAllBytes(file.toPath());
        } catch (IOException e){
            throw new RuntimeException(e);
        } finally {
            file.delete();
        }
    }


}
