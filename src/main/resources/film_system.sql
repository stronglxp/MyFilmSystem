/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50733
Source Host           : localhost:3306
Source Database       : film_system

Target Server Type    : MYSQL
Target Server Version : 50733
File Encoding         : 65001

Date: 2021-08-24 23:18:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for canema
-- ----------------------------
DROP TABLE IF EXISTS `canema`;
CREATE TABLE `canema` (
  `canema_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '影院id',
  `canema_name` varchar(1000) COLLATE utf8_bin NOT NULL COMMENT '影院名称',
  `canema_address` varchar(1000) COLLATE utf8_bin NOT NULL COMMENT '影院地址',
  `canema_line` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '影院联系方式',
  `canema_img_url` varchar(500) COLLATE utf8_bin NOT NULL COMMENT '影院的招牌',
  PRIMARY KEY (`canema_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of canema
-- ----------------------------

-- ----------------------------
-- Table structure for film
-- ----------------------------
DROP TABLE IF EXISTS `film`;
CREATE TABLE `film` (
  `film_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '电影id',
  `film_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '电影名称',
  `film_info` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '电影简介',
  `film_price` float(5,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '电影价格',
  `film_time` date NOT NULL DEFAULT '2019-05-20' COMMENT '电影放映时间',
  `canema_id` int(10) unsigned NOT NULL COMMENT '电影属于哪个影院',
  PRIMARY KEY (`film_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of film
-- ----------------------------

-- ----------------------------
-- Table structure for film_seat
-- ----------------------------
DROP TABLE IF EXISTS `film_seat`;
CREATE TABLE `film_seat` (
  `film_id` int(10) unsigned NOT NULL COMMENT '电影id',
  `film_seat_row` tinyint(3) unsigned NOT NULL COMMENT '该电影的座位行号',
  `film_seat_col` tinyint(3) unsigned NOT NULL COMMENT '该电影的座位列号',
  `film_seat_is_active` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '对于的这个座位是否可以被预定，默认1可以，0不可以',
  PRIMARY KEY (`film_id`,`film_seat_row`,`film_seat_col`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of film_seat
-- ----------------------------

-- ----------------------------
-- Table structure for image
-- ----------------------------
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image` (
  `image_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '图片id',
  `img_url` varchar(500) NOT NULL COMMENT '图片地址',
  `film_id` int(10) DEFAULT NULL COMMENT '图片所属电影',
  PRIMARY KEY (`image_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of image
-- ----------------------------

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `order_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户id',
  `film_id` int(10) unsigned NOT NULL COMMENT '电影id',
  `film_seat_row` tinyint(3) unsigned NOT NULL COMMENT '预定该电影的座位行号',
  `film_seat_col` tinyint(3) unsigned NOT NULL COMMENT '预定该电影的座位列号',
  `order_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '下单时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `permissions` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '权限',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'admin', '');
INSERT INTO `role` VALUES ('2', 'business', '');
INSERT INTO `role` VALUES ('3', 'customer', '');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '用户登录名称',
  `user_salt` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '盐值',
  `user_password` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '用户密码',
  `role_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户角色',
  `canema_id` int(10) unsigned DEFAULT NULL COMMENT '该用户关联的影院，可以为空，因为顾客不需要',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;