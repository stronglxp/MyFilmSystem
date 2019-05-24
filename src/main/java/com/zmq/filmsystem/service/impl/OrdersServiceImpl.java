package com.zmq.filmsystem.service.impl;

import com.zmq.filmsystem.dao.OrdersMapper;
import com.zmq.filmsystem.entity.Orders;
import com.zmq.filmsystem.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public Integer deleteOrders(Integer filmId) {
        return ordersMapper.deleteOrders(filmId);
    }

    @Override
    public List<Map<String, Object>> getCanemaOrderList(Integer canemaId, Integer toPageNo, Integer pageSize) {
        return ordersMapper.getCanemaOrderList(canemaId, toPageNo, pageSize);
    }

    @Override
    public Integer getCanemaOrderNum(Integer canemaId) {
        return ordersMapper.getCanemaOrderNum(canemaId);
    }

    @Override
    public List<Map<String, Object>> getCustomerOrderList(Integer userId, Integer toPageNo, Integer pageSize) {
        return ordersMapper.getCustomerOrderList(userId, toPageNo, pageSize);
    }

    @Override
    public Integer getCustomerOrderNum(Integer userId) {
        return ordersMapper.getCustomerOrderNum(userId);
    }

    @Override
    public Map<String, Object> getOrderInfo(Integer orderId) {
        return ordersMapper.getOrderInfo(orderId);
    }

    @Override
    public Integer backTickets(Integer orderId) {
        return ordersMapper.backTickets(orderId);
    }

    @Override
    public Integer addOrder(Orders orders) {
        return ordersMapper.addOrder(orders);
    }
}
