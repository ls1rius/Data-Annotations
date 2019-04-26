/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : crf_data_db

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 26/04/2019 21:04:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for connection_category
-- ----------------------------
DROP TABLE IF EXISTS `connection_category`;
CREATE TABLE `connection_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of connection_category
-- ----------------------------
BEGIN;
INSERT INTO `connection_category` VALUES (0, '在...之前');
INSERT INTO `connection_category` VALUES (1, '在...之后');
INSERT INTO `connection_category` VALUES (2, '在...期间');
INSERT INTO `connection_category` VALUES (3, '是...的锚点');
INSERT INTO `connection_category` VALUES (5, 'testLink');
COMMIT;

-- ----------------------------
-- Table structure for conntections
-- ----------------------------
DROP TABLE IF EXISTS `conntections`;
CREATE TABLE `conntections` (
  `id` int(11) NOT NULL,
  `from_id` int(11) NOT NULL,
  `to_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for file_data
-- ----------------------------
DROP TABLE IF EXISTS `file_data`;
CREATE TABLE `file_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `filepath` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'data.json',
  `date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` varchar(255) NOT NULL,
  `datetime` varchar(255) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file_data
-- ----------------------------
BEGIN;
INSERT INTO `file_data` VALUES (35, '/Users/lihao/Desktop/Data/20190323162656613.json', 'aaa.json', '2019-02-23', '16:26:56', '2019-02-23 16:26:56');
INSERT INTO `file_data` VALUES (36, '/Users/lihao/Desktop/Data/20190323162859211.json', 'bbb.json', '2019-01-23', '16:28:59', '2019-01-23 16:28:59');
INSERT INTO `file_data` VALUES (37, '/Users/lihao/Desktop/Data/20190323162910326.json', 'data.json', '2019-03-23', '16:29:10', '2019-03-23 16:29:10');
INSERT INTO `file_data` VALUES (39, '/Users/lihao/Desktop/Data/20190323163132927.json', '72.json', '2019-03-23', '16:31:32', '2019-03-23 16:31:32');
INSERT INTO `file_data` VALUES (40, '/Users/lihao/Desktop/Data/20190323163134447.json', '72.json', '2019-03-23', '16:31:34', '2019-03-23 16:31:34');
INSERT INTO `file_data` VALUES (41, '/Users/lihao/Desktop/Data/20190323163136170.json', '72.json', '2019-03-23', '16:31:36', '2019-03-23 16:31:36');
INSERT INTO `file_data` VALUES (42, '/Users/lihao/Desktop/Data/20190323163137558.json', '72.json', '2019-04-23', '16:31:37', '2019-04-23 16:31:37');
INSERT INTO `file_data` VALUES (43, '/Users/lihao/Desktop/Data/20190323163149759.json', '134.json', '2019-03-23', '16:31:49', '2019-03-23  16:31:49');
INSERT INTO `file_data` VALUES (44, '/Users/lihao/Desktop/Data/20190323163158149.json', '134.json', '2019-03-23', '16:31:58', '2019-03-23 16:31:58');
INSERT INTO `file_data` VALUES (45, '/Users/lihao/Desktop/Data/20190323163159700.json', '134.json', '2019-03-23', '16:31:59', '16:31:59 16:31:59');
INSERT INTO `file_data` VALUES (46, '/Users/lihao/Desktop/Data/20190323163201378.json', '134.json', '2019-10-23', '16:32:01', '2019-10-23 16:32:01');
INSERT INTO `file_data` VALUES (47, '/Users/lihao/Desktop/Data/20190323163202497.json', '134.json', '2019-09-23', '16:32:02', '2019-09-23 16:32:02');
INSERT INTO `file_data` VALUES (48, '/Users/lihao/Desktop/Data/20190323163511241.json', '16.json', '2019-05-23', '16:35:11', '2019-05-23 16:35:11');
INSERT INTO `file_data` VALUES (50, '/Users/lihao/Desktop/Data/20190323210548439.json', 'data.json', '2019-03-23', '21:05:48', '2019-03-23 21:05:48');
INSERT INTO `file_data` VALUES (51, '/Users/lihao/Desktop/Data/20190323210611458.json', '133.json', '2019-03-23', '21:06:11', '2019-03-23 21:06:11');
INSERT INTO `file_data` VALUES (52, '/Users/lihao/Desktop/Data/20190323210614529.json', '133.json', '2019-03-23', '21:06:14', '2019-03-23 21:06:14');
INSERT INTO `file_data` VALUES (53, '/Users/lihao/Desktop/Data/20190323210622632.json', '133.json', '2019-03-23', '21:06:22', '2019-03-23 21:06:22');
INSERT INTO `file_data` VALUES (54, '/Users/lihao/Desktop/Data/20190323223206539.json', 'test.json', '2019-03-23', '22:32:06', '2019-03-23 22:32:06');
INSERT INTO `file_data` VALUES (55, '/Users/lihao/Desktop/Data/20190323224042625.json', '18.json', '2019-03-23', '22:40:42', '2019-03-23 22:40:42');
INSERT INTO `file_data` VALUES (56, '/Users/lihao/Desktop/Data/20190323224052818.json', '18.json', '2019-12-23', '22:40:52', '2019-12-23 22:40:52');
INSERT INTO `file_data` VALUES (57, '/Users/lihao/Desktop/Data/20190323225810311.json', '20.json', '2019-11-23', '22:58:10', '2019-11-23 22:58:10');
INSERT INTO `file_data` VALUES (58, '/Users/lihao/Desktop/Data/20190323225816725.json', '20.json', '2019-03-23', '22:58:16', '2019-03-23 22:58:16');
INSERT INTO `file_data` VALUES (59, '/Users/lihao/Desktop/Data/20190324142953421.json', '132.json', '2019-03-24', '14:29:53', '2019-03-24 14:29:53');
INSERT INTO `file_data` VALUES (60, '/Users/lihao/Desktop/Data/20190324143036412.json', '132.json', '2019-03-24', '14:30:36', '2019-03-24 14:30:36');
INSERT INTO `file_data` VALUES (61, '/Users/lihao/Desktop/Data/20190324163430745.json', '19.json', '2019-03-24', '16:34:30', '2019-03-24 16:34:30');
INSERT INTO `file_data` VALUES (62, '/Users/lihao/Desktop/Data/20190324165008502.json', '72.json', '2019-03-24', '16:50:08', '2019-03-24 16:50:08');
INSERT INTO `file_data` VALUES (63, '/Users/lihao/Desktop/Data/20190324165018797.json', '72.json', '2019-03-24', '16:50:18', '2019-03-24 16:50:18');
INSERT INTO `file_data` VALUES (64, '/Users/lihao/Desktop/Data/20190324175502314.json', '19.json', '2019-03-24', '17:55:02', '20190324175502320');
INSERT INTO `file_data` VALUES (65, '/Users/lihao/Desktop/Data/20190324175528483.json', '11.json', '2019-03-24', '17:55:28', '20190324175528490');
INSERT INTO `file_data` VALUES (66, '/Users/lihao/Desktop/Data/20190324180131565.json', 'data.json', '2019-03-24', '18:01:31', '20190324180131566');
INSERT INTO `file_data` VALUES (72, '/Users/lihao/Desktop/Data/20190327153756719.json', '14.json', '2019-03-27', '15:37:56', '20190327153756727');
INSERT INTO `file_data` VALUES (73, '/Users/lihao/Desktop/Data/20190416115946271.json', '测试文件.json', '2019-04-16', '11:59:46', '20190416115946275');
INSERT INTO `file_data` VALUES (75, '/Users/lihao/Desktop/Data/20190421214713219.json', '18.json', '2019-04-21', '21:47:13', '20190421214713225');
COMMIT;

-- ----------------------------
-- Table structure for label_category
-- ----------------------------
DROP TABLE IF EXISTS `label_category`;
CREATE TABLE `label_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `color` tinytext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `border_color` tinytext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `named_entity` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of label_category
-- ----------------------------
BEGIN;
INSERT INTO `label_category` VALUES (0, '时间', '#7ebf50', '#7ebf50', 'time');
INSERT INTO `label_category` VALUES (1, '事件', '#579ef8', '#579ef8', 'event');
INSERT INTO `label_category` VALUES (3, 'test', '#000000', '000000', 'ttest');
COMMIT;

-- ----------------------------
-- Table structure for labels
-- ----------------------------
DROP TABLE IF EXISTS `labels`;
CREATE TABLE `labels` (
  `id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `start_index` int(255) NOT NULL,
  `end_index` int(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
