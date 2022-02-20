package com.github.robert2411.localdockersync;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;


@Service
public class RemoteScanner {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ConfigProperties configProperties;

    public RemoteScanner(ConfigProperties configProperties){
        this.configProperties = configProperties;
    }
    @Scheduled(fixedDelay = 60 * 1000)
    public void scan(){
        configProperties.getRemoteHosts()
        .forEach(h -> scanHost(h));
    }

    public void scanHost(String host){
        System.out.println("Scanning host");
        var images = getImages(host);
        var localImages = DockerUtils.getTags(DockerUtils.getDefaultDockerClient());

        var diff = new ArrayList<>(images);
        diff.removeAll(localImages);

        diff.forEach(i -> downloadAndLoadImage(host, i));

    }

    public List<ImageModel> getImages(String host){
        System.out.println("Getting images of host " + host);
        return Arrays.asList(restTemplate.getForEntity(host + "/images", ImageModel[].class).getBody());
    }

    public void downloadAndLoadImage(String host, ImageModel image){
        String fileName = "./tmp/"+ UUID.randomUUID() + ".tar";
        File file = new File(fileName);
        System.out.println("Downloading and loading image " + image.getTag() + " from host " + host);
        try{
            downloadImageToFile(host, image, file);
            DockerUtils.loadImage(DockerUtils.getDefaultDockerClient(), file);
        } finally {
            if (file.exists()){
                file.delete();
            }
        }
    }

    public void downloadImageToFile(String host, ImageModel image, File file){
        restTemplate.execute(host + image.getDownloadUrl(), HttpMethod.GET, null, 
        response -> StreamUtils.copy(response.getBody(), new FileOutputStream(file)));
    }
}
