-- Schema Relationnel de la base de donnees :  KASUDB
-- Ceci est a importer depuis MySQL 
-- Tuto pour le faire : https://www.youtube.com/watch?v=NlvzbFrwIdc

-- --------------------------------------------------------

-- STRUCTURE DE LA TABLE DES UTILISATEURS (USERS)
CREATE TABLE IF NOT EXISTS `USERS`(
					`id` INT(11) NOT NULL,
					`email` text NOT NULL,
					`mdp` text NOT NULL,
					`nom` text NOT NULL,
					`prenom` text NOT NULL,
					`numero` INT(10) NOT NULL
					);


-- Index pour la table USERS : definition de la cle primaire
ALTER TABLE `USERS` ADD PRIMARY KEY (`id`);

-- AUTO_INCREMENT pour la table USERS
ALTER TABLE `USERS` MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

-- --------------------------------------------------------