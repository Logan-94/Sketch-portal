package com.sample.login;

/**
 * Created by mohit_soni on 3/24/2017.
 */

public class Imageuploading {

    public String name;
    public String url;
    public String email;
    public  String desc;

    public String getDesc() {
        return desc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Imageuploading(String name, String url,String email,String desc) {
        this.name = name;
        this.url = url;
        this.email=email;
        this.desc=desc;
    }

    public Imageuploading()
    {

    }
}
