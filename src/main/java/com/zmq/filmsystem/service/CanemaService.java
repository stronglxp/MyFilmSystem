package com.zmq.filmsystem.service;

import com.zmq.filmsystem.entity.Canema;

import java.util.List;
import java.util.Map;

public interface CanemaService {

    /**
     * 增加一个影院
     * @param canema
     * @return
     */
    Integer addCanema(Canema canema);

    /**
     * 获取影院信息
     * @param canemaId
     * @return
     */
    Canema getCanemaInfo(Integer canemaId);

    /**
     * 更新影院信息
     * @param canema
     * @return
     */
    Integer updateCanema(Canema canema);

    /**
     * 获取所有影院
     * @return
     */
    List<Map<String, Object>> getCanemaList();

    /**
     * 搜索电影院
     * @param canemaName
     * @return
     */
    List<Map<String, Object>> searchCanema(String canemaName);
}
