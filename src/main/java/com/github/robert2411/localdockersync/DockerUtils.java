package com.github.robert2411.localdockersync;


import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;

import org.apache.commons.io.FileUtils;

import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public abstract class DockerUtils {
    private DockerUtils(){}
    private static DockerClient client = null;
    public static DockerClient getDefaultDockerClient() {
        if (client == null) {
        DockerClientConfig config =  DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();
        DockerClient dockerClient = DockerClientImpl.getInstance(config, httpClient);
        dockerClient.pingCmd().exec();
        client = dockerClient;
        }
        return client;
    }
    
    public static List<ImageModel> getTags(DockerClient client){
         return client.listImagesCmd().exec().stream().flatMap(i -> Arrays.stream(i.getRepoTags())).map(i -> new ImageModel(i)).collect(Collectors.toList());
    }

        @SneakyThrows
        public static void saveImageToFile(DockerClient client, File file, String name, String tag) {
        try (var stream =  client.saveImageCmd(name).withTag(tag).exec()) {
            FileUtils.copyInputStreamToFile(stream, file);

        } catch (IOException e){
            throw new RuntimeException(e);
        }
        }

        public static void loadImage(DockerClient client, File file){
            try (var stream = FileUtils.openInputStream(file)) {
                client.loadImageCmd(stream).exec();
            } catch(IOException e){
                throw new RuntimeException(e);
            }
        }
}
