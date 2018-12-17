package com.example.administrator.myapplication;

/**
 * Created by Administrator on 2018-12-12.
 */

public class message {
    public message(String content, int imageID) {
        this.content = content;
        this.imageID = imageID;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String ID;
    public String image;
    public String content;
    public int imageID;

}
