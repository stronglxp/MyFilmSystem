package com.zmq.filmsystem.controller;

import com.zmq.filmsystem.entity.FilmSeat;
import com.zmq.filmsystem.entity.PageVO;
import com.zmq.filmsystem.entity.User;
import com.zmq.filmsystem.service.FilmSeatService;
import com.zmq.filmsystem.service.OrdersService;
import com.zmq.filmsystem.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 顾客controller
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private FilmSeatService filmSeatService;

    /**
     * 返回订单列表
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/order/list")
    public String orderList(Model model, HttpServletRequest request) {

        Integer page = null;
        if(request.getParameter("page") != null) {
            // 获取链接中给的page
            page = Integer.parseInt(request.getParameter("page"));
        }

        // 获取当前登录的顾客名称
        Subject subject = SecurityUtils.getSubject();
        String customerName = (String)subject.getPrincipal();

        // 通过顾客名称获取顾客信息
        User userInfo = userService.getUserInfo(customerName);

        // 用户是否存在
        if(userInfo == null) {
            model.addAttribute("msg", "不明用户");
            return "redirect:/login";
        }

        Integer userId = userInfo.getUserId();

        List<Map<String, Object>> orderList = new ArrayList<>();
        // 页码对象
        PageVO pageVO = new PageVO();
        // 设置总页数
        pageVO.setTotalCount(ordersService.getCustomerOrderNum(userId));
        if(page == null || page == 0) {
            // 默认显示第一页的数据
            pageVO.setToPageNo(1);
            orderList = ordersService.getCustomerOrderList(userId, 0, 5);
        } else {
            pageVO.setToPageNo(page);
            orderList = ordersService.getCustomerOrderList(userId,(page - 1) * 5, 5);
        }

        logger.info("orderList = " + orderList);
        model.addAttribute("orderList", orderList);
        model.addAttribute("pageVo", pageVO);

        return "customer/orderList";
    }

    /**
     * 退票操作
     * @param orderId
     * @param model
     * @return
     */
    @PostMapping("/quitTicket")
    @Transactional
    public String quitTicket(@RequestParam("orderId") Integer orderId, Model model) {

        // 查看当前时间是否允许退票
        Map<String, Object> orderInfo = ordersService.getOrderInfo(orderId);
        long filmStartTime = ((Date)orderInfo.get("film_time")).getTime();
        logger.info("filmStartTime = " + filmStartTime);
        // 获取当前时间
        long now = System.currentTimeMillis();

        // 开播前30分钟不允许退票
        if((now + 30 * 60 * 1000) >= filmStartTime ) {
            model.addAttribute("msg", "开播前30分钟不允许退票");
            return "customer/orderList";
        }

        // 执行退票操作
        Integer num = ordersService.backTickets(orderId);
        if(num != 1) {
            model.addAttribute("msg", "退票失败");
            return "customer/orderList";
        }

        // 恢复座位为可选状态
        Integer seatRow = Integer.parseInt(orderInfo.get("film_seat_row").toString());
        Integer seatCol = Integer.parseInt(orderInfo.get("film_seat_col").toString());
        Integer filmId = Integer.parseInt(orderInfo.get("film_id").toString());
        FilmSeat filmSeat = new FilmSeat();
        filmSeat.setFilmId(filmId);
        filmSeat.setFilmSeatCol(seatCol);
        filmSeat.setFilmSeatRow(seatRow);
        filmSeat.setFilmSeatIsActive(1);
        filmSeatService.updateSeat(filmSeat);

        model.addAttribute("msg", "退票成功");
        return "customer/orderList";
    }

    /**
     * 获取重置密码页面
     * @return
     */
    @GetMapping("/passwordReset")
    public String passwordRestUI() {

        return "customer/passwordReset";
    }
}
