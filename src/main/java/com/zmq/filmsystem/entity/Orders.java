package com.zmq.filmsystem.entity;

import java.sql.Timestamp;

/**
 * 订单表
 */
public class Orders {

    // 订单id
    private Integer orderId;
    // 用户id
    private Integer userId;
    // 电影id
    private Integer filmId;
    // 电影座位行号
    private Integer filmSeatRow;
    // 电影座位列号
    private Integer filmSeatCol;
    // 下单时间
    private Timestamp orderTime;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId='" + orderId + '\'' +
                ", userId=" + userId +
                ", filmId=" + filmId +
                ", filmSeatRow=" + filmSeatRow +
                ", filmSeatCol=" + filmSeatCol +
                ", orderTime=" + orderTime +
                '}';
    }
}
