-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               11.4.8-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.11.0.7065
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for shoppingdb
CREATE DATABASE IF NOT EXISTS `shoppingdb` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci */;
USE `shoppingdb`;

-- Dumping structure for table shoppingdb.comments
CREATE TABLE IF NOT EXISTS `comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` text DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table shoppingdb.comments: ~20 rows (approximately)
DELETE FROM `comments`;
INSERT INTO `comments` (`id`, `text`, `product_id`) VALUES
	(1, 'Great product!', 11),
	(2, 'Not worth the price.', 4),
	(3, 'Fast delivery.', 3),
	(4, 'Highly recommended.', 10),
	(5, 'Very satisfied.', 1),
	(6, 'Not worth the price.', 14),
	(7, 'Not worth the price.', 13),
	(8, 'Poor customer service.', 16),
	(9, 'Not worth the price.', 10),
	(10, 'Highly recommended.', 6),
	(11, 'Highly recommended.', 10),
	(12, 'Disappointed.', 15),
	(13, 'Highly recommended.', 4),
	(14, 'Excellent quality.', 14),
	(15, 'Disappointed.', 9),
	(16, 'Excellent quality.', 13),
	(17, 'Disappointed.', 9),
	(18, 'Excellent quality.', 1),
	(19, 'Will buy again.', 7),
	(20, 'Not worth the price.', 5);

-- Dumping structure for table shoppingdb.customers
CREATE TABLE IF NOT EXISTS `customers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `customerSince` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table shoppingdb.customers: ~20 rows (approximately)
DELETE FROM `customers`;
INSERT INTO `customers` (`id`, `name`, `customerSince`) VALUES
	(1, 'Karen', '2015-08-19'),
	(2, 'Grace', '2015-06-23'),
	(3, 'Frank', '2016-07-01'),
	(4, 'Tina', '2021-06-19'),
	(5, 'Steve', '2021-03-13'),
	(6, 'Karen', '2021-01-23'),
	(7, 'Frank', '2023-09-11'),
	(8, 'Ivy', '2020-06-09'),
	(9, 'Ivy', '2023-12-09'),
	(10, 'Ivy', '2023-09-15'),
	(11, 'Steve', '2020-11-07'),
	(12, 'Olivia', '2021-03-03'),
	(13, 'Quinn', '2017-08-02'),
	(14, 'Tina', '2018-10-22'),
	(15, 'Mona', '2017-11-27'),
	(16, 'Hank', '2022-05-23'),
	(17, 'Ivy', '2018-10-01'),
	(18, 'Leo', '2018-07-09'),
	(19, 'Tina', '2018-08-30'),
	(20, 'Steve', '2023-02-16');

-- Dumping structure for table shoppingdb.orderlines
CREATE TABLE IF NOT EXISTS `orderlines` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `purchasePrice` decimal(10,2) DEFAULT NULL,
  `purchase_price` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `orderlines_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `orderlines_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table shoppingdb.orderlines: ~20 rows (approximately)
DELETE FROM `orderlines`;
INSERT INTO `orderlines` (`id`, `order_id`, `product_id`, `amount`, `purchasePrice`, `purchase_price`) VALUES
	(1, 20, 13, 2, 122.21, NULL),
	(2, 12, 6, 5, 53.67, NULL),
	(3, 11, 4, 2, 454.72, NULL),
	(4, 10, 19, 5, 428.62, NULL),
	(5, 1, 3, 4, 298.72, NULL),
	(6, 8, 18, 8, 190.33, NULL),
	(7, 17, 11, 7, 401.50, NULL),
	(8, 3, 2, 7, 42.13, NULL),
	(9, 5, 5, 9, 114.84, NULL),
	(10, 5, 8, 9, 27.69, NULL),
	(11, 15, 19, 1, 320.77, NULL),
	(12, 17, 8, 3, 224.72, NULL),
	(13, 10, 18, 9, 143.20, NULL),
	(14, 7, 20, 3, 79.79, NULL),
	(15, 14, 10, 1, 314.46, NULL),
	(16, 4, 16, 3, 476.43, NULL),
	(17, 17, 8, 8, 407.43, NULL),
	(18, 13, 19, 4, 69.18, NULL),
	(19, 19, 17, 10, 247.00, NULL),
	(20, 3, 8, 4, 340.08, NULL);

-- Dumping structure for table shoppingdb.orders
CREATE TABLE IF NOT EXISTS `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table shoppingdb.orders: ~20 rows (approximately)
DELETE FROM `orders`;
INSERT INTO `orders` (`id`, `date`, `customer_id`) VALUES
	(1, '2021-10-18', 6),
	(2, '2016-12-27', 15),
	(3, '2015-09-24', 10),
	(4, '2016-09-14', 12),
	(5, '2017-09-01', 1),
	(6, '2015-04-20', 7),
	(7, '2019-05-30', 4),
	(8, '2016-03-15', 10),
	(9, '2022-07-15', 8),
	(10, '2015-05-08', 8),
	(11, '2016-05-11', 19),
	(12, '2017-06-22', 15),
	(13, '2017-08-16', 14),
	(14, '2016-08-18', 7),
	(15, '2023-11-03', 4),
	(16, '2017-06-16', 6),
	(17, '2015-11-14', 18),
	(18, '2021-06-19', 6),
	(19, '2022-10-30', 20),
	(20, '2018-10-21', 5);

-- Dumping structure for table shoppingdb.products
CREATE TABLE IF NOT EXISTS `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `inStock` tinyint(1) DEFAULT NULL,
  `in_stock` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table shoppingdb.products: ~20 rows (approximately)
DELETE FROM `products`;
INSERT INTO `products` (`id`, `name`, `price`, `inStock`, `in_stock`) VALUES
	(1, 'Pepsi', 488.75, 1, NULL),
	(2, 'Cocacola', 114.13, 0, NULL),
	(3, 'CookiesBS', 247.79, 1, NULL),
	(4, 'Rice ST25', 337.2, 1, NULL),
	(5, 'SugarNet', 423.35, 1, NULL),
	(6, 'Rice TL', 21.88, 0, NULL),
	(7, 'Skirt Kids', 189.75, 1, NULL),
	(8, 'Chicken', 97.26, 1, NULL),
	(9, 'Milk US', 372.48, 0, NULL),
	(10, 'WaterLocal', 299.15, 0, NULL),
	(11, 'PenTL', 65.6, 0, NULL),
	(12, 'CocaBrazil', 294.61, 1, NULL),
	(13, 'Milk NZ', 152.86, 0, NULL),
	(14, 'Milk VN', 190.66, 1, NULL),
	(15, 'Rice In', 79.41, 0, NULL),
	(16, 'Chicken Bigsize', 370.97, 0, NULL),
	(17, 'Bread Stick', 291.59, 0, NULL),
	(18, 'Bread Slide', 283.47, 0, NULL),
	(19, 'Sugar pices', 146.66, 0, NULL),
	(20, 'Skirt woman', 143.4, 1, NULL);

-- Dumping structure for table shoppingdb.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(100) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table shoppingdb.users: ~20 rows (approximately)
DELETE FROM `users`;
INSERT INTO `users` (`id`, `firstName`, `lastName`, `nationality`, `age`, `first_name`, `last_name`) VALUES
	(1, 'Grace', 'Steve', 'UK', 60, NULL, NULL),
	(2, 'Grace', 'Karen', 'UK', 63, NULL, NULL),
	(3, 'Hank', 'Quinn', 'USA', 65, NULL, NULL),
	(4, 'Karen', 'Hank', 'USA', 59, NULL, NULL),
	(5, 'Leo', 'Steve', 'USA', 26, NULL, NULL),
	(6, 'Tina', 'Alice', 'Rusia', 32, NULL, NULL),
	(7, 'Mona', 'Rachel', 'Rusia', 62, NULL, NULL),
	(8, 'Hank', 'Alice', 'Rusia', 27, NULL, NULL),
	(9, 'Charlie', 'Paul', 'Rusia', 63, NULL, NULL),
	(10, 'Steve', 'Charlie', 'Rusia', 55, NULL, NULL),
	(11, 'Bob', 'Leo', 'Australia', 38, NULL, NULL),
	(12, 'Steve', 'Quinn', 'Australia', 52, NULL, NULL),
	(13, 'Charlie', 'Quinn', 'Australia', 27, NULL, NULL),
	(14, 'Paul', 'Hank', 'Australia', 63, NULL, NULL),
	(15, 'Ivy', 'Nate', 'France', 24, NULL, NULL),
	(16, 'Leo', 'Paul', 'France', 38, NULL, NULL),
	(17, 'Steve', 'Paul', 'France', 58, NULL, NULL),
	(18, 'Bob', 'Jack', 'Brasil', 22, NULL, NULL),
	(19, 'Grace', 'Steve', 'Brasil', 46, NULL, NULL),
	(20, 'Nate', 'Leo', 'Country19', 46, NULL, NULL);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
