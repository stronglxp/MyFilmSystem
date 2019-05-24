package com.zmq.filmsystem.dao;

import com.zmq.filmsystem.entity.Film;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FilmMapper {

    /**
     * 获取某个影院的所有电影列表
     * @param canemaId
     * @return
     */
    List<Map<String, Object>> getFilmList(@Param("canemaId") Integer canemaId,
                                          @Param("toPageNo") Integer toPageNo,
                                          @Param("pageSize") Integer pageSize);

    /**
     * 获取某个影院的总数量
     * @param canemaId
     * @return
     */
    Integer getFilmNum(Integer canemaId);

    /**
     * 增加一部电影
     * @param film
     * @return
     */
    Integer addFilm(Film film);

    /**
     * 搜索某个影院的电影
     * @param film
     * @return
     */
    List<Map<String, Object>> selectFilm(Film film);

    /**
     * 删除一部电影
     * @param filmId
     * @return
     */
    Integer deleteFilm(Integer filmId);

    /**
     * 查看电影是否存在
     * @param filmId
     * @return
     */
    Integer getFilmById(Integer filmId);

    /**
     * 更新电影信息
     * @param film
     * @return
     */
    Integer updateFilm(Film film);

    /**
     * 获取某个影院的电影列表
     * @param canemaId
     * @return
     */
    List<Map<String, Object>> getCanemaFilmList(Integer canemaId);

    /**
     * 搜索某个电影院的电影
     * @param canemaId
     * @param filmName
     * @return
     */
    List<Map<String, Object>> searchCanemaFilm(@Param("canemaId") Integer canemaId,
                                               @Param("filmName") String filmName);

    /**
     * 获取某部电影的信息
     * @param filmId
     * @return
     */
    Film getFilmInfoById(Integer filmId);
}
