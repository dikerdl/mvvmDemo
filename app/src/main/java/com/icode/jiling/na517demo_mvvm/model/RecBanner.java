package com.icode.jiling.na517demo_mvvm.model;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by jiling on 2018/4/3.
 */

public class RecBanner implements Serializable,Comparable{
    private String imgUrl;

    private String title;

    private boolean isAd = false;

    private String uri;

    public boolean getIsAd() {
        return isAd;
    }

    public void setAd(boolean ad) {
        isAd = ad;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        if(o instanceof RecBanner){
            RecBanner model = (RecBanner)o;
            return this.getTitle().equals(model.getTitle()) ? 0:1;
        }
        return 0;
    }
}
