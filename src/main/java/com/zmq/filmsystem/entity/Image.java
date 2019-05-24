package com.zmq.filmsystem.entity;

/**
 * 图片表
 */
public class Image {

    private Integer imageId;
    private String imgUrl;
    private Integer filmId;

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    @Override
    public String toString() {
        return "Image{" +
                "imageId=" + imageId +
                ", imgUrl='" + imgUrl + '\'' +
                ", filmId=" + filmId +
                '}';
    }
}
