package com.icode.jiling.na517demo_mvvm.model;

import java.io.Serializable;

/**
 * Created by jiling on 2018/4/3.
 */

public class RecomendAnimModel implements Serializable {
    private String img;

    private String PlayCount;

    private String commentCount;

    private String vedioTime;


    private String type;

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPlayCount() {
        return PlayCount;
    }

    public void setPlayCount(String playCount) {
        PlayCount = playCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getVedioTime() {
        return vedioTime;
    }

    public void setVedioTime(String vedioTime) {
        this.vedioTime = vedioTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
