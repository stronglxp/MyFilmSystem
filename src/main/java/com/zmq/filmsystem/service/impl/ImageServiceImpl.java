package com.zmq.filmsystem.service.impl;

import com.zmq.filmsystem.dao.ImageMapper;
import com.zmq.filmsystem.entity.Image;
import com.zmq.filmsystem.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageMapper imageMapper;

    @Override
    public Integer addImage(Image image) {
        return imageMapper.addImage(image);
    }

    @Override
    public Integer deleteImage(Integer filmId) {
        return imageMapper.deleteImage(filmId);
    }
}
