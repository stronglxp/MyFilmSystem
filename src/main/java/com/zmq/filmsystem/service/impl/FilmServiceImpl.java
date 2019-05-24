package com.zmq.filmsystem.service.impl;

import com.zmq.filmsystem.dao.FilmMapper;
import com.zmq.filmsystem.entity.Film;
import com.zmq.filmsystem.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FilmServiceImpl implements FilmService {

    @Autowired
    private FilmMapper filmMapperl;

    @Override
    public List<Map<String, Object>> getFilmList(Integer canemaId, Integer toPageNo, Integer pageSize) {
        return filmMapperl.getFilmList(canemaId, toPageNo, pageSize);
    }

    @Override
    public Integer getFilmNum(Integer canemaId) {
        return filmMapperl.getFilmNum(canemaId);
    }

    @Override
    public Integer addFilm(Film film) {
        return filmMapperl.addFilm(film);
    }

    @Override
    public List<Map<String, Object>> selectFilm(Film film) {
        return filmMapperl.selectFilm(film);
    }

    @Override
    public Integer deleteFilm(Integer filmId) {
        return filmMapperl.deleteFilm(filmId);
    }

    @Override
    public Integer getFilmById(Integer filmId) {
        return filmMapperl.getFilmById(filmId);
    }

    @Override
    public Integer updateFilm(Film film) {
        return filmMapperl.updateFilm(film);
    }

    @Override
    public List<Map<String, Object>> getCanemaFilmList(Integer canemaId) {
        return filmMapperl.getCanemaFilmList(canemaId);
    }

    @Override
    public List<Map<String, Object>> searchCanemaFilm(Integer canemaId, String filmName) {
        return filmMapperl.searchCanemaFilm(canemaId, filmName);
    }

    @Override
    public Film getFilmInfoById(Integer filmId) {
        return filmMapperl.getFilmInfoById(filmId);
    }
}
