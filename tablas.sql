DROP TABLE andlun_registry;

DROP TABLE andlun_user_game;

CREATE TABLE IF NOT EXISTS andlun_user_game(
    
	id_user SERIAL,
    
	name_user varchar(10) UNIQUE NOT NULL,
    
	passwd varchar(60) NOT NULL,
    
	email varchar(30) UNIQUE NOT NULL,
    
	Constraint Pk_user PRIMARY KEY (id_user),

	CONSTRAINT CH_email CHECK (email ~* '^[A-Za-z0-9._%-]+@+[A-Za-z0-9.-]+[.]+[A-Za-z]+$')
);

CREATE TABLE IF NOT EXISTS andlun_registry(
    
	id_play SERIAL,
    
	id_user SERIAL,
    
	start_date DATETIME NOT NULL,
    
	end_date DATETIME NOT NULL,
    
	speed REAL NOT NULL,
    
	CONSTRAINT PK_idplay PRIMARY KEY(id_play),
    
	Constraint Fk_IDuser Foreign Key (id_user) references andlun_user_game (id_user)

);


insert into andlun_user_game (name_user,passwd,email) values ('admin','6652f1488aff7d45ce587ebf5d2c0efb','Admin@gmail.com');

insert into andlun_registry (id_user,start_date,end_date,speed) values (1,'14/08/2016 15:05','15/08/2016 15:10',-6.9);

select * from andlun_registry;