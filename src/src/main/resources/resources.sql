/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : tbvp

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-09-01 21:19:56
*/

SET FOREIGN_KEY_CHECKS=0;

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
-- Records of resources
-- ----------------------------
INSERT INTO `resources` VALUES ('1', '系统设置', '/system', '0', '0', '1');
INSERT INTO `resources` VALUES ('2', 'manager首页', '/managerPage', '1', '1', '2');
INSERT INTO `resources` VALUES ('3', '用户管理', '/usersPage', '1', '1', '3');
INSERT INTO `resources` VALUES ('4', 'manager首页', '/managerPage', null, '1', '4');
INSERT INTO `resources` VALUES ('5', '添加manager', '/addmanager', null, '2', '5');
INSERT INTO `resources` VALUES ('6', '获取userSexJson', '/userJson/getSexJson', null, '2', '6');
INSERT INTO `resources` VALUES ('7', '获取userMapJson', '/userJson/getMapJson', null, '2', '7');
INSERT INTO `resources` VALUES ('8', '获取userTravelMode', '/userJson/getTravelMode', null, '2', '8');
INSERT INTO `resources` VALUES ('9', '获取userAge', '/userJson/getAge', null, '2', '9');
