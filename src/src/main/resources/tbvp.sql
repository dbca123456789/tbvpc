/*
Navicat MySQL Data Transfer

Source Server         : twodog
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : tbvp

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-09-06 17:25:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for accessrecord
-- ----------------------------
DROP TABLE IF EXISTS `accessrecord`;
CREATE TABLE `accessrecord` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `userId` int(12) DEFAULT NULL COMMENT '为空，表示游览用户非平台注册用户',
  `accessProduceId` int(12) DEFAULT NULL COMMENT '访问产品的id',
  `accessTool` int(2) DEFAULT NULL COMMENT '访问工具，0表示省油灯，1表示通过携程，2表示飞猪，3表示其它等',
  `accessTime` datetime DEFAULT NULL COMMENT '购买时间，为空表示访问时间',
  `totalTime` int(3) DEFAULT NULL COMMENT '访问的次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1101001 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `addressId` int(12) NOT NULL COMMENT 'Id',
  `country` varchar(50) NOT NULL COMMENT '国家',
  `province` varchar(50) DEFAULT NULL COMMENT '所在省',
  `city` varchar(50) DEFAULT NULL COMMENT '所在市',
  `deati` varchar(255) DEFAULT NULL COMMENT '详细地址',
  PRIMARY KEY (`addressId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for agent
-- ----------------------------
DROP TABLE IF EXISTS `agent`;
CREATE TABLE `agent` (
  `id` int(11) NOT NULL COMMENT '代理ID',
  `username` varchar(50) NOT NULL COMMENT '代理用户名',
  `password` varchar(255) NOT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '代理姓名',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话号码',
  `addressId` int(12) DEFAULT NULL COMMENT '用户地址',
  `register_time` datetime NOT NULL COMMENT '注册时间',
  `last_time` datetime DEFAULT NULL,
  `sex` int(1) DEFAULT NULL COMMENT '0男，1女',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `idCard` varchar(30) DEFAULT NULL COMMENT '身份证信息',
  `Place` varchar(30) DEFAULT NULL COMMENT '代理地区',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for buyrecord
-- ----------------------------
DROP TABLE IF EXISTS `buyrecord`;
CREATE TABLE `buyrecord` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `userId` int(12) DEFAULT NULL COMMENT '为空，表示游览用户非平台注册用户',
  `buyProduceId` int(12) DEFAULT NULL COMMENT '购买的产品id',
  `buyTool` int(2) DEFAULT NULL COMMENT '购买工具，0表示省油灯，1表示通过携程，2表示飞猪，3表示其它等',
  `buyTime` datetime DEFAULT NULL COMMENT '购买时间',
  `buy_price` double(30,0) DEFAULT NULL COMMENT '所花金额，单位元',
  `totalTime` int(3) DEFAULT NULL COMMENT '访问的次数',
  `buyCount` int(6) DEFAULT NULL COMMENT '购买数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20001 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for manager
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` int(1) NOT NULL COMMENT '0表示管理员，1表示用户',
  `agentName` varchar(100) DEFAULT NULL COMMENT '代理处名称',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `profession` varchar(50) DEFAULT NULL COMMENT '职称',
  `phone` varchar(20) DEFAULT '' COMMENT '电话号码',
  `addressId` int(12) DEFAULT NULL COMMENT '用户地址',
  `register_time` datetime NOT NULL COMMENT '注册时间',
  `last_time` datetime DEFAULT NULL COMMENT '用户最近访问时间',
  `idCard` varchar(30) DEFAULT NULL COMMENT '身份证号码',
  `produceId` int(12) DEFAULT NULL COMMENT '产品信息id',
  `enable` int(1) unsigned zerofill NOT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`),
  KEY `addressId` (`addressId`),
  KEY `produceId` (`produceId`),
  CONSTRAINT `manager_ibfk_1` FOREIGN KEY (`addressId`) REFERENCES `address` (`addressId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for manager_role
-- ----------------------------
DROP TABLE IF EXISTS `manager_role`;
CREATE TABLE `manager_role` (
  `managerId` int(11) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
  KEY `userId` (`managerId`),
  KEY `roleId` (`roleId`),
  CONSTRAINT `manager_role_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mapperdemo
-- ----------------------------
DROP TABLE IF EXISTS `mapperdemo`;
CREATE TABLE `mapperdemo` (
  `user_id` varchar(50) NOT NULL COMMENT '用户id,字符型，用于唯一标识每个用户',
  `user_name` varchar(100) NOT NULL COMMENT '用户名字，用于登陆',
  `user_email` varchar(50) NOT NULL COMMENT '用户邮箱，用于登陆和找回密码',
  `user_phone` varchar(50) NOT NULL COMMENT '用户手机，用于登陆和找回密码',
  `user_password` varchar(50) NOT NULL COMMENT '用户密码，用于登陆',
  `user_delete` int(2) DEFAULT '0' COMMENT '假删除，0代表为删除，1代表删除',
  `user_image` varchar(500) DEFAULT NULL COMMENT '用户图片',
  `user_created_time` datetime NOT NULL COMMENT '用户创建时间',
  `user_deleted_time` datetime DEFAULT NULL COMMENT '用户删除时间',
  PRIMARY KEY (`user_name`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Table structure for produce
-- ----------------------------
DROP TABLE IF EXISTS `produce`;
CREATE TABLE `produce` (
  `produceId` mediumint(12) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL COMMENT '旅游路线主题名称',
  `scenics` varchar(500) DEFAULT NULL COMMENT '路线所包含所有景点，用“青秀山，大明山”这种格式',
  `waySId` char(60) DEFAULT NULL COMMENT '出行方式，中间用逗号隔开',
  `totalAmount` double(60,0) DEFAULT NULL COMMENT '路线原价，单位元',
  `price` double(60,0) DEFAULT NULL COMMENT '路线现价',
  `Images` varchar(255) DEFAULT NULL COMMENT '路线的特色图片，多张图片用逗号隔开',
  `description` varchar(1000) DEFAULT NULL COMMENT '对路线的文字描述',
  `start_time` datetime DEFAULT NULL COMMENT '路线运营的初始时间',
  `End_time` datetime DEFAULT NULL COMMENT '路线运营的结束时间',
  PRIMARY KEY (`produceId`)
) ENGINE=InnoDB AUTO_INCREMENT=1456 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for region
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
  `id` int(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `parent_id` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for resources
-- ----------------------------
DROP TABLE IF EXISTS `resources`;
CREATE TABLE `resources` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '��Դ����',
  `resUrl` varchar(255) DEFAULT NULL COMMENT '��Դurl',
  `type` int(11) DEFAULT NULL COMMENT '��Դ����   1:�˵�    2����ť',
  `parentId` int(11) DEFAULT NULL COMMENT '����Դ',
  `sort` int(11) DEFAULT NULL COMMENT '����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleDesc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role_resources
-- ----------------------------
DROP TABLE IF EXISTS `role_resources`;
CREATE TABLE `role_resources` (
  `roleId` int(11) NOT NULL,
  `resourcesId` int(11) NOT NULL,
  PRIMARY KEY (`roleId`,`resourcesId`),
  KEY `resourcesId` (`resourcesId`),
  CONSTRAINT `role_resources_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`),
  CONSTRAINT `role_resources_ibfk_2` FOREIGN KEY (`resourcesId`) REFERENCES `resources` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for super_manager
-- ----------------------------
DROP TABLE IF EXISTS `super_manager`;
CREATE TABLE `super_manager` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` int(1) NOT NULL COMMENT '0表示管理员，1表示用户',
  `agentName` varchar(100) DEFAULT NULL COMMENT '代理处名称',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `profession` varchar(50) DEFAULT NULL COMMENT '职称',
  `phone` varchar(20) DEFAULT '' COMMENT '电话号码',
  `addressId` int(12) DEFAULT NULL COMMENT '用户地址',
  `register_time` datetime NOT NULL COMMENT '注册时间',
  `last_time` datetime DEFAULT NULL COMMENT '用户最近访问时间',
  `idCard` varchar(30) DEFAULT NULL COMMENT '身份证号码',
  `produceId` int(12) DEFAULT NULL COMMENT '产品信息id',
  PRIMARY KEY (`id`),
  KEY `addressId` (`addressId`),
  KEY `produceId` (`produceId`),
  CONSTRAINT `super_manager_ibfk_1` FOREIGN KEY (`addressId`) REFERENCES `address` (`addressId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `province` varchar(255) DEFAULT NULL COMMENT '省份',
  PRIMARY KEY (`id`),
  KEY `addressId` (`addressId`)
) ENGINE=InnoDB AUTO_INCREMENT=1503329 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `userId` int(11) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
  KEY `userId` (`userId`),
  KEY `roleId` (`roleId`),
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ways
-- ----------------------------
DROP TABLE IF EXISTS `ways`;
CREATE TABLE `ways` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `startPlace` varchar(100) DEFAULT NULL COMMENT '起始地',
  `endPlace` varchar(100) DEFAULT NULL COMMENT '目的地',
  `amount` double(255,0) NOT NULL,
  `way` int(1) NOT NULL COMMENT '0飞机，1火车，2汽车，3自驾，4其它',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000001 DEFAULT CHARSET=utf8;
