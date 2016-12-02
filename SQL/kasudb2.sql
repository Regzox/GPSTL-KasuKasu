-- MySQL Script generated by MySQL Workbench
-- 12/02/16 11:48:30
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema kasudb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema kasudb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `kasudb` DEFAULT CHARACTER SET utf8 ;
USE `kasudb` ;

-- -----------------------------------------------------
-- Table `kasudb`.`df`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kasudb`.`df` (
  `word` VARCHAR(255) NOT NULL,
  `df` INT(100) NOT NULL,
  `defdate` DATETIME NOT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kasudb`.`friendrequests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kasudb`.`friendrequests` (
  `idfrom` INT(100) NOT NULL,
  `idto` INT(100) NOT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kasudb`.`friends`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kasudb`.`friends` (
  `id1` INT(100) NOT NULL,
  `id2` INT(100) NOT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kasudb`.`images`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kasudb`.`images` (
  `user_id` INT(100) NOT NULL,
  `path` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kasudb`.`tf`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kasudb`.`tf` (
  `word` VARCHAR(255) NOT NULL,
  `docID` VARCHAR(255) NOT NULL,
  `tf` INT(100) NOT NULL,
  `defdate` DATETIME NOT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kasudb`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kasudb`.`users` (
  `id` INT(100) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `mdp` VARCHAR(255) NOT NULL,
  `nom` VARCHAR(255) NOT NULL,
  `prenom` VARCHAR(255) NOT NULL,
  `numero` VARCHAR(255) NOT NULL,
  `accountdate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `checked` INT(1) NULL DEFAULT '0',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kasudb`.`point_emprunt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kasudb`.`point_emprunt` (
  `id` INT NOT NULL,
  `lon` DECIMAL(11,8) NOT NULL,
  `lat` DECIMAL(10,8) NOT NULL,
  `radius` INT NULL,
  `nom` VARCHAR(45) NULL,
  `users_id` INT(100) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_point_emprunt_users_idx` (`users_id` ASC),
  CONSTRAINT `fk_point_emprunt_users`
    FOREIGN KEY (`users_id`)
    REFERENCES `kasudb`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kasudb`.`objet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kasudb`.`objet` (
  `id` INT NOT NULL,
  `description` LONGTEXT NULL,
  `nom` VARCHAR(45) NOT NULL,
  `date` TIMESTAMP NULL,
  `id_owner` INT(100) NOT NULL,
  `id_emprunteur` INT(100) NULL,
  `debut` TIMESTAMP NULL,
  `fin` TIMESTAMP NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_objet_users1_idx` (`id_owner` ASC),
  INDEX `fk_objet_users2_idx` (`id_emprunteur` ASC),
  CONSTRAINT `fk_objet_users1`
    FOREIGN KEY (`id_owner`)
    REFERENCES `kasudb`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_objet_users2`
    FOREIGN KEY (`id_emprunteur`)
    REFERENCES `kasudb`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kasudb`.`point_pret`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kasudb`.`point_pret` (
  `id` INT NOT NULL,
  `lon` DECIMAL(11,8) NOT NULL,
  `lat` DECIMAL(10,8) NOT NULL,
  `radius` INT NULL,
  `nom` VARCHAR(45) NULL,
  `id_objet` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_point_pret_objet1_idx` (`id_objet` ASC),
  CONSTRAINT `fk_point_pret_objet1`
    FOREIGN KEY (`id_objet`)
    REFERENCES `kasudb`.`objet` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kasudb`.`demande_emprunt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kasudb`.`demande_emprunt` (
  `users_id` INT(100) NOT NULL,
  `objet_id` INT NOT NULL,
  `debut` TIMESTAMP NULL,
  `fin` TIMESTAMP NULL,
  `date` TIMESTAMP NULL,
  PRIMARY KEY (`users_id`, `objet_id`),
  INDEX `fk_users_has_objet_objet1_idx` (`objet_id` ASC),
  INDEX `fk_users_has_objet_users1_idx` (`users_id` ASC),
  CONSTRAINT `fk_users_has_objet_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `kasudb`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_objet_objet1`
    FOREIGN KEY (`objet_id`)
    REFERENCES `kasudb`.`objet` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kasudb`.`abonnement`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kasudb`.`abonnement` (
  `users_id` INT(100) NOT NULL,
  `objet_id` INT NOT NULL,
  `date` TIMESTAMP NULL,
  PRIMARY KEY (`users_id`, `objet_id`),
  INDEX `fk_users_has_objet_objet2_idx` (`objet_id` ASC),
  INDEX `fk_users_has_objet_users2_idx` (`users_id` ASC),
  CONSTRAINT `fk_users_has_objet_users2`
    FOREIGN KEY (`users_id`)
    REFERENCES `kasudb`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_objet_objet2`
    FOREIGN KEY (`objet_id`)
    REFERENCES `kasudb`.`objet` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kasudb`.`notification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kasudb`.`notification` (
  `id` INT NOT NULL,
  `message` TEXT NOT NULL,
  `date` TIMESTAMP NULL,
  `vue` TINYINT(1) NULL,
  `users_id` INT(100) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_notification_users1_idx` (`users_id` ASC),
  CONSTRAINT `fk_notification_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `kasudb`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;