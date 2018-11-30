/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : tbvp

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-09-04 20:45:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` mediumint(12) NOT NULL AUTO_INCREMENT,
  `addressId` int(12) DEFAULT NULL,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话号码',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `last_time` datetime DEFAULT NULL COMMENT '用户最近访问时间',
  `sex` int(1) DEFAULT NULL COMMENT '0男，1女',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `idCard` varchar(30) DEFAULT NULL COMMENT '身份证信息',
  `enable` int(1) DEFAULT '0' COMMENT '账户是否能用',
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '省份',
  PRIMARY KEY (`id`),
  KEY `addressId` (`addressId`)
) ENGINE=InnoDB AUTO_INCREMENT=1503329 DEFAULT CHARSET=utf8;
