package org.example.model;
import lombok.Getter;
import  lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Image_ex extends Image{
    private String[] url;
    private Integer index = 0;
}
