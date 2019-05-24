package com.zmq.filmsystem.entity;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 电影表
 */
public class Film {

    // 电影id
    private Integer filmId;
    // 电影名称
    private String filmName;
    // 电影简介
    private String filmInfo;
    // 电影价格
    private Float filmPrice;
    // 电影播放时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date filmTime;
    // 电影所属影院
    private Integer canemaId;

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmInfo() {
        return filmInfo;
    }

    public void setFilmInfo(String filmInfo) {
        this.filmInfo = filmInfo;
    }

    public Float getFilmPrice() {
        return filmPrice;
    }

    public void setFilmPrice(Float filmPrice) {
        this.filmPrice = filmPrice;
    }

    public Date getFilmTime() {
        return filmTime;
    }

    public void setFilmTime(Date filmTime) {
        this.filmTime = filmTime;
    }

    public Integer getCanemaId() {
        return canemaId;
    }

    public void setCanemaId(Integer canemaId) {
        this.canemaId = canemaId;
    }

    @Override
    public String toString() {
        return "Film{" +
                "filmId=" + filmId +
                ", filmName='" + filmName + '\'' +
                ", filmInfo='" + filmInfo + '\'' +
                ", filmPrice=" + filmPrice +
                ", filmTime=" + filmTime +
                ", canemaId=" + canemaId +
                '}';
    }
}
