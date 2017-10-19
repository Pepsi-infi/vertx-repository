/*
 Navicat Premium Data Transfer

 Source Server         : 10.10.10.178(测试)
 Source Server Type    : MySQL
 Source Server Version : 50626
 Source Host           : 10.10.10.178
 Source Database       : mc_admin

 Target Server Type    : MySQL
 Target Server Version : 50626
 File Encoding         : utf-8

 Date: 10/14/2017 10:31:30 AM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `im_phrase_driver`
-- ----------------------------
DROP TABLE IF EXISTS `im_phrase_driver`;
CREATE TABLE `im_phrase_driver` (
  `id` int(16) NOT NULL,
  `userId` int(16) DEFAULT NULL,
  `content` varchar(64) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
