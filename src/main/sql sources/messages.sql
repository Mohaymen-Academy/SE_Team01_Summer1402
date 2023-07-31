
CREATE TYPE messageType AS ENUM ('TEXT', 'IMAGE', 'VIDEO', 'SOUND', 'FILE');
drop table messages;
drop function edit_message;
drop function send_message;
drop function delete_message;
drop function get_all_messages;
drop function get_all_messages_num;
CREATE TABLE messages(
	message_id serial NOT NULL PRIMARY KEY,
	chat_id integer NOT NULL,
	sender integer NOT NULL,
	full_date timestamp NOT NULL default now(),
	message_context text,
	message_type messageType NOT NULL
);

CREATE FUNCTION send_message (chat_id integer, sender integer,
  message_context text, message_type messageType)
  RETURNS void AS $$
	BEGIN
	insert into messages(chat_id, sender, message_context, message_type)
	values(chat_id, sender, message_context, message_type);
	END;$$
LANGUAGE plpgsql;
--drop function edit_message;
CREATE FUNCTION edit_message (msg_id integer, new_messag text)
	RETURNS void AS $$
	BEGIN
	update messages
	set message_context = new_messag
	where messages.message_id = msg_id;
END;$$
LANGUAGE plpgsql;

CREATE or replace FUNCTION delete_message (msg_id integer)
	RETURNS void AS $$
	BEGIN
		delete from Messages
		where messages.message_id = msg_id;
	END;$$
LANGUAGE plpgsql;

CREATE FUNCTION get_all_messages (sendr integer)
	RETURNS table(msg_ontext text, msg_type messageType) AS $$
	BEGIN
	return query
	select message_context, message_type from messages
	where messages.sender = sendr;
	END;$$
LANGUAGE plpgsql;

CREATE FUNCTION get_all_messages_num (sendr integer)
	RETURNS integer AS $$
	BEGIN
		return count(*)
		from Messages
		where sender = sendr;
END;$$
LANGUAGE plpgsql;
drop function avg_messages
------------------------------------------------------------------
CREATE or replace FUNCTION avg_messages(userid integer)
RETURNS float AS $$
Begin
	return 
	(select cast (get_all_messages_num(userid) as float)/
	(select count(distinct sender) from messages));
End;$$
LANGUAGE plpgsql;

select send_message(2,1,'salam','TEXT');
select send_message(3,1,'salam,khubi?','TEXT');
select send_message(1,4,'pic1','IMAGE');
select edit_message(1,'hello');
select send_message(2,1,'ch khbr','TEXT');
select get_all_messages_num(1);
select get_all_messages(1);
select avg_messages(1);
select * from messages;