/*
Navicat MySQL Data Transfer

Source Server         : localhostDB
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : websocker

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-09-29 18:52:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for group_member
-- ----------------------------
DROP TABLE IF EXISTS `group_member`;
CREATE TABLE `group_member` (
  `id` varchar(50) NOT NULL,
  `my_id` varchar(50) NOT NULL COMMENT '我的id',
  `group_id` varchar(50) NOT NULL COMMENT '群id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of group_member
-- ----------------------------
INSERT INTO `group_member` VALUES ('1', '100000', '10000');
INSERT INTO `group_member` VALUES ('2', '100000', '10001');
INSERT INTO `group_member` VALUES ('3', '100000', '10002');
INSERT INTO `group_member` VALUES ('4', '100001', '10000');
INSERT INTO `group_member` VALUES ('5', '100002', '10001');
INSERT INTO `group_member` VALUES ('6', '100002', '10002');
INSERT INTO `group_member` VALUES ('7', '100002', '10000');

-- ----------------------------
-- Table structure for my_friend
-- ----------------------------
DROP TABLE IF EXISTS `my_friend`;
CREATE TABLE `my_friend` (
  `id` varchar(50) NOT NULL,
  `my_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '我的id',
  `grouping_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分组id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of my_friend
-- ----------------------------
INSERT INTO `my_friend` VALUES ('1', '100001', '1');
INSERT INTO `my_friend` VALUES ('2', '100002', '1');
INSERT INTO `my_friend` VALUES ('3', '100003', '1');
INSERT INTO `my_friend` VALUES ('4', '000000', '1');
INSERT INTO `my_friend` VALUES ('5', '100000', '2');
INSERT INTO `my_friend` VALUES ('6', '100000', '3');

-- ----------------------------
-- Table structure for my_group
-- ----------------------------
DROP TABLE IF EXISTS `my_group`;
CREATE TABLE `my_group` (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '群id',
  `groupname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '群名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '群组头像',
  `group_status` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '群状态 1：正常 -1：禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of my_group
-- ----------------------------
INSERT INTO `my_group` VALUES ('10000', 'javaweb开发一群', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537528811897&di=2101d562b7407f60f6ed85337421c6a3&imgtype=0&src=http%3A%2F%2Fuploadfiles.nowcoder.net%2Fimages%2F20150405%2F607220_1428226181241_607220_1428226172039_Java.png', '1');
INSERT INTO `my_group` VALUES ('10001', 'javaweb开发二群', 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2310268777,952048309&fm=26&gp=0.jpg', '1');
INSERT INTO `my_group` VALUES ('10002', 'javaweb开发三群', 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=873346154,1562023283&fm=26&gp=0.jpg', '1');

-- ----------------------------
-- Table structure for my_grouping
-- ----------------------------
DROP TABLE IF EXISTS `my_grouping`;
CREATE TABLE `my_grouping` (
  `group_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `group_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分组名称',
  `group_status` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '分组状态',
  `my_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '我的id',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of my_grouping
-- ----------------------------
INSERT INTO `my_grouping` VALUES ('1', '我的好友', '1', '100000');
INSERT INTO `my_grouping` VALUES ('2', '测试组', '1', '100001');
INSERT INTO `my_grouping` VALUES ('3', '马化腾测试小组', '1', '100002');

-- ----------------------------
-- Table structure for my_message
-- ----------------------------
DROP TABLE IF EXISTS `my_message`;
CREATE TABLE `my_message` (
  `id` varchar(50) NOT NULL,
  `username` varchar(255) NOT NULL,
  `number` varchar(30) NOT NULL COMMENT '账号',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '123456' COMMENT '密码',
  `sign` varchar(255) NOT NULL,
  `avatar` varchar(255) NOT NULL,
  `status` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of my_message
-- ----------------------------
INSERT INTO `my_message` VALUES ('000000', '小艾', 'xiaoai', '123456', '图灵机器人', 'http://www.tuling123.com/resources/web/v4/img/personalCen/icon40.png', 'online');
INSERT INTO `my_message` VALUES ('100000', '邹智敏', 'zzm', '123456', '在深邃的编码世界，做一枚轻盈的纸飞机', 'http://test-001-1253700700.cosgz.myqcloud.com/zzm/c43eafd70c3e4df6aff979b1594f6e32.jpg', 'online');
INSERT INTO `my_message` VALUES ('100001', '马云', 'my', '123456', ' 阿里巴巴集团主要创始人，现担任阿里巴巴集团董事局主席、日本软银董事、大自然保护协会中国理事会主席兼全球董事会成员、华谊兄弟董事、生命科学突破奖基金会董事、联合国数字合作高级别小组联合主席', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537849869929&di=a9dec2020e5c2e8951f2b19f5627a463&imgtype=0&src=http%3A%2F%2Fwww.qqzhi.com%2Fuploadpic%2F2014-10-06%2F085136112.jpg', 'hide');
INSERT INTO `my_message` VALUES ('100002', '马化腾', 'mht', '123456', '腾讯公司主要创办人之一。现任腾讯公司控股董事会主席兼首席执行官；全国青联副主席', 'https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/sign=bf1f9fba0f082838680ddb1280a2ce3c/8cb1cb1349540923453457db9a58d109b3de4931.jpg', 'online');
INSERT INTO `my_message` VALUES ('100003', '王健林', 'wjl', '123456', '大连万达集团股份有限公司董事长，中共十七大代表，第十一届全国政协常委，第十一届全国工商联副主席，现任中国慈善联合会副会长，中国民间商会副会长，中国企业联合会、中国企业家协会副会长', 'https://image.wanda.cn/uploadfile/2013/0914/20130914090913657.jpg', 'online');
