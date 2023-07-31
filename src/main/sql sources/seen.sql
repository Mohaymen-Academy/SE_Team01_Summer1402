create table seen(
  message_id integer not null,
  receiver_id integer not null
);

create or replace function add_to_seen(msg_id integer,rcv_id integer)
RETURNS void AS $$
	BEGIN
	insert into seen(message_id ,receiver_id)
	values(msg_id, rcv_id);
	END;$$
LANGUAGE plpgsql;