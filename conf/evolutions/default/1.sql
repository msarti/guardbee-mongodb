# --- First database schema

# --- !Ups

create table application (
	client_id varchar(255) not null primary key,
	client_secret varchar(255) not null,
	owner_user varchar(255) not null,
	app_name varchar(50) not null,
	app_description varchar(255),
	redirect_uris varchar(255) not null,
	issued_on timestamp not null
);

create table access_token (
  token varchar(255) not null primary key,
  refresh_token varchar(255) not null,
  token_type varchar(255) not null,
  user varchar(255) not null,
  client_id varchar(255) not null,
  issued_on timestamp not null,
  token_expiration timestamp not null,
  refresh_token_expiration timestamp not null
);

create table auth_code (
  code varchar(255) not null primary key,
  user varchar(255) not null,
  redirect_uri varchar(255) not null,
  issued_on timestamp not null,
  expire_on timestamp not null
);

create table scope (
	scope varchar(255) not null primary key,
	description varchar(255) not null
);

create table authorization_request (
  code varchar(255) not null primary key,
  client_id varchar(255) not null,
  user varchar(255) not null,
  response_type varchar(255) not null,
  redirect_uri varchar(255) not null,
  state varchar(255),
  request_timestamp timestamp not null,
  request_expiration timestamp not null
);

create table auth_code_scope (
	scope varchar(255) not null,
	code varchar(255) not null,
	foreign key(scope)      references scope(scope) on delete cascade,
	foreign key(code)  references auth_code(code) on delete cascade
);


create table auth_request_scope (
	scope varchar(255) not null,
	code varchar(255) not null,
	foreign key(scope)      references scope(scope) on delete cascade,
	foreign key(code)  references authorization_request(code) on delete cascade
);

create table user_grant (
  client_id varchar(255) not null,
  user varchar(255) not null,
  granted_on timestamp not null,
  PRIMARY KEY(client_id, user) 
);

  


create table user_grant_scope (
	scope varchar(255) not null,
	client_id varchar(255) not null,
	user varchar(255) not null,
	foreign key(scope)      references scope(scope) on delete cascade,
	foreign key(client_id, user)  references user_grant(client_id, user) on delete cascade
);


create table token_scope (
	scope varchar(255) not null,
	token varchar(255) not null,
	foreign key(scope)      references scope(scope) on delete cascade,
	foreign key(token)  references access_token(token) on delete cascade
);


# --- !Downs

drop table if exists user_grant_scope;
drop table if exists token_scope;
drop table if exists auth_request_scope;
drop table if exists auth_code_scope;

drop table if exists user_grant;
drop table if exists authorization_request;

drop table if exists access_token;
drop table if exists auth_code;

drop table if exists application;
drop table if exists scope;

