package com.zmq.filmsystem.service;

import com.zmq.filmsystem.entity.Image;

public interface ImageService {

    /**
     * 增加一张图片
     * @param image
     * @return
     */
    Integer addImage(Image image);

    /**
     * 删除电影相关的图片
     * @param filmId
     * @return
     */
    Integer deleteImage(Integer filmId);
}
