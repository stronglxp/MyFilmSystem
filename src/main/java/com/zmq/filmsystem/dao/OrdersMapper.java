package com.zmq.filmsystem.dao;

import com.zmq.filmsystem.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrdersMapper {

    /**
     * 删除某部电影相关的订单
     * @param filmId
     * @return
     */
    Integer deleteOrders(Integer filmId);

    /**
     * 获取某个商家的订单列表
     * @param canemaId
     * @param toPageNo
     * @param pageSize
     * @return
     */
    List<Map<String, Object>> getCanemaOrderList(@Param("canemaId") Integer canemaId,
                                          @Param("toPageNo") Integer toPageNo,
                                          @Param("pageSize") Integer pageSize);

    /**
     * 获取某个商家的订单总数
     * @param canemaId
     * @return
     */
    Integer getCanemaOrderNum(Integer canemaId);

    /**
     * 获取某个顾客的订单列表
     * @param userId
     * @param toPageNo
     * @param pageSize
     * @return
     */
    List<Map<String, Object>> getCustomerOrderList(@Param("userId") Integer userId,
                                                   @Param("toPageNo") Integer toPageNo,
                                                   @Param("pageSize") Integer pageSize);

    /**
     * 获取某个顾客的订单总数
     * @param userId
     * @return
     */
    Integer getCustomerOrderNum(Integer userId);

    /**
     * 获取订单电影开始时间
     * @param orderId
     * @return
     */
    Map<String, Object> getOrderInfo(Integer orderId);

    /**
     * 退票操作
     * @param orderId
     * @return
     */
    Integer backTickets(Integer orderId);

    /**
     * 添加一条订单记录
     * @param orders
     * @return
     */
    Integer addOrder(Orders orders);
}
