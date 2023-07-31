CREATE TYPE EntityType AS ENUM ('USER', 'GROUP', 'CHANNEL');

CREATE TABLE entities(
	id serial unique primary key,
	entityName text unique not null,
	entity_type EntityType NOT NULL,
	name varchar(255) not null,
	bio text,
	picture text,
	created_at timestamptz default now()
);


CREATE TABLE account(
	username text unique primary key not null,
	password text NOT NULL,
	phoneNumber text not null,
	lastname text
);

create table participants(
	entyty1_id integer not null,	
	entyty2_id integer not null,
	message_id integer,
 	primary key(entyty1_id, entyty2_id)
);

select * from Users where Users.username='a' -- ' and Users.password='1234';



select * from Users where Users.username='a'--' and Users.password='fdsfsgdfasd'
