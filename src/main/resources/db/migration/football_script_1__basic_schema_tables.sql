create table User (
  id binary(16) not null,
  username varchar(100) not null unique,
  password varchar(60) not null,
  first_name varchar(60) null,
  last_name varchar(60) null,
  displayName varchar (20) null,
  role varchar (10) not null,
  primary key (id)
);