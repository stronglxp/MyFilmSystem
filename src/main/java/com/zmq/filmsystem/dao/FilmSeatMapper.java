package com.zmq.filmsystem.dao;

import com.zmq.filmsystem.entity.FilmSeat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FilmSeatMapper {

    /**
     * 删除某部电影的所有座位
     * @param filmId
     * @return
     */
    Integer deleteSeat(Integer filmId);

    /**
     * 批量增加座位
     * @param seatList
     * @param filmId
     * @return
     */
    Integer insertSeat(@Param("seatList") List<Map<Integer, Integer>> seatList,
                       @Param("filmId") Integer filmId);

    /**
     * 获取某个电影已被预订的座位
     * @param filmId
     * @return
     */
    List<Map<String, Object>> getUnAcSeat(Integer filmId);

    /**
     * 更新座位状态
     * @param filmSeat
     * @return
     */
    Integer updateSeat(FilmSeat filmSeat);
}
