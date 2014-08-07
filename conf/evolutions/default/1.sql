# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table complex (
  key                       bigint not null,
  string_field              varchar(255),
  long_string_field         varchar(256),
  integer_field             integer,
  double_field              double,
  boolean_field             boolean,
  date_field                timestamp,
  constraint pk_complex primary key (key))
;

create table simple (
  key                       bigint not null,
  name                      varchar(255),
  constraint pk_simple primary key (key))
;

create table simple1 (
  key                       bigint not null,
  name                      varchar(255),
  constraint pk_simple1 primary key (key))
;

create sequence complex_seq;

create sequence simple_seq;

create sequence simple1_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists complex;

drop table if exists simple;

drop table if exists simple1;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists complex_seq;

drop sequence if exists simple_seq;

drop sequence if exists simple1_seq;

