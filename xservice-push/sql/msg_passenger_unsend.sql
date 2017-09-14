-- 未推送成功的消息记录入库，过期会自动清理
CREATE TABLE `msg_passenger_unsend` (
`msgId` int(11) NOT NULL,
`phone` varchar(11) NOT NULL,
`userId` int(11) DEFAULT NULL,
`expireTime` datetime DEFAULT NULL,
`content` varchar(3000) DEFAULT NULL,
UNIQUE KEY `uni_msgphone` (`phone`,`msgId`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
