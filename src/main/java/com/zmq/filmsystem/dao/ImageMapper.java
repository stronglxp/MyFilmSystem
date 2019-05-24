package com.zmq.filmsystem.dao;

import com.zmq.filmsystem.entity.Image;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {

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
