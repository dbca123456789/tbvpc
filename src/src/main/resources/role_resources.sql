/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : tbvp

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-09-01 21:20:06
*/

SET FOREIGN_KEY_CHECKS=0;

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
-- Records of role_resources
-- ----------------------------
INSERT INTO `role_resources` VALUES ('3', '2');
INSERT INTO `role_resources` VALUES ('3', '3');
INSERT INTO `role_resources` VALUES ('3', '4');
INSERT INTO `role_resources` VALUES ('3', '5');
INSERT INTO `role_resources` VALUES ('3', '6');
INSERT INTO `role_resources` VALUES ('3', '7');
INSERT INTO `role_resources` VALUES ('3', '8');
INSERT INTO `role_resources` VALUES ('3', '9');
