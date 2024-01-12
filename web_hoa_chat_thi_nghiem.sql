/*
 Navicat Premium Data Transfer

 Source Server         : an-toan-bao-mat
 Source Server Type    : MySQL
 Source Server Version : 80200
 Source Host           : roundhouse.proxy.rlwy.net:59797
 Source Schema         : railway

 Target Server Type    : MySQL
 Target Server Version : 80200
 File Encoding         : 65001

 Date: 12/01/2024 14:26:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account_admin
-- ----------------------------
DROP TABLE IF EXISTS `account_admin`;
CREATE TABLE `account_admin`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `id_role_admin` int NOT NULL,
  `id_status_acc` int NOT NULL,
  `passwordAD` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `time_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `time_change_pass` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fullname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`username`) USING BTREE,
  INDEX `id_status_acc`(`id_status_acc` ASC) USING BTREE,
  INDEX `id_role_admin`(`id_role_admin` ASC) USING BTREE,
  CONSTRAINT `account_admin_ibfk_1` FOREIGN KEY (`id_status_acc`) REFERENCES `status_acc` (`id_status_acc`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `account_admin_ibfk_2` FOREIGN KEY (`id_role_admin`) REFERENCES `role_admin` (`id_role_admin`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of account_admin
-- ----------------------------
INSERT INTO `account_admin` VALUES ('nguyenphutai', 2, 1, '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '2023-11-29 11:28:42', '2023-11-29 11:28:42', 'Nguyễn Phú Tài');
INSERT INTO `account_admin` VALUES ('nguyentandat', 2, 1, '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '2023-11-29 11:28:42', '2023-11-29 11:28:42', 'Nguyễn Tấn Đạt');
INSERT INTO `account_admin` VALUES ('tranminhtuyen', 3, 1, '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '2023-11-29 11:28:42', '2023-11-29 11:28:42', 'Trần Minh Tuyên');

-- ----------------------------
-- Table structure for account_customer
-- ----------------------------
DROP TABLE IF EXISTS `account_customer`;
CREATE TABLE `account_customer`  (
  `id_user_customer` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `pass` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `id_status_acc` int NOT NULL,
  `id_city` int NOT NULL,
  `fullname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sex` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email_customer` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone_customer` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `time_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `time_change_pass` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `uuid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id_user_customer`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE,
  INDEX `id_status_acc`(`id_status_acc` ASC) USING BTREE,
  INDEX `id_city`(`id_city` ASC) USING BTREE,
  CONSTRAINT `account_customer_ibfk_1` FOREIGN KEY (`id_status_acc`) REFERENCES `status_acc` (`id_status_acc`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `account_customer_ibfk_2` FOREIGN KEY (`id_city`) REFERENCES `city` (`id_city`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of account_customer
-- ----------------------------

-- ----------------------------
-- Table structure for bill_detail
-- ----------------------------
DROP TABLE IF EXISTS `bill_detail`;
CREATE TABLE `bill_detail`  (
  `id_bill` int NOT NULL,
  `id_product` int NOT NULL,
  `quantity` int NOT NULL,
  `listed_price` bigint NOT NULL,
  `current_price` bigint NOT NULL,
  PRIMARY KEY (`id_bill`, `id_product`) USING BTREE,
  INDEX `id_product`(`id_product` ASC) USING BTREE,
  CONSTRAINT `bill_detail_ibfk_1` FOREIGN KEY (`id_bill`) REFERENCES `bills` (`id_bill`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `bill_detail_ibfk_2` FOREIGN KEY (`id_product`) REFERENCES `products` (`id_product`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bill_detail
-- ----------------------------

-- ----------------------------
-- Table structure for bills
-- ----------------------------
DROP TABLE IF EXISTS `bills`;
CREATE TABLE `bills`  (
  `id_bill` int NOT NULL AUTO_INCREMENT,
  `id_user` int NOT NULL,
  `id_status_bill` int NOT NULL,
  `id_city` int NOT NULL,
  `fullname_customer` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone_customer` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email_customer` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `address_customer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `bill_price` double NOT NULL,
  `total_price` double NOT NULL,
  `time_order` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `hash_bill_encrypted` text CHARACTER SET utf32 COLLATE utf32_general_ci NULL,
  PRIMARY KEY (`id_bill`) USING BTREE,
  INDEX `id_user`(`id_user` ASC) USING BTREE,
  INDEX `id_status_bill`(`id_status_bill` ASC) USING BTREE,
  INDEX `id_city`(`id_city` ASC) USING BTREE,
  CONSTRAINT `bills_ibfk_2` FOREIGN KEY (`id_status_bill`) REFERENCES `status_bill` (`id_status_bill`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `bills_ibfk_3` FOREIGN KEY (`id_city`) REFERENCES `city` (`id_city`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `bills_ibfk_4` FOREIGN KEY (`id_user`) REFERENCES `account_customer` (`id_user_customer`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bills
-- ----------------------------

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city`  (
  `id_city` int NOT NULL AUTO_INCREMENT,
  `name_city` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `transport` bigint NOT NULL,
  PRIMARY KEY (`id_city`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of city
-- ----------------------------
INSERT INTO `city` VALUES (1, 'Hồ Chí Minh', 10000);
INSERT INTO `city` VALUES (2, 'Hà Nội', 30000);
INSERT INTO `city` VALUES (3, 'Đà Nẵng', 20000);

-- ----------------------------
-- Table structure for comment_news
-- ----------------------------
DROP TABLE IF EXISTS `comment_news`;
CREATE TABLE `comment_news`  (
  `id_comment` int NOT NULL AUTO_INCREMENT,
  `id_news` int NOT NULL,
  `id_user_customer` int NOT NULL,
  `time_comment` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `content_comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id_comment`) USING BTREE,
  INDEX `id_news`(`id_news` ASC) USING BTREE,
  INDEX `id_user_customer`(`id_user_customer` ASC) USING BTREE,
  CONSTRAINT `comment_news_ibfk_1` FOREIGN KEY (`id_news`) REFERENCES `news` (`id_news`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `comment_news_ibfk_2` FOREIGN KEY (`id_user_customer`) REFERENCES `account_customer` (`id_user_customer`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment_news
-- ----------------------------

-- ----------------------------
-- Table structure for contact
-- ----------------------------
DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact`  (
  `id_contact` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone_contact` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email_contact` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `problem_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `content_problem` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `time_insert` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_contact`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of contact
-- ----------------------------

-- ----------------------------
-- Table structure for infor_web
-- ----------------------------
DROP TABLE IF EXISTS `infor_web`;
CREATE TABLE `infor_web`  (
  `id_infor` int NOT NULL AUTO_INCREMENT,
  `phone_web` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email_web` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `address_web` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id_infor`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of infor_web
-- ----------------------------

-- ----------------------------
-- Table structure for logs
-- ----------------------------
DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs`  (
  `id_log` bigint NOT NULL AUTO_INCREMENT,
  `id_level` int NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `src` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ip_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `web_browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_at` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id_log`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of logs
-- ----------------------------

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`  (
  `id_news` int NOT NULL AUTO_INCREMENT,
  `title_news` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content_news` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `url_img_news` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `date_posted` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `quantity_comment` int NOT NULL,
  `nameAdmin` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id_news`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of news
-- ----------------------------

-- ----------------------------
-- Table structure for price_product
-- ----------------------------
DROP TABLE IF EXISTS `price_product`;
CREATE TABLE `price_product`  (
  `id_product` int NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `listed_price` bigint NOT NULL,
  `current_price` bigint NOT NULL,
  `nameAdmin` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id_product`, `date`) USING BTREE,
  CONSTRAINT `price_product_ibfk_1` FOREIGN KEY (`id_product`) REFERENCES `products` (`id_product`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of price_product
-- ----------------------------
INSERT INTO `price_product` VALUES (13, '2023-11-29 11:28:42', 699000, 699000, NULL);
INSERT INTO `price_product` VALUES (14, '2023-11-29 11:28:42', 699000, 699000, NULL);
INSERT INTO `price_product` VALUES (15, '2023-11-29 11:28:42', 699000, 699000, NULL);
INSERT INTO `price_product` VALUES (16, '2023-11-29 11:28:42', 699000, 699000, NULL);

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products`  (
  `id_product` int NOT NULL AUTO_INCREMENT,
  `id_subtype` int NOT NULL,
  `id_status_product` int NOT NULL,
  `id_supplier` int NOT NULL,
  `name_product` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description_product` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `url_img_product` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `quantity_product` int NOT NULL,
  `date_inserted` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `star_review` tinyint NULL DEFAULT NULL,
  `nameAdmin` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `views` int NULL DEFAULT 0,
  PRIMARY KEY (`id_product`) USING BTREE,
  INDEX `id_type_product`(`id_subtype` ASC) USING BTREE,
  INDEX `id_status_product`(`id_status_product` ASC) USING BTREE,
  INDEX `id_supplier`(`id_supplier` ASC) USING BTREE,
  CONSTRAINT `fk_id_subtype_4` FOREIGN KEY (`id_subtype`) REFERENCES `subtype_product` (`id_subtype`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`id_subtype`) REFERENCES `subtype_product` (`id_subtype`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `products_ibfk_2` FOREIGN KEY (`id_status_product`) REFERENCES `status_product` (`id_status_product`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `products_ibfk_3` FOREIGN KEY (`id_supplier`) REFERENCES `suppliers` (`id_supplier`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 121 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of products
-- ----------------------------
INSERT INTO `products` VALUES (12, 1, 1, 4, 'Axit Selenic - H2SeO4', 'Axit Sulfuric hay còn gọi là hóa chất H2SO4, tên gọi này có nguồn gốc từ tiếng Pháp là acide sulfurique. Axit Sulfuric có công thức phần tử là H2SO4. H2SO4 là một axit vô cơ gồm các nguyên tố lưu huỳnh, oxy, hidro; tồn tại ở dạng chất lỏng sánh như dầu, không mùi, không màu, không bay hơi, nặng gấp 2 lần nước (H2SO4 98% có D = 1,84g/cm3). H2SO4 có khả năng tan hoàn toàn trong nước theo bất kỳ tỷ lệ nào, khi tan trong nước H2SO4 tỏa rất nhiều nhiệt.', '/images/products/hoa_chat/axit/axit_selenic.png', 999, '2023-11-29 11:28:42', 5, NULL, 0);
INSERT INTO `products` VALUES (13, 1, 1, 1, 'Axit Sunfuric - H2SO4', 'Axit Sulfuric hay còn gọi là hóa chất H2SO4, tên gọi này có nguồn gốc từ tiếng Pháp là acide sulfurique. Axit Sulfuric có công thức phần tử là H2SO4. H2SO4 là một axit vô cơ gồm các nguyên tố lưu huỳnh, oxy, hidro; tồn tại ở dạng chất lỏng sánh như dầu, không mùi, không màu, không bay hơi, nặng gấp 2 lần nước (H2SO4 98% có D = 1,84g/cm3). H2SO4 có khả năng tan hoàn toàn trong nước theo bất kỳ tỷ lệ nào, khi tan trong nước H2SO4 tỏa rất nhiều nhiệt.', '/images/products/hoa_chat/axit/axit_sunfuric.jpg', 993, '2023-11-29 11:28:42', 4, NULL, 1);
INSERT INTO `products` VALUES (14, 1, 1, 5, 'Axit Sunfuro - H2SO3', 'Axit Sulfuric hay còn gọi là hóa chất H2SO4, tên gọi này có nguồn gốc từ tiếng Pháp là acide sulfurique. Axit Sulfuric có công thức phần tử là H2SO4. H2SO4 là một axit vô cơ gồm các nguyên tố lưu huỳnh, oxy, hidro; tồn tại ở dạng chất lỏng sánh như dầu, không mùi, không màu, không bay hơi, nặng gấp 2 lần nước (H2SO4 98% có D = 1,84g/cm3). H2SO4 có khả năng tan hoàn toàn trong nước theo bất kỳ tỷ lệ nào, khi tan trong nước H2SO4 tỏa rất nhiều nhiệt.', '/images/products/hoa_chat/axit/axit_sunfuro.jpg', 994, '2023-11-29 11:28:42', 5, NULL, 0);
INSERT INTO `products` VALUES (15, 2, 1, 1, 'Aluminium Hydroxide - Al(OH)3', 'Axit Sulfuric hay còn gọi là hóa chất H2SO4, tên gọi này có nguồn gốc từ tiếng Pháp là acide sulfurique. Axit Sulfuric có công thức phần tử là H2SO4. H2SO4 là một axit vô cơ gồm các nguyên tố lưu huỳnh, oxy, hidro; tồn tại ở dạng chất lỏng sánh như dầu, không mùi, không màu, không bay hơi, nặng gấp 2 lần nước (H2SO4 98% có D = 1,84g/cm3). H2SO4 có khả năng tan hoàn toàn trong nước theo bất kỳ tỷ lệ nào, khi tan trong nước H2SO4 tỏa rất nhiều nhiệt.', '/images/products/hoa_chat/bazo/aluminum_hydroxide.jpg', 984, '2023-11-29 11:28:42', 5, NULL, 0);
INSERT INTO `products` VALUES (16, 2, 1, 4, 'Barium Hydroxide - Ba(OH)2', 'Axit Sulfuric hay còn gọi là hóa chất H2SO4, tên gọi này có nguồn gốc từ tiếng Pháp là acide sulfurique. Axit Sulfuric có công thức phần tử là H2SO4. H2SO4 là một axit vô cơ gồm các nguyên tố lưu huỳnh, oxy, hidro; tồn tại ở dạng chất lỏng sánh như dầu, không mùi, không màu, không bay hơi, nặng gấp 2 lần nước (H2SO4 98% có D = 1,84g/cm3). H2SO4 có khả năng tan hoàn toàn trong nước theo bất kỳ tỷ lệ nào, khi tan trong nước H2SO4 tỏa rất nhiều nhiệt.', '/images/products/hoa_chat/bazo/barium_hydroxide.jpg', 992, '2023-11-29 11:28:42', 5, NULL, 0);

-- ----------------------------
-- Table structure for public_keys
-- ----------------------------
DROP TABLE IF EXISTS `public_keys`;
CREATE TABLE `public_keys`  (
  `id_public_key` bigint NOT NULL AUTO_INCREMENT,
  `id_user` int NULL DEFAULT NULL,
  `public_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `start_time` timestamp NULL DEFAULT NULL,
  `expired_time` timestamp NULL DEFAULT NULL,
  `is_valid` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id_public_key`) USING BTREE,
  INDEX `id_user_customer`(`id_user` ASC) USING BTREE,
  CONSTRAINT `public_keys_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `account_customer` (`id_user_customer`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 59 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of public_keys
-- ----------------------------

-- ----------------------------
-- Table structure for review_product
-- ----------------------------
DROP TABLE IF EXISTS `review_product`;
CREATE TABLE `review_product`  (
  `id_review` int NOT NULL AUTO_INCREMENT,
  `id_product` int NOT NULL,
  `stars` tinyint NULL DEFAULT 0,
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `fullname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id_review`, `id_product`) USING BTREE,
  INDEX `fk_review_product`(`id_product` ASC) USING BTREE,
  CONSTRAINT `fk_review_product` FOREIGN KEY (`id_product`) REFERENCES `products` (`id_product`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of review_product
-- ----------------------------

-- ----------------------------
-- Table structure for role_admin
-- ----------------------------
DROP TABLE IF EXISTS `role_admin`;
CREATE TABLE `role_admin`  (
  `id_role_admin` int NOT NULL,
  `name_role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id_role_admin`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_admin
-- ----------------------------
INSERT INTO `role_admin` VALUES (1, 'super-root');
INSERT INTO `role_admin` VALUES (2, 'member');
INSERT INTO `role_admin` VALUES (3, 'root');

-- ----------------------------
-- Table structure for sold_product
-- ----------------------------
DROP TABLE IF EXISTS `sold_product`;
CREATE TABLE `sold_product`  (
  `id_product` int NOT NULL,
  `datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `quantity_sold` int NOT NULL,
  PRIMARY KEY (`id_product`, `datetime`) USING BTREE,
  CONSTRAINT `sold_product_ibfk_1` FOREIGN KEY (`id_product`) REFERENCES `products` (`id_product`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sold_product
-- ----------------------------

-- ----------------------------
-- Table structure for status_acc
-- ----------------------------
DROP TABLE IF EXISTS `status_acc`;
CREATE TABLE `status_acc`  (
  `id_status_acc` int NOT NULL,
  `name_status_acc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id_status_acc`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of status_acc
-- ----------------------------
INSERT INTO `status_acc` VALUES (1, 'Bình thường');
INSERT INTO `status_acc` VALUES (2, 'Tạm khóa');
INSERT INTO `status_acc` VALUES (3, 'Khóa vĩnh viễn');

-- ----------------------------
-- Table structure for status_bill
-- ----------------------------
DROP TABLE IF EXISTS `status_bill`;
CREATE TABLE `status_bill`  (
  `id_status_bill` int NOT NULL,
  `name_status_bill` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id_status_bill`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of status_bill
-- ----------------------------
INSERT INTO `status_bill` VALUES (1, 'Chờ xử lý ');
INSERT INTO `status_bill` VALUES (2, 'Đang vận chuyển ');
INSERT INTO `status_bill` VALUES (3, 'Đã giao ');
INSERT INTO `status_bill` VALUES (4, 'Đã hủy ');

-- ----------------------------
-- Table structure for status_product
-- ----------------------------
DROP TABLE IF EXISTS `status_product`;
CREATE TABLE `status_product`  (
  `id_status_product` int NOT NULL,
  `name_status_product` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id_status_product`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of status_product
-- ----------------------------
INSERT INTO `status_product` VALUES (1, 'Mới');
INSERT INTO `status_product` VALUES (2, 'Hot');
INSERT INTO `status_product` VALUES (3, 'Cấm bán');

-- ----------------------------
-- Table structure for subtype_product
-- ----------------------------
DROP TABLE IF EXISTS `subtype_product`;
CREATE TABLE `subtype_product`  (
  `id_subtype` int NOT NULL,
  `name_subtype` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `id_type_product` int NOT NULL,
  `sign` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id_subtype`) USING BTREE,
  INDEX `id_type_product`(`id_type_product` ASC) USING BTREE,
  CONSTRAINT `subtype_product_ibfk_1` FOREIGN KEY (`id_type_product`) REFERENCES `type_product` (`id_type_product`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of subtype_product
-- ----------------------------
INSERT INTO `subtype_product` VALUES (1, 'Acid', 1, NULL);
INSERT INTO `subtype_product` VALUES (2, 'Bazo', 1, NULL);
INSERT INTO `subtype_product` VALUES (3, 'Muối', 1, NULL);
INSERT INTO `subtype_product` VALUES (4, 'Kim Loại', 1, NULL);
INSERT INTO `subtype_product` VALUES (5, 'Chất Chỉ Thị', 1, NULL);
INSERT INTO `subtype_product` VALUES (6, 'Dung Môi', 1, NULL);
INSERT INTO `subtype_product` VALUES (7, 'Thuốc Thử', 1, NULL);
INSERT INTO `subtype_product` VALUES (8, 'Hợp Chất Hữu Cơ', 1, NULL);
INSERT INTO `subtype_product` VALUES (9, 'Oxit', 1, NULL);
INSERT INTO `subtype_product` VALUES (10, 'Giá Đỡ Ống Nghiệm', 2, NULL);
INSERT INTO `subtype_product` VALUES (11, 'Kẹp Gỗ', 2, NULL);
INSERT INTO `subtype_product` VALUES (12, 'Dụng Cụ Thủy Tinh', 2, NULL);
INSERT INTO `subtype_product` VALUES (13, 'Ống Hút Nhỏ Giọt', 2, NULL);
INSERT INTO `subtype_product` VALUES (14, 'Cân Điện Tử', 3, NULL);
INSERT INTO `subtype_product` VALUES (15, 'Máy Khuấy', 3, NULL);
INSERT INTO `subtype_product` VALUES (16, 'Máy Nghiền Mẫu', 3, NULL);
INSERT INTO `subtype_product` VALUES (17, 'Các Loại Tủ', 3, NULL);

-- ----------------------------
-- Table structure for suppliers
-- ----------------------------
DROP TABLE IF EXISTS `suppliers`;
CREATE TABLE `suppliers`  (
  `id_supplier` int NOT NULL AUTO_INCREMENT,
  `name_supplier` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id_supplier`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of suppliers
-- ----------------------------
INSERT INTO `suppliers` VALUES (1, 'Merck - Đức');
INSERT INTO `suppliers` VALUES (2, 'Acros Organics - Mỹ');
INSERT INTO `suppliers` VALUES (3, 'Fisher - Mỹ');
INSERT INTO `suppliers` VALUES (4, 'BOSF - Trung Quốc');
INSERT INTO `suppliers` VALUES (5, 'Duchefa - Hà Lan');
INSERT INTO `suppliers` VALUES (6, 'Wako - Nhật Bản');

-- ----------------------------
-- Table structure for type_product
-- ----------------------------
DROP TABLE IF EXISTS `type_product`;
CREATE TABLE `type_product`  (
  `id_type_product` int NOT NULL AUTO_INCREMENT,
  `name_type_product` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id_type_product`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of type_product
-- ----------------------------
INSERT INTO `type_product` VALUES (1, 'Hóa Chất');
INSERT INTO `type_product` VALUES (2, 'Dụng Cụ');
INSERT INTO `type_product` VALUES (3, 'Thiết Bị');

SET FOREIGN_KEY_CHECKS = 1;
