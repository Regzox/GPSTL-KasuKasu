-- Schema Relationnel de la base de donnees :  KASUDB

-- ----------------------------------------------------------------------------
-- STRUCTURE DE LA TABLE DES UTILISATEURS (USERS)
CREATE TABLE IF NOT EXISTS `USERS`(
					`id` INT(100) NOT NULL,
					`email` VARCHAR(255) NOT NULL,
					`mdp` VARCHAR(255) NOT NULL,
					`nom` VARCHAR(255) NOT NULL,
					`prenom` VARCHAR(255) NOT NULL,
					`numero` VARCHAR(255) NOT NULL,
					`accountdate` TIMESTAMP NOT NULL,
					`checked` INT(1) DEFAULT 0 
-- 0(waiting for confirmation) , 1(confirmed)
					);				
					
-- Index pour la table USERS : definition de la cle primaire
ALTER TABLE `USERS` ADD PRIMARY KEY (`id`);

-- AUTO_INCREMENT pour la table USERS
ALTER TABLE `USERS` MODIFY `id` int(100) NOT NULL AUTO_INCREMENT;

-- ----------------------------------------------------------------------------

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

-- STRUCTURE DE LA TABLE DES AMIS (FRIENDS)
CREATE TABLE IF NOT EXISTS FRIENDS 
	(id1 INT(100) NOT NULL REFERENCES USERS (id),
	 id2 INT(100) NOT NULL REFERENCES USERS (id));
	 
-- STRUCTURE DE LA TABLE DES REQUETES D'AMIS (FRIENDREQUESTS)
CREATE TABLE IF NOT EXISTS FRIENDREQUESTS
	(idfrom INT(100) NOT NULL REFERENCES USERS (id),
	 idto INT(100) NOT NULL REFERENCES USERS (id));

-- ----------------------------------------------------------------------------
-- STRUCTURE DE LA TABLE DES IMAGES (IMAGES)
CREATE TABLE IF NOT EXISTS IMAGES 
	(`user_id` INT(100) NOT NULL REFERENCES USERS (id),
	 `path` VARCHAR(255) NOT NULL);
ALTER TABLE `IMAGES` ADD PRIMARY KEY (`user_id`);

-- --------------------------------------------------------
--
-- Table structure for table `POINT_EMPRUNT`
--
CREATE TABLE `POINT_EMPRUNT` (
  `id` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `nom` varchar(20) NOT NULL,
  `lat` double NOT NULL,
  `lon` double NOT NULL,
  `radius` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `POINT_EMPRUNT`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
  
  ALTER TABLE `POINT_EMPRUNT`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`);
  
  ALTER TABLE `POINT_EMPRUNT`
  ADD CONSTRAINT `POINT_EMPRUNT_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `USERS` (`id`);


-- --------------------------------------------------------

--
-- Table structure for table `POINT_PRET`
--

CREATE TABLE `POINT_PRET` (
  `id` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `nom` varchar(20) NOT NULL,
  `lat` double NOT NULL,
  `lon` double NOT NULL,
  `radius` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `POINT_PRET`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
  
ALTER TABLE `POINT_PRET`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`);
  
ALTER TABLE `POINT_PRET`
  ADD CONSTRAINT `POINT_PRET_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `USERS` (`id`);