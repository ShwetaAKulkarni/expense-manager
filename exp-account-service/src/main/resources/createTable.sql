-- Tobe used if we dont want to use hibernate 
--drop table  if exists user;
--      create table user (
--        user_id bigint generated by default as identity,
--        firstName varchar(255),
--        lastName varchar(255),
--        username varchar(255),
--        password varchar(255),
--        email varchar(255),
--        active boolean not null,
--        primary key (user_id)
--    );
--
--
--    drop table  if exists account_transaction;
--    create table account_transaction (
--        trans_Id bigint generated by default as identity,
--        description varchar(255),  
--        transaction_type varchar(255),
--        date timestamp,
--        amount decimal(19,2),
--        primary key (trans_Id)
--    );
--
--    drop table  if exists user_transaction;
--     create table user_transaction (
--        trans_id bigint not null,
--        user_id bigint,
--        primary key (trans_id)
--    );
