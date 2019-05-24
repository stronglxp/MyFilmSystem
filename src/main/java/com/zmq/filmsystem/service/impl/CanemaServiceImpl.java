package com.zmq.filmsystem.service.impl;

import com.zmq.filmsystem.dao.CanemaMapper;
import com.zmq.filmsystem.entity.Canema;
import com.zmq.filmsystem.service.CanemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CanemaServiceImpl implements CanemaService {

    @Autowired
    private CanemaMapper canemaMapper;

    @Override
    public Integer addCanema(Canema canema) {
        return canemaMapper.addCanema(canema);
    }

    @Override
    public Canema getCanemaInfo(Integer canemaId) {
        return canemaMapper.getCanemaInfo(canemaId);
    }

    @Override
    public Integer updateCanema(Canema canema) {
        return canemaMapper.updateCanema(canema);
    }


    @Override
    public List<Map<String, Object>> getCanemaList() {
        return canemaMapper.getCanemaList();
    }


    @Override
    public List<Map<String, Object>> searchCanema(String canemaName) {
        return canemaMapper.searchCanema(canemaName);
    }
}
