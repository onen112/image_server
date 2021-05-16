package org.example.model;

import lombok.Getter;
import  lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Image {

    private String imageName;
    private  Integer imageId;
    private Long size;
    private String uploadTime;
    private String md5;
    private String contentType;
    private String path;
}
