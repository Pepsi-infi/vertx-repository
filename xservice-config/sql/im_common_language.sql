/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : admin

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-09-01 15:37:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for im_common_language
-- ----------------------------
DROP TABLE IF EXISTS `im_common_language`;
CREATE TABLE `im_common_language` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(50) NOT NULL COMMENT '内容',
  `weight` int(10) NOT NULL COMMENT '权重',
  `type` int(4) NOT NULL COMMENT '常用语类型 1：司机常用语 2：乘客常用语',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
SET FOREIGN_KEY_CHECKS=1;
