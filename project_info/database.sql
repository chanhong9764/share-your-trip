-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema sharetrip
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema sharetrip
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sharetrip` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `sharetrip` ;

-- -----------------------------------------------------
-- Table `sharetrip`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sharetrip`.`users` ;

CREATE TABLE IF NOT EXISTS `sharetrip`.`users` (
  `user_id` VARCHAR(32) NOT NULL,
  `user_password` BLOB NULL DEFAULT NULL,
  `user_name` VARCHAR(20) NULL DEFAULT NULL,
  `email` VARCHAR(64) NULL DEFAULT NULL,
  `join_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `salt` VARCHAR(255) NULL DEFAULT NULL,
  `profile` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sharetrip`.`board`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sharetrip`.`board` ;

CREATE TABLE IF NOT EXISTS `sharetrip`.`board` (
  `article_no` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(10000) NULL DEFAULT NULL,
  `createdAt` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` VARCHAR(32) NULL DEFAULT NULL,
  PRIMARY KEY (`article_no`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `sharetrip`.`users` (`user_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 84
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sharetrip`.`board_images`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sharetrip`.`board_images` ;

CREATE TABLE IF NOT EXISTS `sharetrip`.`board_images` (
  `file_id` INT NOT NULL AUTO_INCREMENT,
  `original_name` VARCHAR(100) NULL DEFAULT NULL,
  `article_no` INT NOT NULL,
  `save_folder` VARCHAR(1000) NULL DEFAULT NULL,
  `save_file` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`file_id`),
  INDEX `board_images_idx` (`article_no` ASC) VISIBLE,
  CONSTRAINT `board_images`
    FOREIGN KEY (`article_no`)
    REFERENCES `sharetrip`.`board` (`article_no`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 79
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sharetrip`.`hashtag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sharetrip`.`hashtag` ;

CREATE TABLE IF NOT EXISTS `sharetrip`.`hashtag` (
  `hashtag_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`hashtag_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 125
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sharetrip`.`board_tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sharetrip`.`board_tag` ;

CREATE TABLE IF NOT EXISTS `sharetrip`.`board_tag` (
  `board_tag_id` INT NOT NULL AUTO_INCREMENT,
  `article_no` INT NOT NULL,
  `hashtag_id` INT NOT NULL,
  PRIMARY KEY (`board_tag_id`),
  INDEX `hash_board_fk_idx` (`hashtag_id` ASC) VISIBLE,
  INDEX `hash_board_fk2_idx` (`article_no` ASC) VISIBLE,
  CONSTRAINT `hash_board_fk`
    FOREIGN KEY (`hashtag_id`)
    REFERENCES `sharetrip`.`hashtag` (`hashtag_id`)
    ON DELETE CASCADE,
  CONSTRAINT `hash_board_fk2`
    FOREIGN KEY (`article_no`)
    REFERENCES `sharetrip`.`board` (`article_no`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 137
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sharetrip`.`chatting_room`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sharetrip`.`chatting_room` ;

CREATE TABLE IF NOT EXISTS `sharetrip`.`chatting_room` (
  `room_id` INT NOT NULL AUTO_INCREMENT,
  `identifier` VARCHAR(100) NULL DEFAULT NULL,
  `createdAt` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `content` VARCHAR(255) NOT NULL,
  `room_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`room_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 106
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sharetrip`.`chatting`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sharetrip`.`chatting` ;

CREATE TABLE IF NOT EXISTS `sharetrip`.`chatting` (
  `chatting_id` INT NOT NULL AUTO_INCREMENT,
  `message` VARCHAR(10000) NOT NULL,
  `createdAt` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` VARCHAR(32) NOT NULL,
  `room_id` INT NOT NULL,
  PRIMARY KEY (`chatting_id`),
  INDEX `user_chatroom_fk_idx` (`user_id` ASC) VISIBLE,
  INDEX `user_chatroom_fk2_idx` (`room_id` ASC) VISIBLE,
  CONSTRAINT `user_chatroom_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `sharetrip`.`users` (`user_id`)
    ON DELETE CASCADE,
  CONSTRAINT `user_chatroom_fk2`
    FOREIGN KEY (`room_id`)
    REFERENCES `sharetrip`.`chatting_room` (`room_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 768
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sharetrip`.`chatting_participant`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sharetrip`.`chatting_participant` ;

CREATE TABLE IF NOT EXISTS `sharetrip`.`chatting_participant` (
  `participant_id` INT NOT NULL AUTO_INCREMENT,
  `isAccepted` TINYINT NULL DEFAULT '0',
  `room_id` INT NOT NULL,
  `user_id` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`participant_id`),
  INDEX `chatroom_user_fk1_idx` (`user_id` ASC) VISIBLE,
  INDEX `chatroom_user_fk2_idx` (`room_id` ASC) VISIBLE,
  CONSTRAINT `chatroom_user_fk1`
    FOREIGN KEY (`user_id`)
    REFERENCES `sharetrip`.`users` (`user_id`)
    ON DELETE CASCADE,
  CONSTRAINT `chatroom_user_fk2`
    FOREIGN KEY (`room_id`)
    REFERENCES `sharetrip`.`chatting_room` (`room_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 213
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sharetrip`.`comments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sharetrip`.`comments` ;

CREATE TABLE IF NOT EXISTS `sharetrip`.`comments` (
  `comment_id` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(500) NULL DEFAULT NULL,
  `createdAt` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` VARCHAR(32) NOT NULL,
  `article_no` INT NOT NULL,
  PRIMARY KEY (`comment_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `rm_board_id_idx` (`article_no` ASC) VISIBLE,
  CONSTRAINT `rm_board_id`
    FOREIGN KEY (`article_no`)
    REFERENCES `sharetrip`.`board` (`article_no`)
    ON DELETE CASCADE,
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `sharetrip`.`users` (`user_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 41
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sharetrip`.`recommend`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sharetrip`.`recommend` ;

CREATE TABLE IF NOT EXISTS `sharetrip`.`recommend` (
  `recommend_id` INT NOT NULL AUTO_INCREMENT,
  `createdAt` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` VARCHAR(32) NOT NULL,
  `article_no` INT NOT NULL,
  PRIMARY KEY (`recommend_id`),
  INDEX `user_recommend_fk_idx` (`user_id` ASC) VISIBLE,
  INDEX `board_recommend_fk_idx` (`article_no` ASC) VISIBLE,
  CONSTRAINT `board_recommend_fk`
    FOREIGN KEY (`article_no`)
    REFERENCES `sharetrip`.`board` (`article_no`)
    ON DELETE CASCADE,
  CONSTRAINT `user_recommend_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `sharetrip`.`users` (`user_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 39
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sharetrip`.`trip_info`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sharetrip`.`trip_info` ;

CREATE TABLE IF NOT EXISTS `sharetrip`.`trip_info` (
  `trip_info_id` INT NOT NULL AUTO_INCREMENT,
  `y` VARCHAR(100) NULL DEFAULT NULL,
  `x` VARCHAR(100) NULL DEFAULT NULL,
  `road_address_name` VARCHAR(1000) NULL DEFAULT NULL,
  `sequense` INT NULL DEFAULT '0',
  `category` VARCHAR(255) NULL DEFAULT NULL,
  `createdAt` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `room_id` INT NULL DEFAULT NULL,
  `place_name` VARCHAR(100) NULL DEFAULT NULL,
  `place_url` VARCHAR(200) NULL DEFAULT NULL,
  `phone` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`trip_info_id`),
  INDEX `chatroom_trip_idx` (`room_id` ASC) VISIBLE,
  CONSTRAINT `chatroom_trip`
    FOREIGN KEY (`room_id`)
    REFERENCES `sharetrip`.`chatting_room` (`room_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 29
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
