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

 Date: 10/18/2017 10:50:58 AM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `quick_phrase`
-- ----------------------------
DROP TABLE IF EXISTS `im_quick_phrase_model`;
CREATE TABLE
    im_quick_phrase_model
    (
        id INT(16) NOT NULL AUTO_INCREMENT,
        identity INT(2) NOT NULL COMMENT ' 0司机 1乘客',
        title VARCHAR(64) COMMENT '司机端快捷短语消息标题',
        content VARCHAR(64) NOT NULL COMMENT '快捷短语模板内容',
        sort INT(2) COMMENT '顺序',
        createTime DATETIME NOT NULL COMMENT '创建时间',
        updateTime DATETIME COMMENT '更新时间',
        PRIMARY KEY (id),
        INDEX userId (identity, sort)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='默认常用短语表';

INSERT INTO im_quick_phrase_model (id, identity, title, content, sort, createTime, updateTime) VALUES (1, 0, '定位准吗', '您好，定位准吗？我会按照导航接您', 1, '2017-11-06 11:29:00', null);
INSERT INTO im_quick_phrase_model (id, identity, title, content, sort, createTime, updateTime) VALUES (2, 0, '我马上到', '您好，我马上到，请稍候', 2, '2017-11-06 11:29:00', null);
INSERT INTO im_quick_phrase_model (id, identity, title, content, sort, createTime, updateTime) VALUES (3, 0, '好的', '好的，我知道了', 3, '2017-11-06 11:29:00', null);
INSERT INTO im_quick_phrase_model (id, identity, title, content, sort, createTime, updateTime) VALUES (4, 1, '', '您好，我的定位准确，请按导航来接我', 1, '2017-11-06 11:29:00', null);
INSERT INTO im_quick_phrase_model (id, identity, title, content, sort, createTime, updateTime) VALUES (5, 1, '', '我已到达上车点，请您尽快过来', 2, '2017-11-06 11:29:00', null);
INSERT INTO im_quick_phrase_model (id, identity, title, content, sort, createTime, updateTime) VALUES (6, 1, '', '我马上到，请您稍等一会', 3, '2017-11-06 11:29:00', null);
INSERT INTO im_quick_phrase_model (id, identity, title, content, sort, createTime, updateTime) VALUES (7, 1, '', '不好意思，我还要几分钟，请您稍等一会', 4, '2017-11-06 11:29:00', null);

SET FOREIGN_KEY_CHECKS = 1;
