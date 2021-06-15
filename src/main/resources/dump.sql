
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `booktify` DEFAULT CHARACTER SET utf8 ;
USE `booktify` ;

-- -----------------------------------------------------
-- Table books
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `books` (
  `book_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL,
  `author` VARCHAR(45) NULL,
  `content` VARCHAR(300) NULL,
  `content_short` VARCHAR(100) NULL,
  `publisher` VARCHAR(30) NULL,
  `publisher_date` DATE NULL,
  `pages` INT NULL,
  `language` VARCHAR(20) NULL,
  `url_datails` VARCHAR(100) NULL,
  `url_downland` VARCHAR(100) NULL,
  `cover` VARCHAR(45) NULL,
  PRIMARY KEY (`book_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table type user
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `type_user` (
  `type_user_id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`type_user_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table users
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL UNIQUE,
  `password` VARCHAR(300) NOT NULL,
  `fullname` VARCHAR(45) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL,
  `token` VARCHAR(300) NULL,
  `type_user` INT NOT NULL,
  PRIMARY KEY (`user_id`),
    FOREIGN KEY (`type_user`)
    REFERENCES `type_user` (`type_user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table rating
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rating` (
  `user_id` INT NOT NULL,
  `book_id` INT NOT NULL,
  `score` INT(1) NULL,
  `comment` VARCHAR(200) NULL,
  `rating_id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`rating_id`),
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (`book_id`)
    REFERENCES `books` (`book_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table readList
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `readlist` (
  `readlist_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`readlist_id`),
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table readLists-books
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `readlists_books` (
  `readlist_id` INT NOT NULL,
  `book_id` INT NOT NULL,
  `readlist_book_id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`readlist_book_id`),
    FOREIGN KEY (`readlist_id`)
    REFERENCES `readList` (`readlist_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (`book_id`)
    REFERENCES `books` (`book_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO `type_user` (`type_user_id`, `type`) VALUES
(1, 'ADMIN'),
(2, 'PREMIUM'),
(3, 'FREE');


INSERT INTO `users` (`user_id`, `email`, `password`, `fullname`, `lastname`, `created_at`, `updated_at`, `token`, `type_user`) VALUES
(1, 'admin@gmail.com', '$2a$10$IIfInBBRThZvEQ2Odyw63.06uj0tyN8CjVsztLCrmeIopVYScWGmO', 'AdminName', 'AdminLastname', '2021-06-13 12:29:53', '2021-06-13 12:29:53', NULL, 1);

INSERT INTO `booktify`.`books`  VALUES ('1', 'demo', 'demo', 'demo', 'demo', 'demo', '2021-06-13', '100', 'lan', 'https', 'https', 'https');