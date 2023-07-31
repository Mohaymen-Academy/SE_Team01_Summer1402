-- Database: Messenger

-- DROP DATABASE IF EXISTS "Messenger";

CREATE DATABASE "Messenger"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1256'
    LC_CTYPE = 'English_United States.1256'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

drop table Users;
drop sequence Users_user_id;
drop function add_user;
drop function edit_bio;
drop function delete_account;

create table Users(
	user_id serial unique primary key,
	phoneNumber varchar(11) unique not null,
	username varchar(255) unique not null,
	name varchar(255) not null,
	lastname varchar(255) not null,
	bio text,
	password varchar(255),
-- 	groups_urls  text[],
-- 	channel_urls text[],
	created_at timestamptz default now()
);

/***********************sequnces**************/
CREATE SEQUENCE Users_user_id
  start with 1
  increment by 1
  NO MAXVALUE;
/**********************Functions****************************/ 
--add to the user's group urls
-- drop function add_to_group;
-- create or replace function add_to_group(namee varchar(255),new_url text)
-- returns void as $$
-- declare 	
-- 	list_urls text[];
-- 	is_username bool:=false;
-- Begin
-- 	--check if the username exists in the table
-- -- 	select exists(select *
-- -- 				from Users
-- -- 				where username='maryam')
-- -- 				into is_username;
-- -- 	if is_username then
-- 		-- Get the list of values from the specific row
--  		    select groups_urls into list_urls
--    			from Users
--    			where Users.username=namee;
	
-- 		-- Check if the search_value exists in the list
--    		 if new_url = any(list_urls) then
--      		update Users
-- 			set groups_urls=array_append(groups_urls,new_url)
-- 			where Users.username=namee;
--    		 end if;
-- 	--end if;
-- End; $$
-- LANGUAGE plpgsql;  

--add a user to the table
CREATE or replace FUNCTION add_user(phoneNumber varchar(255), username varchar(255), 
						 name varchar(255), lastname varchar(255), 
						 password varchar(255), bio varchar(255))
RETURNS void AS $$
Begin
	Insert into Users(phoneNumber,username,name,lastname,password,bio)
    values(phoneNumber,username,name,lastname,password,bio);
	
End;$$
LANGUAGE plpgsql;

-- delete a user from the table
CREATE or replace FUNCTION delete_account(namee varchar(255))
RETURNS void AS $$
Begin
	DELETE FROM Users
	where Users.username=namee;
End;$$
LANGUAGE plpgsql;

--change biography
CREATE or replace FUNCTION edit_bio(namee varchar(255),new_bio text)
RETURNS void AS $$
Begin
	update Users
	set bio=new_bio
	where Users.username=namee;
End;$$
LANGUAGE plpgsql;

/********************************************************/
select add_user('09124147656','sanaMousavi','sana','mousavi',null,'contact me');
select add_user('09137894765','aliiiiiiii','ali','alavi','02344','working');
select add_user('09109827622','knockknock','taghi','taghavi','1381',null);
select add_user('09121234633','saraMahmoudi','sara','mahmoudi',null,'studying');
select edit_bio('sanaMousavi','not in the mood to talk');
select add_user('091212346','xxx','xx','x',null,null);
select delete_account('xxx');
select * from Users;


--find the people in relation with x
select distinct count(*) from participants where chat_id in(
select chat_id from participants
where user_id=3
	) and user_id!=3;
---











