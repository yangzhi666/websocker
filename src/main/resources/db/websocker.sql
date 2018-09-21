/*
Navicat MySQL Data Transfer

Source Server         : localhostDB
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : websocker

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-09-21 19:06:30
*/

SET FOREIGN_KEY_CHECKS=0;

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

-- ----------------------------
-- Table structure for my_group
-- ----------------------------
DROP TABLE IF EXISTS `my_group`;
CREATE TABLE `my_group` (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '群id',
  `groupname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '群名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '群组头像',
  `my_id` varchar(50) NOT NULL COMMENT '我的id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of my_group
-- ----------------------------
INSERT INTO `my_group` VALUES ('10000', 'javaweb开发一群', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537528811897&di=2101d562b7407f60f6ed85337421c6a3&imgtype=0&src=http%3A%2F%2Fuploadfiles.nowcoder.net%2Fimages%2F20150405%2F607220_1428226181241_607220_1428226172039_Java.png', '100000');
INSERT INTO `my_group` VALUES ('10001', 'javaweb开发二群', 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2310268777,952048309&fm=26&gp=0.jpg', '100000');
INSERT INTO `my_group` VALUES ('10002', 'javaweb开发三群', 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=873346154,1562023283&fm=26&gp=0.jpg', '100000');

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

-- ----------------------------
-- Table structure for my_message
-- ----------------------------
DROP TABLE IF EXISTS `my_message`;
CREATE TABLE `my_message` (
  `id` varchar(50) NOT NULL,
  `username` varchar(255) NOT NULL,
  `sign` varchar(255) NOT NULL,
  `avatar` varchar(255) NOT NULL,
  `status` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of my_message
-- ----------------------------
INSERT INTO `my_message` VALUES ('100000', '邹智敏', '在深邃的编码世界，做一枚轻盈的纸飞机', 'http://e.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=b24fd50b0e3387449c902778643ff5cf/1f178a82b9014a90b268cde6ab773912b31bee13.jpg', 'online');
INSERT INTO `my_message` VALUES ('100001', '买云', '圣海一片', 'http://g.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=4d4c9dd3fddcd100cdc9f02547bb6b26/8c1001e93901213fce85790251e736d12e2e95bd.jpg', 'online');
