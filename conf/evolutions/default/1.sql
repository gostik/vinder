# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table complex (
  key                       bigint not null,
  string_field              varchar(255),
  long_string_field         varchar(256),
  integer_field             integer,
  double_field              varchar(255),
  boolean_field             boolean,
  date_field                timestamp,
  constraint pk_complex primary key (key))
;

create table friendships (
  id                        bigint not null,
  user1_id                  bigint,
  user2_id                  bigint,
  like_to_user1_id          bigint,
  like_to_user2_id          bigint,
  user1delivered            boolean,
  user2delivered            boolean,
  constraint pk_friendships primary key (id))
;

create table likes (
  id                        bigint not null,
  who_id                    bigint,
  whom_id                   bigint,
  photo_id                  bigint,
  result                    boolean,
  constraint pk_likes primary key (id))
;

create table locations (
  id                        bigint not null,
  latitude                  varchar(255),
  longitude                 varchar(255),
  constraint pk_locations primary key (id))
;

create table messages (
  id                        bigint not null,
  created_at                timestamp,
  who_id                    bigint,
  whom_id                   bigint,
  friendship_id             bigint,
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
  user_id                   bigint,
  constraint pk_photos primary key (id))
;

create table settings (
  key                       bigint not null,
  filter_by_pro             boolean,
  filter_is_pro             boolean,
  sex                       integer,
  min_age                   integer,
  max_age                   integer,
  range_in_km               integer,
  hide_age                  boolean,
  constraint pk_settings primary key (key))
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
  reg_id                    varchar(255),
  created_date              timestamp,
  updated_date              timestamp,
  age                       integer,
  settings_key              bigint,
  pro_status                boolean,
  vip_status                boolean,
  constraint pk_users primary key (id))
;


create table users_friendships (
  users_id                       bigint not null,
  friendships_id                 bigint not null,
  constraint pk_users_friendships primary key (users_id, friendships_id))
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

alter table friendships add constraint fk_friendships_user1_1 foreign key (user1_id) references users (id) on delete restrict on update restrict;
create index ix_friendships_user1_1 on friendships (user1_id);
alter table friendships add constraint fk_friendships_user2_2 foreign key (user2_id) references users (id) on delete restrict on update restrict;
create index ix_friendships_user2_2 on friendships (user2_id);
alter table friendships add constraint fk_friendships_likeToUser1_3 foreign key (like_to_user1_id) references likes (id) on delete restrict on update restrict;
create index ix_friendships_likeToUser1_3 on friendships (like_to_user1_id);
alter table friendships add constraint fk_friendships_likeToUser2_4 foreign key (like_to_user2_id) references likes (id) on delete restrict on update restrict;
create index ix_friendships_likeToUser2_4 on friendships (like_to_user2_id);
alter table likes add constraint fk_likes_who_5 foreign key (who_id) references users (id) on delete restrict on update restrict;
create index ix_likes_who_5 on likes (who_id);
alter table likes add constraint fk_likes_whom_6 foreign key (whom_id) references users (id) on delete restrict on update restrict;
create index ix_likes_whom_6 on likes (whom_id);
alter table likes add constraint fk_likes_photo_7 foreign key (photo_id) references photos (id) on delete restrict on update restrict;
create index ix_likes_photo_7 on likes (photo_id);
alter table messages add constraint fk_messages_who_8 foreign key (who_id) references users (id) on delete restrict on update restrict;
create index ix_messages_who_8 on messages (who_id);
alter table messages add constraint fk_messages_whom_9 foreign key (whom_id) references users (id) on delete restrict on update restrict;
create index ix_messages_whom_9 on messages (whom_id);
alter table messages add constraint fk_messages_friendship_10 foreign key (friendship_id) references friendships (id) on delete restrict on update restrict;
create index ix_messages_friendship_10 on messages (friendship_id);
alter table photos add constraint fk_photos_user_11 foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_photos_user_11 on photos (user_id);
alter table users add constraint fk_users_settings_12 foreign key (settings_key) references settings (key) on delete restrict on update restrict;
create index ix_users_settings_12 on users (settings_key);



alter table users_friendships add constraint fk_users_friendships_users_01 foreign key (users_id) references users (id) on delete restrict on update restrict;

alter table users_friendships add constraint fk_users_friendships_friendsh_02 foreign key (friendships_id) references friendships (id) on delete restrict on update restrict;

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

drop table if exists users_friendships;

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

