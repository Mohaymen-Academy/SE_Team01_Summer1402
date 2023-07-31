drop table participants;
create table participants(
user_id integer not null,	
chat_id integer not null,
	primary key(user_id,chat_id)
);
create or replace function add_to_participants(usr_id integer,cht_id integer)
RETURNS void AS $$
	BEGIN
	insert into participants(user_id ,chat_id)
	values(usr_id, cht_id);
	END;$$
LANGUAGE plpgsql;

select add_to_participants(1,2);
select add_to_participants(3,2);
select add_to_participants(2,1);
select add_to_participants(3,1);
select add_to_participants(4,1);
select add_to_participants(1,3);
select add_to_participants(2,3);
select * from participants;