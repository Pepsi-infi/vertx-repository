/*
Navicat MySQL Data Transfer

Source Server         : （测试消息中心）10.10.10.178
Source Server Version : 50626
Source Host           : 10.10.10.178:3306
Source Database       : device

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2017-08-15 15:16:45
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
  `channel` int(4) DEFAULT NULL COMMENT '2：GCM 3：XIAOMI 4：APNS',
  `deviceToken` varchar(255) DEFAULT NULL COMMENT '对应渠道设备token',
  `osType` tinyint(2) DEFAULT NULL COMMENT '设备类型 1：Android 2：ios',
  `osVersion` varchar(10) DEFAULT NULL COMMENT '操作系统版本',
  `appCode` int(4) NOT NULL COMMENT '应用标识,首约app乘客端 1001；首约app司机端 1002',
  `appVersion` varchar(20) DEFAULT NULL COMMENT '应用版本（数字）',
  `antFingerprint` varchar(255) DEFAULT NULL COMMENT '蚂蚁金服指纹',
  `isAcceptPush` tinyint(2) DEFAULT NULL COMMENT '是否接收推送消息 1：是 0 否',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_phone_deviceToken` (`phone`,`deviceToken`) USING BTREE,
  KEY `idx_antFingerprint` (`antFingerprint`) USING BTREE,
  KEY `idx_phone_channel` (`phone`,`channel`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1191766 DEFAULT CHARSET=utf8;
SET FOREIGN_KEY_CHECKS=1;
