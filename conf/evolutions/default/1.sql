# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  id                        bigint auto_increment not null,
  username                  varchar(255),
  password                  varchar(255),
  company                   varchar(255),
  age                       integer,
  address                   varchar(255),
  constraint pk_account primary key (id))
;

create table company (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_company primary key (id))
;

create table computer (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  introduced                datetime,
  discontinued              datetime,
  company_id                bigint,
  constraint pk_computer primary key (id))
;

create table pet (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  gender                    integer,
  constraint ck_pet_gender check (gender in (0,1)),
  constraint pk_pet primary key (id))
;

create table word (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_word primary key (id))
;

alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;
create index ix_computer_company_1 on computer (company_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table account;

drop table company;

drop table computer;

drop table pet;

drop table word;

SET FOREIGN_KEY_CHECKS=1;

