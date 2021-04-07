create table role
(
	role_id int unsigned auto_increment
		primary key,
	name    varchar(45) not null
);


create table user
(
	user_id    int unsigned auto_increment
		primary key,
	user_role  int unsigned                 not null,
	username   varchar(45)                  not null,
	password   varchar(81)                  not null,
	email      varchar(45)                  not null,
	phone      varchar(45)                  not null,
	first_name varchar(45)                  not null,
	last_name  varchar(45)                  not null,
	active     tinyint unsigned default '0' not null,
	constraint fk_user_role_id
		foreign key (user_role) references role (role_id)
);

create index role_id_idx
	on user (user_role);

create table user_confirmation
(
	token_id           int unsigned auto_increment
		primary key,
	confirmation_token varchar(255)                 not null,
	user_id            int unsigned                 not null,
	created_date       datetime                     not null,
	is_confirmed       tinyint unsigned default '0' not null,
	constraint fk_userCon_user_id
		foreign key (user_id) references user (user_id)
);

create index fk_usrCon_user_id_idx
	on user_confirmation (user_id);


