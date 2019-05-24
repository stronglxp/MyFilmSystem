/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : film_system

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 24/05/2019 16:59:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for canema
-- ----------------------------
DROP TABLE IF EXISTS `canema`;
CREATE TABLE `canema`  (
  `canema_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '影院id',
  `canema_name` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '影院名称',
  `canema_address` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '影院地址',
  `canema_line` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '影院联系方式',
  `canema_img_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '影院的招牌',
  PRIMARY KEY (`canema_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for film
-- ----------------------------
DROP TABLE IF EXISTS `film`;
CREATE TABLE `film`  (
  `film_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '电影id',
  `film_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '电影名称',
  `film_info` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '电影简介',
  `film_price` float(5, 2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '电影价格',
  `film_time` date NOT NULL DEFAULT '2019-05-20' COMMENT '电影放映时间',
  `canema_id` int(10) UNSIGNED NOT NULL COMMENT '电影属于哪个影院',
  PRIMARY KEY (`film_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for film_seat
-- ----------------------------
DROP TABLE IF EXISTS `film_seat`;
CREATE TABLE `film_seat`  (
  `film_id` int(10) UNSIGNED NOT NULL COMMENT '电影id',
  `film_seat_row` tinyint(3) UNSIGNED NOT NULL COMMENT '该电影的座位行号',
  `film_seat_col` tinyint(3) UNSIGNED NOT NULL COMMENT '该电影的座位列号',
  `film_seat_is_active` tinyint(2) UNSIGNED NOT NULL DEFAULT 1 COMMENT '对于的这个座位是否可以被预定，默认1可以，0不可以',
  PRIMARY KEY (`film_id`, `film_seat_row`, `film_seat_col`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for image
-- ----------------------------
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image`  (
  `image_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '图片id',
  `img_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片地址',
  `film_id` int(10) NULL DEFAULT NULL COMMENT '图片所属电影',
  PRIMARY KEY (`image_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `order_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `user_id` int(10) UNSIGNED NOT NULL COMMENT '用户id',
  `film_id` int(10) UNSIGNED NOT NULL COMMENT '电影id',
  `film_seat_row` tinyint(3) UNSIGNED NOT NULL COMMENT '预定该电影的座位行号',
  `film_seat_col` tinyint(3) UNSIGNED NOT NULL COMMENT '预定该电影的座位列号',
  `order_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '下单时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `permissions` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '权限',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户登录名称',
  `user_salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '盐值',
  `user_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户密码',
  `role_id` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户角色',
  `canema_id` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '该用户关联的影院，可以为空，因为顾客不需要',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
