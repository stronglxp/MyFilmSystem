package com.zmq.filmsystem.entity;

/**
 * 电影座位表
 */
public class FilmSeat {

    // 电影id
    private Integer filmId;
    // 座位行号
    private Integer filmSeatRow;
    // 座位列号
    private Integer filmSeatCol;
    // 座位是否可被预定
    private Integer filmSeatIsActive;

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public Integer getFilmSeatRow() {
        return filmSeatRow;
    }

    public void setFilmSeatRow(Integer filmSeatRow) {
        this.filmSeatRow = filmSeatRow;
    }

    public Integer getFilmSeatCol() {
        return filmSeatCol;
    }

    public void setFilmSeatCol(Integer filmSeatCol) {
        this.filmSeatCol = filmSeatCol;
    }

    public Integer getFilmSeatIsActive() {
        return filmSeatIsActive;
    }

    public void setFilmSeatIsActive(Integer filmSeatIsActive) {
        this.filmSeatIsActive = filmSeatIsActive;
    }

    @Override
    public String toString() {
        return "FilmSeat{" +
                "filmId=" + filmId +
                ", filmSeatRow=" + filmSeatRow +
                ", filmSeatCol=" + filmSeatCol +
                ", filmSeatIsActive=" + filmSeatIsActive +
                '}';
    }
}
