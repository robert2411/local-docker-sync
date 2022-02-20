package com.github.robert2411.localdockersync;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.UUID;

@Disabled
class DockerUtilsTest {

    @Test
    void saveImage() {
        File file = new File("./tmp/" + UUID.randomUUID() + ".tar");
        
        try {
            DockerUtils.saveImageToFile(DockerUtils.getDefaultDockerClient(), file, "hello-world", "latest");

        } finally {
            file.delete();
        }
        
    }

    @Test
    void getTags() {
        System.out.println("------");
        DockerUtils.getTags(DockerUtils.getDefaultDockerClient());
        System.out.println("------");
    }
}