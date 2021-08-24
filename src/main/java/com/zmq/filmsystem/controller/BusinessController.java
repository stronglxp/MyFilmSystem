package com.zmq.filmsystem.controller;

import com.zmq.filmsystem.entity.*;
import com.zmq.filmsystem.service.*;
import com.zmq.filmsystem.service.impl.QiniuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商家controller
 */
@Controller
@RequestMapping("/business")
public class BusinessController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private QiniuService qiniuService;

    @Autowired
    private CanemaService canemaService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private FilmSeatService filmSeatService;

    @Autowired
    private OrdersService ordersService;

    /**
     * 返回电影列表
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/film/list")
    public String filmList(Model model, HttpServletRequest request) {

        Integer page = null;
        if(request.getParameter("page") != null) {
            // 获取链接中给的page
            page = Integer.parseInt(request.getParameter("page"));
        }

        // 获取当前登录的商家id
        Subject subject = SecurityUtils.getSubject();
        String businessName = (String)subject.getPrincipal();

        // 通过商家id获取商家信息
        User userInfo = userService.getUserInfo(businessName);

        // 用户是否存在
        if(userInfo == null) {
            model.addAttribute("msg", "不明用户");
            return "redirect:/login";
        }

        Integer canemaId = userInfo.getCanemaId();
        logger.info("canemaId = " + canemaId);

        // 判断是否有关联的影院
        if(canemaId == null) {
            model.addAttribute("isAdd", 0);
        } else {

            List<Map<String, Object>> filmList = new ArrayList<>();
            // 页码对象
            PageVO pageVO = new PageVO();
            // 设置总页数
            pageVO.setTotalCount(filmService.getFilmNum(canemaId));
            if(page == null || page == 0) {
                // 默认显示第一页的数据
                pageVO.setToPageNo(1);
                filmList = filmService.getFilmList(canemaId, 0, 5);
            } else {
                pageVO.setToPageNo(page);
                filmList = filmService.getFilmList(canemaId,(page - 1) * 5, 5);
            }

            logger.info("filmList = " + filmList);
            model.addAttribute("filmList", filmList);
            model.addAttribute("pageVo", pageVO);
            model.addAttribute("canemaId", canemaId);
        }

        return "business/filmList";
    }

    /**
     * 添加影院信息
     * @param file
     * @param canema
     * @param model
     * @return
     */
    @PostMapping("/addCanema")
    @Transactional
    public String addCanema(@RequestParam("file") MultipartFile file, Canema canema,
                            Model model) {

        if(file.isEmpty()) {
            model.addAttribute("msg", "文件为空");
        }

        try {

            String fileUrl = qiniuService.saveImage(file);
            canema.setCanemaImgUrl(fileUrl);

            // 往canema表添加一条记录
            Integer num = canemaService.addCanema(canema);

            // 添加成功，则和user表进行关联
            if(num == 1) {
                // 获取当前登录的商家id
                Subject subject = SecurityUtils.getSubject();
                String businessName = (String)subject.getPrincipal();

                User user = new User();
                user.setCanemaId(canema.getCanemaId());
                user.setUserName(businessName);

                userService.updateCanema(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("msg", "添加失败!");
            return "business/filmList";
        }

        model.addAttribute("alert", "添加成功!");

        return "business/filmList";

    }

    /**
     * 获取影院信息页面
     * @param model
     * @return
     */
    @GetMapping("/canema/info")
    public String getCanemaInfo(Model model) {

        // 获取当前登录的商家id
        Subject subject = SecurityUtils.getSubject();
        String businessName = (String)subject.getPrincipal();

        // 获取商家关联的影院
        User user = userService.getUserInfo(businessName);
        if(user == null) {
            model.addAttribute("msg", "用户错误");
            return "business/filmList";
        }

        // 获取影院信息
        Integer canemaId = user.getCanemaId();
        if (canemaId == null) {
            model.addAttribute("msg", "请先添加影院");
            return "business/filmList";
        }
        Canema canema = canemaService.getCanemaInfo(canemaId);

        model.addAttribute("canema", canema);
        return "business/canemaInfo";
    }

    /**
     * 修改影院信息
     * @param canema
     * @param model
     * @return
     */
    @PostMapping("/editCanema")
    public String editCanema(Canema canema, Model model) {

        // 查看影院是否存在
        Canema c = canemaService.getCanemaInfo(canema.getCanemaId());
        if(c == null) {
            model.addAttribute("msg", "影院信息错误");
            return "business/canemaInfo";
        }

        Integer num = canemaService.updateCanema(canema);
        if(num != 1) {
            model.addAttribute("msg", "更新失败");
            return "business/canemaInfo";
        }

        model.addAttribute("msg", "更新成功");
        return "business/canemaInfo";
    }

    /**
     * 添加电影信息
     * @param file
     * @param film
     * @param model
     * @return
     */
    @PostMapping("/addFilm")
    @Transactional
    public String addFilm(@RequestParam("file") MultipartFile file, Film film,
                          Model model) {

        if(file.isEmpty()) {
            model.addAttribute("msg", "文件为空");
        }

        try {

            logger.info("time = " + film.getFilmTime());

            // 往film表添加一条记录
            Integer num = filmService.addFilm(film);

            // 添加成功，则再插入image表
            if(num == 1) {

                String fileUrl = qiniuService.saveImage(file);
                Integer filmId = film.getFilmId();
                logger.error("fileUrl = " + fileUrl);
                Image image = new Image();
                image.setImgUrl(fileUrl);
                image.setFilmId(filmId);

                imageService.addImage(image);

                // film_seat添加座位
                List<Map<Integer, Integer>> data = new ArrayList<>();
                // 6排10座
                for(int i = 1; i <= 10; i++) {
                    for(int j = 1; j < 10; j++) {
                        if(i == 3) {
                            continue;
                        }
                        Map<Integer, Integer> map = new HashMap<>();
                        map.put(i, j);
                        data.add(map);
                    }
                }

                // 调用方法插入
                filmSeatService.insertSeat(data, filmId);
            }
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("msg", "添加失败!");
            return "business/filmList";
        }

        model.addAttribute("msg", "添加成功!");

        return "business/filmList";
    }

    /**
     * 搜索电影
     * @param film
     * @param model
     * @return
     */
    @PostMapping("/selectFilm")
    public String selectFilm(Film film, Model model) {

        // 获取当前登录的商家id
        Subject subject = SecurityUtils.getSubject();
        String businessName = (String)subject.getPrincipal();

        // 获取商家关联的影院
        User user = userService.getUserInfo(businessName);
        if(user == null) {
            model.addAttribute("msg", "用户错误");
            return "business/filmList";
        }

        // 获取影院信息
        Integer canemaId = user.getCanemaId();
        film.setCanemaId(canemaId);

        // 查询
        List<Map<String, Object>> filmList = filmService.selectFilm(film);

        model.addAttribute("filmList", filmList);

        return "business/filmList";
    }

    /**
     * 删除一部电影
     * @param filmId
     * @param model
     * @return
     */
    @PostMapping("/removeFilm")
    @Transactional
    public String removeFilm(@RequestParam("filmId") Integer filmId, Model model) {

        // 删除一部电影film表
        Integer num = filmService.deleteFilm(filmId);

        // 删除成功再执行下面的操作
        if(num == 1) {
            // 删除image表
            imageService.deleteImage(filmId);
            // 删除orders表
            ordersService.deleteOrders(filmId);
            // 删除film_seat表
            filmSeatService.deleteSeat(filmId);

            model.addAttribute("msg", "删除成功");
        } else {
            model.addAttribute("msg", "删除失败");
        }

        return "business/filmList";

    }

    /**
     * 更新电影信息
     * @param model
     * @param film
     * @return
     */
    @PostMapping("/updateFilm")
    public String updateFilm(Model model, Film film) {

        // 查看film是否存在
        Integer film_id = filmService.getFilmById(film.getFilmId());
        if(film_id == null) {
            model.addAttribute("msg", "不存在该电影");
            return "business/filmList";
        }

        // 更新电影信息
        Integer num = filmService.updateFilm(film);

        if(num != 1) {
            model.addAttribute("msg", "更新失败");
            return "business/filmList";
        }

        model.addAttribute("msg", "更新成功");
        return "business/filmList";
    }

    /**
     * 获取订单列表页面
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/order/list")
    public String getOrderPage(Model model, HttpServletRequest request) {

        Integer page = null;
        if(request.getParameter("page") != null) {
            // 获取链接中给的page
            page = Integer.parseInt(request.getParameter("page"));
        }

        // 获取当前登录的商家id
        Subject subject = SecurityUtils.getSubject();
        String businessName = (String)subject.getPrincipal();

        // 通过商家id获取商家信息
        User userInfo = userService.getUserInfo(businessName);

        // 用户是否存在
        if(userInfo == null) {
            model.addAttribute("msg", "不明用户");
            return "redirect:/login";
        }

        Integer canemaId = userInfo.getCanemaId();

        List<Map<String, Object>> orderList = new ArrayList<>();
        // 页码对象
        PageVO pageVO = new PageVO();
        // 设置总页数
        pageVO.setTotalCount(ordersService.getCanemaOrderNum(canemaId));
        if(page == null || page == 0) {
            // 默认显示第一页的数据
            pageVO.setToPageNo(1);
            orderList = ordersService.getCanemaOrderList(canemaId, 0, 5);
        } else {
            pageVO.setToPageNo(page);
            orderList = ordersService.getCanemaOrderList(canemaId,(page - 1) * 5, 5);
        }

        logger.info("orderList = " + orderList);
        model.addAttribute("orderList", orderList);
        model.addAttribute("pageVo", pageVO);

        return "business/orderList";
    }

    /**
     * 获取重置密码页面
     * @return
     */
    @GetMapping("/passwordReset")
    public String passwordRestUI() {

        return "business/passwordReset";
    }
}
