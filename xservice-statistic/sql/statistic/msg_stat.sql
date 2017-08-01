/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : statistic

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-08-01 10:37:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for msg_stat
-- ----------------------------
DROP TABLE IF EXISTS `msg_stat`;
CREATE TABLE `msg_stat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `msgId` varchar(45) NOT NULL DEFAULT '0' COMMENT '消息id',
  `statTime` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '统计时间',
  `sendSum` bigint(20) DEFAULT NULL COMMENT '发送总数',
  `sendAndroidSum` bigint(20) DEFAULT NULL COMMENT 'Android发送总量',
  `sendIosSum` bigint(20) DEFAULT NULL COMMENT 'ios发送总量',
  `sendSockSum` bigint(20) DEFAULT NULL COMMENT 'socket渠道发送总量',
  `sendMiSum` bigint(20) DEFAULT NULL COMMENT '小米渠道发送总量',
  `sendGcmSum` bigint(20) DEFAULT NULL COMMENT 'gcm渠道发送总量',
  `arriveSum` bigint(20) DEFAULT NULL COMMENT '到达总数',
  `arriveAndroidSum` bigint(20) DEFAULT NULL COMMENT 'Android到达总数',
  `arriveIosSum` bigint(20) DEFAULT NULL COMMENT 'ios达到总数',
  `arriveSockSum` bigint(20) DEFAULT NULL COMMENT 'socket渠道达到总数',
  `arriveMiSum` bigint(20) DEFAULT NULL COMMENT '小米渠道到达总数',
  `arriveGcmSum` bigint(20) DEFAULT NULL COMMENT 'gcm渠道到达总数',
  PRIMARY KEY (`id`),
  KEY `ind_msgId` (`msgId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8;
SET FOREIGN_KEY_CHECKS=1;
