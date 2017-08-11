/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : device

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-08-07 15:25:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) DEFAULT NULL COMMENT '用户id',
  `phone` varchar(18) DEFAULT NULL COMMENT '电话',
  `deviceType` varchar(40) DEFAULT NULL COMMENT '设备型号',
  `miToken` varchar(50) DEFAULT NULL COMMENT '设备token',
  `gcmToken` varchar(50) DEFAULT NULL COMMENT 'gcm设备token',
  `apnsToken` varchar(50) DEFAULT NULL COMMENT 'Apple 推送token',
  `imei` varchar(50) NOT NULL COMMENT 'imei',
  `osType` tinyint(2) NOT NULL COMMENT '设备类型 1：Android 2：ios',
  `osVersion` varchar(10) DEFAULT NULL,
  `appCode` tinyint(4) NOT NULL COMMENT '应用标识,首约app乘客端 1001；首约app司机端 1002',
  `appVersion` varchar(20) NOT NULL COMMENT '应用版本',
  `antFingerprint` varchar(50) NOT NULL COMMENT '蚂蚁金服指纹',
  `isAcceptPush` tinyint(2) NOT NULL COMMENT '是否接收推送消息 1：是 0 否',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_antFingerprint` (`antFingerprint`) USING BTREE,
  KEY `ind_phone` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
SET FOREIGN_KEY_CHECKS=1;
