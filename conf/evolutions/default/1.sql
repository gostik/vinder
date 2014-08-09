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

create table invitations (
  id                        bigint not null,
  who_id                    bigint,
  whom_id                   bigint,
  constraint pk_invitations primary key (id))
;

create table likes (
  id                        bigint not null,
  who_id                    bigint,
  whom_id                   bigint,
  constraint pk_likes primary key (id))
;

create table messages (
  id                        bigint not null,
  like_result               boolean,
  constraint pk_messages primary key (id))
;

create table photos (
  id                        bigint not null,
  url                       varchar(255),
  ID_BOOK                   bigint,
  constraint pk_photos primary key (id))
;

create table settings (
  id                        bigint not null,
  filter_by_pro             boolean,
  filter_is_pro             boolean,
  sex                       integer,
  min_age                   integer,
  max_age                   integer,
  range_in_km               integer,
  hide_age                  boolean,
  constraint pk_settings primary key (id))
;

create table simple (
  key                       bigint not null,
  name                      varchar(255),
  constraint pk_simple primary key (key))
;

create table users (
  id                        bigint not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  latitude                  double,
  longitude                 double,
  sex                       integer,
  uid                       bigint,
  created_date              timestamp,
  updated_date              timestamp,
  settings_id               bigint,
  constraint pk_users primary key (id))
;

create sequence complex_seq;

create sequence invitations_seq;

create sequence likes_seq;

create sequence messages_seq;

create sequence photos_seq;

create sequence settings_seq;

create sequence simple_seq;

create sequence users_seq;

alter table invitations add constraint fk_invitations_who_1 foreign key (who_id) references users (id) on delete restrict on update restrict;
create index ix_invitations_who_1 on invitations (who_id);
alter table invitations add constraint fk_invitations_whom_2 foreign key (whom_id) references users (id) on delete restrict on update restrict;
create index ix_invitations_whom_2 on invitations (whom_id);
alter table likes add constraint fk_likes_who_3 foreign key (who_id) references users (id) on delete restrict on update restrict;
create index ix_likes_who_3 on likes (who_id);
alter table likes add constraint fk_likes_whom_4 foreign key (whom_id) references users (id) on delete restrict on update restrict;
create index ix_likes_whom_4 on likes (whom_id);
alter table photos add constraint fk_photos_user_5 foreign key (ID_BOOK) references users (id) on delete restrict on update restrict;
create index ix_photos_user_5 on photos (ID_BOOK);
alter table users add constraint fk_users_settings_6 foreign key (settings_id) references settings (id) on delete restrict on update restrict;
create index ix_users_settings_6 on users (settings_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists complex;

drop table if exists invitations;

drop table if exists likes;

drop table if exists messages;

drop table if exists photos;

drop table if exists settings;

drop table if exists simple;

drop table if exists users;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists complex_seq;

drop sequence if exists invitations_seq;

drop sequence if exists likes_seq;

drop sequence if exists messages_seq;

drop sequence if exists photos_seq;

drop sequence if exists settings_seq;

drop sequence if exists simple_seq;

drop sequence if exists users_seq;

