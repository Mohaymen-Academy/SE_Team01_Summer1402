CREATE TYPE EntityType AS ENUM ('USER', 'GROUP', 'CHANNEL');

drop table entities;
CREATE TABLE entities(
	id serial unique primary key,
	username varchar(255) unique not null,
	entity_type EntityType NOT NULL,
	name varchar(255) not null,
	bio text,
	picture text,
	created_at timestamptz default now()
);


CREATE TABLE account(
	username varchar(255) unique primary key not null,
	password text NOT NULL,
	phoneNumber varchar(13) unique not null,
	lastname varchar(255)
);

create table participants(
	entyty1_id integer not null,	
	entyty2_id integer not null,
	last_message_seen integer,
 	primary key(entyty1_id, entyty2_id)
);
drop table messages
CREATE TABLE messages(
	message_id serial NOT NULL PRIMARY KEY,
	entity_id integer NOT NULL,
	sender_id integer NOT NULL,
	full_date timestamp NOT NULL default now(),
	message_text text,
	message_byte text,
	file_extension text
);
select * from entities;
select * from account;
