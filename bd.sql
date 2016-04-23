CREATE database euguardei;
USE euguardei;

CREATE TABLE usuario(
  id_user int(20) NOT NULL AUTO_INCREMENT,
  email varchar(40) NOT NULL,
  senha varchar(32) NOT NULL,
  nome varchar(40) NOT NULL,
  PRIMARY KEY(id_user),
  UNIQUE KEY(email)
  );


CREATE TABLE grupo(
	id_group int(20) NOT NULL AUTO_INCREMENT,
	nome varchar(40) NOT NULL,
	id_admin int(20) NOT NULL,
	PRIMARY KEY (id_group)
);


CREATE TABLE item(
	id_item int(20) NOT NULL AUTO_INCREMENT,
	desc_item varchar(40) NOT NULL,
	local_item varchar(40) NOT NULL,
	date_item varchar(40) NOT NULL,
	id_user int(20) NOT NULL,
	id_group int(20) NULL,
	PRIMARY KEY (id_item)
);

ALTER TABLE item
ADD FOREIGN KEY (id_user)
REFERENCES usuario(id_user);


CREATE TABLE usuario_grupo(
	id int(20) NOT NULL AUTO_INCREMENT,
	id_user int(20) NOT NULL,
	id_group int(20) NOT NULL,
	PRIMARY KEY (id)
);

ALTER TABLE usuario_grupo
ADD FOREIGN KEY (id_user)
REFERENCES usuario(id_user);

ALTER TABLE usuario_grupo
ADD FOREIGN KEY (id_group)
REFERENCES grupo(id_group);

/*-------------------------------------------------------*/

INSERT INTO `item`(`desc_item`,`local_item`,`date_item`,`id_user`)
	 VALUES ('Bola','Casa',DATE_FORMAT(NOW(), '%d/%m/%Y'),9)

