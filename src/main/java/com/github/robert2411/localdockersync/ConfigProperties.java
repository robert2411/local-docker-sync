package com.github.robert2411.localdockersync;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class ConfigProperties {
    
    private List<String> remoteHosts = new ArrayList<>();

    public void setRemoteHosts(List<String> remoteHosts){
        this.remoteHosts = remoteHosts;
    }

    public List<String> getRemoteHosts(){
        return remoteHosts;
    }
}
