-- liquibase formatted sql

-- changeset lucija:0.0.1
CREATE TABLE moneydistributor.user_tbl (
  id         bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
  first_name nvarchar(25)       NOT NULL,
  last_name  nvarchar(25)       NOT NULL,
  username   nvarchar(15)       NOT NULL UNIQUE,
  email      nvarchar(70)       NOT NULL UNIQUE
);
create table moneydistributor.transaction_tbl (
  id           bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
  from_user_id bigint             NOT NULL,
  to_user_id   bigint             NOT NULL,
  date         bigint,
  amount       decimal(16, 2)     NOT NULL,
  description  blob,

  index (from_user_id),
  index (to_user_id),
  foreign key (from_user_id)
  references moneydistributor.user_tbl (id),
  foreign key (to_user_id)
  references moneydistributor.user_tbl (id)
);

create table moneydistributor.balance_tbl (
  first_user_id  bigint         NOT NULL,
  second_user_id bigint         NOT NULL,
  amount         decimal(16, 2) NOT NULL,

  INDEX (first_user_id),
  INDEX (second_user_id),
  PRIMARY KEY (first_user_id, second_user_id)
);






