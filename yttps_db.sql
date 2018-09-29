/*
Navicat MySQL Data Transfer

Source Server         : MyDB
Source Server Version : 50547
Source Host           : localhost:3306
Source Database       : yttps_db

Target Server Type    : MYSQL
Target Server Version : 50547
File Encoding         : 65001

Date: 2018-08-21 16:16:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `customer`
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `customer_name` text NOT NULL COMMENT '客户名称（一般为企业名称）',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('1', 'eservice', '2018-08-21 11:26:37', '2018-08-21 13:31:18');
INSERT INTO `customer` VALUES ('2', '汉堃科技', '2018-08-21 13:31:37', null);

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `account` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'HT', 'admin');
