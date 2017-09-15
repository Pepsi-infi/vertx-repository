CREATE TABLE `msg_passenger_unsend` (
`msgId` int(11) NOT NULL,
`phone` varchar(11) NOT NULL,
`userId` int(11) DEFAULT NULL,
`expireTime` datetime DEFAULT NULL,
`callFlag` int(1) DEFAULT NULL COMMENT '1广告消息，2非广告消息',
`senderId` varchar(100) DEFAULT NULL COMMENT '非广告消息验证需要',
`senderKey` varchar(100) DEFAULT NULL COMMENT '非广告消息验证需要',
`content` varchar(3000) DEFAULT NULL COMMENT '消息内容，整个body体',
UNIQUE KEY `uni_msgphone` (`phone`,`msgId`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
