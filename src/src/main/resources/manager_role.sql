/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : tbvp

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-09-01 21:19:43
*/

SET FOREIGN_KEY_CHECKS=0;

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
-- Records of manager_role
-- ----------------------------
INSERT INTO `manager_role` VALUES ('1', '3');
