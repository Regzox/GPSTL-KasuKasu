-- Schema Relationnel de la base de donnees :  KASUDB

-- ----------------------------------------------------------------------------
-- STRUCTURE DE LA TABLE DES UTILISATEURS (USERS)
CREATE TABLE IF NOT EXISTS `USERS`(
					`id` INT(100) NOT NULL,
					`email` VARCHAR(255) NOT NULL,
					`mdp` VARCHAR(255) NOT NULL,
					`nom` VARCHAR(255) NOT NULL,
					`prenom` VARCHAR(255) NOT NULL,
					`numero` CHAR(10) NOT NULL,
					`accountdate` TIMESTAMP NOT NULL,
					`checked` INT(1) DEFAULT 0 
-- 0(waiting for confirmation) , 1(confirmed)
					);				
					
-- Index pour la table USERS : definition de la cle primaire
ALTER TABLE `USERS` ADD PRIMARY KEY (`id`);

-- AUTO_INCREMENT pour la table USERS
ALTER TABLE `USERS` MODIFY `id` int(100) NOT NULL AUTO_INCREMENT;

-- ----------------------------------------------------------------------------
<<<<<<< HEAD
-- STRUCTURE DE LA TABLE DES TERM FREQUENCY (TF)
CREATE TABLE IF NOT EXISTS `TF` (
				`word` VARCHAR(255) NOT NULL,
				`docID` VARCHAR(255) NOT NULL,
				`tf` INT(100) NOT NULL,
				`defdate` DATETIME NOT NULL
				);
					
-- ----------------------------------------------------------------------------
-- STRUCTURE DE LA TABLE DES DOCUMENT FREQUENCY (DF)
CREATE TABLE IF NOT EXISTS `DF`(
				`word` VARCHAR(255) NOT NULL,
				`df` INT(100) NOT NULL,
				`defdate` DATETIME NOT NULL
				);
				
				
				
=======

-- STRUCTURE DE LA TABLE DES AMIS (FRIENDS)
CREATE TABLE IF NOT EXISTS FRIENDS 
	(id1 INT(100) NOT NULL REFERENCES USERS (id),
	 id2 INT(100) NOT NULL REFERENCES USERS (id));
	 
-- STRUCTURE DE LA TABLE DES REQUETES D'AMIS (FRIENDREQUESTS)
CREATE TABLE IF NOT EXISTS FRIENDREQUESTS
	(idfrom INT(100) NOT NULL REFERENCES USERS (id),
	 idto INT(100) NOT NULL REFERENCES USERS (id));
>>>>>>> 58ea9b905acd1137e21adbb7917758ce0be5a199
