package com.zmq.filmsystem.service.impl;

import com.zmq.filmsystem.dao.FilmSeatMapper;
import com.zmq.filmsystem.entity.FilmSeat;
import com.zmq.filmsystem.service.FilmSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FilmSeatServiceImpl implements FilmSeatService {

    @Autowired
    private FilmSeatMapper filmSeatMapper;

    @Override
    public Integer deleteSeat(Integer filmId) {
        return filmSeatMapper.deleteSeat(filmId);
    }

    @Override
    public Integer insertSeat(List<Map<Integer, Integer>> seatList, Integer filmId) {
        return filmSeatMapper.insertSeat(seatList, filmId);
    }

    @Override
    public List<Map<String, Object>> getUnAcSeat(Integer filmId) {
        return filmSeatMapper.getUnAcSeat(filmId);
    }

    @Override
    public Integer updateSeat(FilmSeat filmSeat) {
        return filmSeatMapper.updateSeat(filmSeat);
    }
}
