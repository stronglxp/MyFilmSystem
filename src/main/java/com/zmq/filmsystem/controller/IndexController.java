package com.zmq.filmsystem.controller;

import com.zmq.filmsystem.entity.Film;
import com.zmq.filmsystem.entity.FilmSeat;
import com.zmq.filmsystem.entity.Orders;
import com.zmq.filmsystem.entity.User;
import com.zmq.filmsystem.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 首页的逻辑
 */
@Controller
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private CanemaService canemaService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private FilmSeatService filmSeatService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrdersService ordersService;

    /**
     * 获取商家首页html
     * @param model
     * @return
     */
    @GetMapping("/business/index")
    public String getBusinessIndexPage(Model model) {

        // 获取所有电影院
        List<Map<String, Object>> canemaList = canemaService.getCanemaList();

        model.addAttribute("canemaList", canemaList);

        return "business/index";
    }

    /**
     * 获取顾客首页html
     * @return
     */
    @GetMapping("/customer/index")
    public String getCustomerIndexPage(Model model) {

        // 获取所有电影院
        List<Map<String, Object>> canemaList = canemaService.getCanemaList();

        model.addAttribute("canemaList", canemaList);

        return "customer/index";
    }

    /**
     * 搜索电影院
     * @param canemaName
     * @param model
     * @param request
     * @return
     */
    @PostMapping("/search/canema")
    public String searchCanema(@RequestParam("canemaName") String canemaName, Model model,
                               HttpServletRequest request) {

        String type = null;
        if(request.getParameter("type") != null) {
            // 获取链接中给的type
            type = request.getParameter("type");
        }

        // 调用方法进行查询
        List<Map<String, Object>> canemaList = canemaService.searchCanema(canemaName);
        model.addAttribute("canemaList", canemaList);

        // 区分是商家还是顾客发起的请求
        if("business".equals(type)) {
            return "business/index";
        } else {
            return "customer/index";
        }

    }


    /**
     * 获取某个影院所有电影列表
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/film/list")
    public String getFilmList(HttpServletRequest request, Model model) {

        String type = null;
        Integer canemaId = null;
        if(request.getParameter("type") != null) {
            // 获取链接中给的type
            type = request.getParameter("type");
        }

        if(request.getParameter("canemaId") != null) {
            // 获取链接中给的canemaId
            canemaId = Integer.parseInt(request.getParameter("canemaId"));
        }

        // 调用方法进行查询
        List<Map<String, Object>> filmList = filmService.getCanemaFilmList(canemaId);
        model.addAttribute("filmList", filmList);
        model.addAttribute("canemaId", canemaId);

        // 判断当前时间是否大于电影放映时间
        for(int i = 0; i < filmList.size(); i++) {
            Map<String, Object> film = filmList.get(i);
            long now = System.currentTimeMillis();
            if(now >= ((Date)film.get("film_time")).getTime()) {
                film.put("is_out", 1);
            } else {
                film.put("is_out", 0);
            }

            filmList.remove(i);
            filmList.add(i, film);
        }

        // 区分是商家还是顾客发起的请求
        if("business".equals(type)) {
            return "business/showFilm";
        } else {
            return "customer/showFilm";
        }
    }

    /**
     * 搜索电影
     * @param filmName
     * @param model
     * @param request
     * @return
     */
    @PostMapping("/search/film")
    public String searchFilm(@RequestParam("filmName") String filmName, Model model,
                               HttpServletRequest request) {

        String type = null;
        Integer canemaId = null;
        if(request.getParameter("type") != null) {
            // 获取链接中给的type
            type = request.getParameter("type");
        }

        if(request.getParameter("canemaId") != null) {
            // 获取链接中给的canemaId
            canemaId = Integer.parseInt(request.getParameter("canemaId"));
        }

        // 调用方法进行查询
        List<Map<String, Object>> filmList = filmService.searchCanemaFilm(canemaId, filmName);
        model.addAttribute("filmList", filmList);
        model.addAttribute("canemaId", canemaId);

        // 区分是商家还是顾客发起的请求
        if("business".equals(type)) {
            return "business/showFilm";
        } else {
            return "customer/showFilm";
        }

    }

    /**
     * 进入某部电影的选座购票页面
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/film/seat/list")
    public String getFilmSeat(HttpServletRequest request, Model model) {

        String type = null;
        Integer filmId = null;
        if(request.getParameter("type") != null) {
            // 获取链接中给的type
            type = request.getParameter("type");
        }

        if(request.getParameter("filmId") != null) {
            // 获取链接中给的filmId
            filmId = Integer.parseInt(request.getParameter("filmId"));
        }

        // 获取该电影的信息
        Film film = filmService.getFilmInfoById(filmId);
        // 获取该电影已被预订的座位
        List<Map<String, Object>> unSeatList = filmSeatService.getUnAcSeat(filmId);

        // 转换为二维数组
        Integer[][] seatArr = new Integer[unSeatList.size()][2];
        for (int i = 0; i < unSeatList.size(); i++) {
            Map<String, Object> map = unSeatList.get(i);
            seatArr[i][0] = Integer.parseInt(map.get("film_seat_row").toString());
            seatArr[i][1] = Integer.parseInt(map.get("film_seat_col").toString());
        }
        logger.info("seatArr = " + seatArr);

        model.addAttribute("film", film);
        model.addAttribute("seatArr", seatArr);
        model.addAttribute("filmId", filmId);

        // 区分是商家还是顾客发起的请求
        if("business".equals(type)) {
            return "business/showFilmSeat";
        } else {
            return "customer/showFilmSeat";
        }

    }

    /**
     * 购票操作
     * @param arr
     * @return
     */
    @PostMapping("/pick/ticket")
    @ResponseBody
    @Transactional
    public Integer pickTicket(@RequestBody Object[][] arr, HttpServletRequest request) {

        // 获取当前登录的顾客名称
        Subject subject = SecurityUtils.getSubject();
        String customerName = (String)subject.getPrincipal();

        // 通过顾客名称获取顾客信息
        User userInfo = userService.getUserInfo(customerName);
        Integer result = 0;

        // 用户是否存在
        if(userInfo == null) {
            result = -2;
            return result;
        }

        Integer userId = userInfo.getUserId();

        Integer filmId = null;
        if(request.getParameter("filmId") != null) {
            // 获取链接中给的filmId
            filmId = Integer.parseInt(request.getParameter("filmId").toString());
        }

        FilmSeat filmSeat = new FilmSeat();
        filmSeat.setFilmId(filmId);
        filmSeat.setFilmSeatIsActive(0);
        logger.info("arr " + arr[0][0]);
        for (int i = 0; i < arr.length; i++) {
            String seat = arr[i][0].toString();
            String[] colRow = seat.split(",");
            Integer row = Integer.parseInt(colRow[0]);
            Integer col = Integer.parseInt(colRow[1]);
            filmSeat.setFilmSeatRow(row);
            filmSeat.setFilmSeatCol(col);
            // 调用方法
            Integer num = filmSeatService.updateSeat(filmSeat);
            if(num != 1) {
                result = -1;
                break;
            }
            // 往orders表加记录
            Orders orders = new Orders();
            // 随机生成orderId

            orders.setUserId(userId);
            orders.setFilmId(filmId);
            orders.setFilmSeatCol(col);
            orders.setFilmSeatRow(row);
            ordersService.addOrder(orders);
        }

        return result;

    }
}
