package com.github.robert2411.localdockersync;


import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value={ "name", "version", "downloadUrl" }, allowGetters=true)
public class ImageModel {
    private String image;
    public ImageModel(){
        image = "";
    }
    public ImageModel(String image){
        this.image = image;
    }

    public String getName(){
        return image.substring(0, image.indexOf(":"));
    }

    public String getVersion() {
        return image.substring(image.indexOf(":")+1);
    }

    public void setTag(String tag){
        image = tag; 
    }
    public String getTag(){
        return image;
    }

    public String getDownloadUrl(){
        return "/images/tar/?name=" + getName() + "&version=" + getVersion();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ImageModel))
            return false;
        ImageModel other = (ImageModel)o;
        return Objects.equals(this.image, other.image);
    }

    @Override
    public int hashCode() {
        return image.hashCode();
    }
}
