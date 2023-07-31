CREATE TYPE chatType AS ENUM ('PRIVATE_CHAT', 'GROUP', 'CHANNEL');

CREATE TABLE Chat(
chat_id serial NOT NULL PRIMARY KEY,
chat_link text NOT NULL,
chat_type chatType not null
);

CREATE FUNCTION create_chat (chat_link text, chat_type chatType)
	RETURNS void AS $$
	BEGIN
		insert into Chat(chat_link, chat_type)
		VALUES(chat_link, chat_type);
	END;$$
LANGUAGE plpgsql;

select create_chat('\link\group1','GROUP');
select create_chat('\link\pv1','PRIVATE_CHAT');
select create_chat('\link\pv2','PRIVATE_CHAT');
select create_chat('\link\channel1','CHANNEL');
select * from Chat;