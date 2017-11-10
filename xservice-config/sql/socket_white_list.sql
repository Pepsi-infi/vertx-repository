/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.0.18
 Source Server Type    : MySQL
 Source Server Version : 50626
 Source Host           : 192.168.0.18
 Source Database       : mc_config

 Target Server Type    : MySQL
 Target Server Version : 50626
 File Encoding         : utf-8

 Date: 11/10/2017 13:45:27 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `socket_white_list`
-- ----------------------------
DROP TABLE IF EXISTS `socket_white_list`;
CREATE TABLE `socket_white_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '用户id',
  `type` int(2) NOT NULL COMMENT '用户类型1:司机 2:乘客',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_uid_type` (`uid`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `im_white_list`
-- ----------------------------
BEGIN;
INSERT INTO `socket_white_list` VALUES ('1', '1', '1', '2017-11-06 18:06:06');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
