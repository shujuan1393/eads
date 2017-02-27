-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 27, 2017 at 10:02 AM
-- Server version: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `eads`
--

-- --------------------------------------------------------

--
-- Table structure for table `data` --> raw data
--

CREATE TABLE IF NOT EXISTS `data` (
  `customerid` int(11) NOT NULL,
  `age` int(11) NOT NULL,
  `gender` varchar(200) NOT NULL,
  `transacid` int(11) NOT NULL,
  `transactdate` varchar(200) NOT NULL,
  `transacttime` varchar(200) NOT NULL,
  `outlet` varchar(200) NOT NULL,
  `outletdistrict` int(11) NOT NULL,
  `transactdetailsid` int(11) NOT NULL,
  `item` varchar(200) NOT NULL,
  `itemdesc` varchar(200) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` double NOT NULL,
  `spending` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `item_id` varchar(200) NOT NULL,
  `item_desc` varchar(300) NOT NULL,
  `course` varchar(10) NOT NULL,
  `origin` varchar(50) NOT NULL,
  `tags` varchar(255) NOT NULL,
  `hot_cold` varchar(10) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`item_id`, `item_desc`, `course`, `origin`, `tags`, `hot_cold`) VALUES
('855', 'AlmondCream Glutinous Ball', 'dessert', 'chinese', '', 'hot'),
('20', 'Baked Pork Chop Cream Spag', 'main', 'western', 'pork,spaghetti,chop', 'hot'),
('48', 'BAKED RICE CURRY SLICED BEEF', 'main', 'western', 'beef,rice,curry', 'hot'),
('41', 'BAKED RICE EBI', 'main', 'western', 'prawn,rice', 'hot'),
('418', 'BBQ Pork Bun', 'snacks', 'chinese', 'pork,bun', 'hot'),
('403', 'Beancurd Skin Prawn Roll', 'snacks', 'chinese', 'prawn,beancurd', 'hot'),
('863', 'Beancurd w Mango', 'dessert', '', 'mango,beancurd', 'cold'),
('818', 'Belacan 4 S.Bean', 'side', 'chinese', 'vegetable', 'hot'),
('306', 'Bo Lo Bun', 'snacks', 'chinese', 'bun', 'hot'),
('127', 'Borsch Soup', 'side', 'western', 'soup', 'hot'),
('44', 'BP Beef Bk Rice', 'main', 'western', 'beef,rice', 'hot'),
('35', 'BP Chicken Chop', 'main', 'western', 'chicken,chop', 'hot'),
('14', 'BP Chicken Chop Spag', 'main', 'western', 'chicken,spaghetti,chop', 'hot'),
('35', 'BP Chix Chop & Fries', 'main', 'western', 'chicken,chop,fries', 'hot'),
('14', 'BP Chix Spag', 'main', 'western', 'chicken,spaghetti', 'hot'),
('76', 'Braised Beef brisket Noodles', 'main', 'chinese', 'beef,noodles', 'hot'),
('509', 'Braised Beef Brisket Soup Noodles', 'main', 'chinese', 'beef,noodles,soup', 'hot'),
('902', 'Cent Egg Pork Por', 'main', 'chinese', 'pork,porridge', 'hot'),
('21', 'Chicken Chop Cream Spag', 'main', 'western', 'chicken,spaghetti,chop', 'hot'),
('608', 'CHICKEN CHOP EGG DRY NOODLE', 'main', 'chinese', 'chicken,noodles,chop', 'hot'),
('808', 'Chicken Dry Curry Ramen', 'main', '', 'chicken,noodles,curry', 'hot'),
('414', 'Chicken Feet', 'side', '', 'chicken', 'hot'),
('545', 'Chicken Flos Fried Rice', 'main', 'chinese', 'chicken,rice', 'hot'),
('545', 'Chicken Floss Egg Fried Rice', 'main', 'chinese', 'chicken,rice', 'hot'),
('545', 'Chicken Floss N Egg Fried Rice', 'main', 'chinese', 'chicken,rice', 'hot'),
('1216', 'Chicken Hot Pot Rice', 'main', 'chinese', 'chicken,rice', 'hot'),
('450', 'Chicken In A Basket(3PCS)', 'snacks', '', 'chicken', 'hot'),
('451', 'Chicken In A Basket(5PCS)', 'snacks', '', 'chicken', 'hot'),
('293', 'Chicken Macaroni Soup', 'main', '', 'chicken,macaroni,soup', 'hot'),
('216B', 'Chicken Papaya Soup Beehoon', 'main', '', 'chicken,bee hoon,soup', 'hot'),
('312', 'Chicken with Seaweed', 'snacks', '', 'chicken', 'hot'),
('701', 'Chinese Tea', 'drinks', 'chinese', '', 'hot'),
('45', 'Chix Chop Bk Rice', 'main', 'western', 'chicken,rice,chop', 'hot'),
('414', 'Chix Feet', 'snacks', '', 'chicken', 'hot'),
('1210', 'Chix Wing BP', 'snacks', '', 'chicken', 'hot'),
('1110', 'Chix Wing Home-made Sour \'N\' Spicy Powde', 'snacks', '', 'chicken', 'hot'),
('1109', 'Chix Wing Honey Sweet Sauce', 'snacks', '', 'chicken', 'hot'),
('1108', 'Chix Wing Japanese Sesame Sauce', 'snacks', '', 'chicken', 'hot'),
('1137', 'Chix Wing Nachos Cheese', 'snacks', '', 'chicken', 'hot'),
('11', 'Chs Bk Pork Chop Spag', 'main', 'western', 'pork,spaghetti,chop', 'hot'),
('607', 'Chye Poh N Floss Dry Noodles', 'main', '', 'noodles', 'hot'),
('560', 'Coffee-Tea Snow Ice', 'dessert', '', 'coffee,tea', 'cold'),
('759C', 'Coke', 'drinks', '', 'coke,soft drinks', 'cold'),
('793', 'COKE FLOAT', 'drinks', '', 'coke,soft drinks', 'cold'),
('1286', 'Coke Float (Promo)', 'drinks', '', 'coke,soft drinks', 'cold'),
('1', 'Cold / Iced Water', 'drinks', '', '', 'cold'),
('106', 'Condensed & Peanut TT', 'dessert', '', '', 'cold'),
('61', 'CRISPY PRAWN NOODLE', 'main', '', 'seafood,noodles,prawn', 'hot'),
('203', 'CURRY BEEF BRISKET HOT POT WITH RICE', 'main', '', 'beef,rice,curry', 'hot'),
('404', 'Custard Bun', 'snacks', 'chinese', 'bun,steamed', 'hot'),
('1283', 'Deep Fried Prawn (3pcs)', 'snacks', 'chinese', 'prawn,seafood,fried', 'hot'),
('302', 'Deep-fried Prawns with Mango sauce', 'snacks', 'chinese', 'prawn,seafood,fried', 'hot'),
('546', 'Diced Pork Fried Rice', 'main', 'chinese', 'pork,rice', 'hot'),
('121', 'Egg & Ham Sandwich', 'snacks', 'western', 'egg,ham', 'cold'),
('305', 'F.Fries Curry Mayo', 'snacks', 'western', 'curry,fries', 'hot'),
('111', 'F.Toast Chicken Floss', 'snacks', 'western', 'chicken,toast', 'hot'),
('112', 'F.Toast Ice Cream', 'snacks', 'western', 'toast,ice cream', 'cold'),
('1217', 'Fermented Pork Dry Noodle', 'main', 'chinese', 'pork,noodles', 'hot'),
('34', 'Fish & Chips', 'main', 'western', 'fish,fries', 'hot'),
('46', 'Fish Bk Rice', 'main', 'western', 'fish,rice', 'hot'),
('66', 'Fish Egg Sauteed HF', 'main', 'chinese', 'fish,noodles', 'hot'),
('17', 'Fish Fillet Spag', 'main', 'western', 'fish,spaghetti', 'hot'),
('217B', 'Fish Papaya Soup Beehoon', 'main', 'chinese', 'fish,bee hoon,soup', 'hot'),
('632', 'Fish Slice Muifan', 'main', 'chinese', 'fish', 'hot'),
('904', 'Fish Slice Por', 'main', 'chinese', 'fish,porridge', 'hot'),
('809', 'F\'mented Pork Dry Curry Ramen', 'main', '', 'pork,noodles,curry', 'hot'),
('109', 'French Toast', 'snacks', 'western', 'toast', 'hot'),
('765', 'Fresh Lime Juice', 'drinks', '', '', 'cold'),
('704', 'Fresh Soy Milk', 'drinks', '', 'soy,milk', 'hot'),
('68', 'Fried Spare Ribs Bee Hoon', 'main', 'chinese', 'pork,noodles,bee hoon', 'hot'),
('1232', 'Fried Wanton 3pcs', 'snacks', 'chinese', 'dumpling', 'hot'),
('1136', 'Fruit Jelly Pudding', 'dessert', '', 'fruit', 'cold'),
('507', 'Ham & Egg Soup Ndl', 'main', 'chinese', 'ham,noodles,soup', 'hot'),
('313', 'HAM BO LO BUN', 'snacks', 'chinese', 'ham,bread,bun', 'hot'),
('290', 'Ham Egg Toast N French Fries', 'main', 'western', 'ham,toast,bread,fries', 'hot'),
('412', 'Har Gao', 'snacks', 'chinese', 'prawn', 'hot'),
('1175', 'Homemade Cold Beancurd', 'dessert', 'chinese', 'beancurd', 'cold'),
('857', 'Home-Made Cold Beancurd', 'dessert', 'chinese', 'beancurd', 'cold'),
('315', 'HONEY CHICKEN WING (5pcs)', 'snacks', '', 'chicken', 'hot'),
('1154', 'Hot Coffee-Tea', 'drinks', '', 'coffee,tea', 'hot'),
('713', 'Hot Coffee-Tea', 'drinks', '', 'coffee,tea', 'hot'),
('717', 'Hot Honey Green Tea', 'drinks', '', 'tea', 'hot'),
('718', 'Hot Honey with Lemon', 'drinks', '', 'honey,lemon', 'hot'),
('761', 'Ice Milo Dinosaur', 'drinks', '', 'milo', 'cold'),
('753', 'Iced Coffee-Tea', 'drinks', '', 'coffee,tea', 'cold'),
('783', 'Iced Coffee-Tea Float', 'drinks', '', 'coffee,tea,ice cream', 'cold'),
('1134', 'Iced Grass Jelly', 'drinks', '', '', 'cold'),
('780', 'Iced Grass Jelly', 'drinks', '', '', 'cold'),
('766', 'Iced Honey Green Tea', 'drinks', '', 'honey,tea', 'cold'),
('1132', 'Iced Lemon Tea', 'drinks', '', 'fruit,lemon,tea', 'cold'),
('1285', 'Iced Lemon Tea', 'drinks', '', 'fruit,lemon,tea', 'cold'),
('769', 'Iced Lemon Tea', 'drinks', '', 'fruit,lemon,tea', 'cold'),
('767', 'Iced Lychee Tea', 'drinks', '', 'fruit,lychee,tea', 'cold'),
('751', 'Iced Milk Tea', 'drinks', '', 'milk,tea', 'cold'),
('751N', 'Iced Milk Tea', 'drinks', '', 'milk,tea', 'cold'),
('784', 'Iced Milk Tea Float', 'drinks', '', 'ice cream,tea', 'cold'),
('794', 'ICED MILO FLOAT', 'drinks', '', 'ice cream,milo', 'cold'),
('768', 'Iced Peach Tea', 'drinks', '', 'fruit,peach,tea', 'cold'),
('758', 'Iced Soy Milk', 'drinks', '', 'milk,soy', 'cold'),
('791', 'Iced Yuzu Tea', 'drinks', '', 'fruit,yuzu,tea', 'cold'),
('102', 'Kaya Butter Bread', 'snacks', '', 'kaya,butter', 'hot'),
('1227', 'Kaya Butter Bread + Kopi', 'snacks', '', 'butter,coffee,kaya', 'hot'),
('705', 'Kopi', 'drinks', '', 'coffee', 'hot'),
('126', 'L.Meat\'N\'Egg S\'wich', 'main', 'western', 'pork,bread', 'cold'),
('722', 'LEMON COKE', 'drinks', '', 'lemon,coke,soft drinks', 'cold'),
('765', 'Lime Juice', 'drinks', '', 'fruit,lime', 'cold'),
('941', 'LUNCHEON MEAT (2pcs)', 'snacks', 'chinese', 'pork', 'hot'),
('1292', 'Luncheon Meat Fries', 'snacks', 'western', 'pork,fries', 'hot'),
('23', 'Luncheon Mushrm Cream Spag', 'main', 'western', 'pork,spaghetti,mushroom', 'hot'),
('126', 'Luncheon N Egg Sandwich', 'snacks', 'western', 'pork,bread,egg', 'hot'),
('501', 'LuncheonMeatSoup Ndl', 'soup', 'chinese', 'pork,noodle,soup', 'hot'),
('781', 'Lychee Ice-blended', 'drinks', '', 'fruit,lychee', 'cold'),
('552', 'Mango Pomelo', 'dessert', 'chinese', 'fruit,mango,pomelo', 'cold'),
('553', 'Mango SnowIce', 'dessert', '', 'fruit,mango', 'cold'),
('715', 'Milk Tea', 'drinks', '', 'milk,tea', 'hot'),
('702', 'Milo', 'drinks', '', 'milo', 'hot'),
('820', 'Minced Kai Lan W Silver Fish', 'side', 'chinese', 'fish, vegetable', 'hot'),
('47', 'Mush Bk Rice', 'main', 'western', 'mushroom,rice,baked', 'hot'),
('22', 'Mushroom Cream Spag', 'main', 'western', 'mushroom,spaghetti', 'hot'),
('411', 'Pan Fried Dumpling', 'snacks', 'chinese', 'dimsum,fried', 'hot'),
('821', 'Paoched Lettuce', '', '', 'vegetable', 'hot'),
('782', 'Peach Ice-blended', 'drinks', '', 'fruit', 'cold'),
('106', 'Peanut Butter Milk Thick Toast', 'snacks', 'western', 'peanut,butter,milk', 'hot'),
('215B', 'Pork Chop Bee Hoon', 'main', 'western', 'pork,bee hoon,asian,chop', 'hot'),
('43', 'Pork Chop Bk Rice', 'main', 'western', 'pork,rice,chop,baked', 'hot'),
('254', 'Pork Chop Frag Rice', 'main', 'western', 'pork,rice,chop', 'hot'),
('215A', 'Pork Chop Noodle', 'main', 'western', 'pork,noodle,chop', 'hot'),
('902', 'Pork n Century Egg Congee', 'main', 'chinese', 'pork,egg,porridge', 'hot'),
('68', 'Pork Ribs BH', 'snacks', '', 'pork', ''),
('307', 'Prawn Paste Chicken', 'snacks', 'chinese', 'chicken,prawn', 'hot'),
('1263', 'Pumpkin Chix Chop Rice', 'main', 'western', 'chicken,rice,chop', 'hot'),
('407', 'Radish Cake', 'snacks', 'chinese', 'dimsum', 'hot'),
('797', 'RAINBOW SPRITE', 'drinks', '', 'soft drinks', 'cold'),
('555', 'Red Bean Snow', 'dessert', '', '', 'cold'),
('548', 'Sambal Seafood Fried Rice', 'main', 'chinese', 'seafood,rice', 'hot'),
('903', 'Sampan Congee', 'main', 'chinese', 'porridge', 'hot'),
('24', 'SCALLOP CREAM SAUCE SPAGHETTI', 'main', 'western', 'seafood,spaghetti', 'hot'),
('294', 'Seafood and Slice Pork Macaroni Soup', 'main', 'western', 'pork,seafood,macaroni,soup', 'hot'),
('1201', 'Seafood Ee Noodle', 'main', 'chinese', 'seafood,noodles', 'hot'),
('62', 'Seafood Egg Hor Fun', 'main', 'chinese', 'egg,hor fun,seafood', 'hot'),
('631', 'Seafood Muifan', 'main', 'chinese', 'seafood', 'hot'),
('62', 'Seafood Sauteed HF', 'main', 'chinese', 'hor fun,seafood', 'hot'),
('854', 'Sesame Cream Glutinous Ball', 'dessert', 'chinese', '', 'hot'),
('254', 'SH Pork Chop Rice', 'main', 'western', 'pork,rice,chop', 'hot'),
('401', 'Siew Mai', 'snacks', 'chinese', 'pork,dimsum', 'hot'),
('67', 'Signature Fried Noodles', 'main', 'chinese', 'noodle,fried', 'hot'),
('549', 'Signature Fried Rice', 'main', 'chinese', 'rice,fried', 'hot'),
('549N', 'Signature Fried Rice', 'main', 'chinese', 'rice,fried', 'hot'),
('909', 'Sliced Empress Congee', 'main', 'chinese', 'porridge', 'hot'),
('66', 'Sliced Fish Egg Hor Fun', 'main', 'chinese', 'seafood,fish,hor fun', 'hot'),
('810', 'SMOKED DUCK DRY CURRY RAMEN', 'main', '', 'duck,ramen,curry', 'hot'),
('292', 'Soft Boiled Eggs', 'snacks', '', 'egg', 'hot'),
('1135', 'Soup of the Day', 'main', '', 'soup', 'hot'),
('131', 'Soup Of The Day', 'main', '', 'soup', 'hot'),
('795', 'SOUR PLUM COKE', 'drinks', '', 'coke,soft drinks', 'cold'),
('859', 'SOY MILK WITH GLUTINOUS BALL', 'dessert', 'chinese', 'milk', 'cold'),
('78', 'SPECIAL STIR FRIED BEE HOON', 'main', 'chinese', 'bee hoon,stir fried', 'hot'),
('906', 'Spicy Pork Por', 'pork', 'chinese', 'pork,porridge', 'hot'),
('1211', 'Spicy Sour Fries', 'snacks', '', 'fries', 'hot'),
('413', 'SPRING ROLL', 'snacks', 'chinese', '', 'hot'),
('759S', 'Sprite', 'drinks', '', 'soft drinks', 'cold'),
('503', 'Spy P.Dices Soup Ndl', 'main', '', 'noodle,soup', 'hot'),
('402', 'Steam Pork Spare Ribs', 'snacks', 'chinese', 'pork', 'hot'),
('709', 'Teh', 'drinks', '', 'tea', 'hot'),
('521', 'VEGETARIAN HOR FUN', 'main', '', 'hor fun', 'hot'),
('522', 'VEGETARIAN RAMEN', 'main', '', '', 'hot'),
('129', 'Wanton Soup', 'drinks', 'chinese', 'pork,soup', 'hot'),
('74', 'Wanton Soup Noodle', 'main', 'chinese', 'pork,noodle,soup', 'hot'),
('2', 'Warm Water', 'drinks', '', '', 'hot'),
('776', 'Watermelon Ice', 'drinks', '', 'fruit', 'cold'),
('776', 'Watermelon Ice-Blended', 'drinks', '', 'fruit', 'cold'),
('556', 'Watermelon \'N\' Jelly', 'drinks', '', 'fruit', 'cold'),
('943', 'WHITE RICE', 'main', '', 'rice', 'hot'),
('251', 'Yao Ma Tei Chix Rice', 'main', 'chinese', 'chicken,rice', 'hot'),
('720', 'Yuzu Tea', 'drinks', '', 'fruit,yuzu,tea', 'hot');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
