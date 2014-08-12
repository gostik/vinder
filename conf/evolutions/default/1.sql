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

create table friendships (
  id                        bigint not null,
  who_id                    bigint,
  whom_id                   bigint,
  delivered                 boolean,
  constraint pk_friendships primary key (id))
;

create table likes (
  id                        bigint not null,
  who_id                    bigint,
  whom_id                   bigint,
  result                    boolean,
  constraint pk_likes primary key (id))
;

create table locations (
  id                        bigint not null,
  latitude                  double,
  longitude                 double,
  constraint pk_locations primary key (id))
;

create table messages (
  id                        bigint not null,
  message                   varchar(255),
  constraint pk_messages primary key (id))
;

create table photos (
  id                        bigint not null,
  url75                     varchar(255),
  url130                    varchar(255),
  url604                    varchar(255),
  url807                    varchar(255),
  url1280                   varchar(255),
  url2560                   varchar(255),
  ID_BOOK                   bigint,
  constraint pk_photos primary key (id))
;

create table settings (
  id                        bigint not null,
  filter_by_pro             boolean,
  filter_is_pro             boolean,
  pro_status                boolean,
  vip_status                boolean,
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
  sex                       integer,
  uid                       bigint,
  created_date              timestamp,
  updated_date              timestamp,
  settings_id               bigint,
  constraint pk_users primary key (id))
;

create sequence complex_seq;

create sequence friendships_seq;

create sequence likes_seq;

create sequence locations_seq;

create sequence messages_seq;

create sequence photos_seq;

create sequence settings_seq;

create sequence simple_seq;

create sequence users_seq;

alter table friendships add constraint fk_friendships_who_1 foreign key (who_id) references users (id) on delete restrict on update restrict;
create index ix_friendships_who_1 on friendships (who_id);
alter table friendships add constraint fk_friendships_whom_2 foreign key (whom_id) references users (id) on delete restrict on update restrict;
create index ix_friendships_whom_2 on friendships (whom_id);
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

drop table if exists friendships;

drop table if exists likes;

drop table if exists locations;

drop table if exists messages;

drop table if exists photos;

drop table if exists settings;

drop table if exists simple;

drop table if exists users;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists complex_seq;

drop sequence if exists friendships_seq;

drop sequence if exists likes_seq;

drop sequence if exists locations_seq;

drop sequence if exists messages_seq;

drop sequence if exists photos_seq;

drop sequence if exists settings_seq;

drop sequence if exists simple_seq;

drop sequence if exists users_seq;

