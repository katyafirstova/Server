drop table if exists color;

create sequence id
start 1
increment 2
minvalue 1;

select  nextval('id');

create table color(id int, name text)

insert into color(id, name) values(nextval('id'), 'BROWN');

select * from color;

insert into color(id, name) values(nextval('id'),'BLACK');

select * from color

insert into color(id, name) values(nextval('id'), 'WHITE');

select * from color

drop table if exists coordinates;

select nextval('id');

create table coordinates(id int, x int, y int check (y <= 92));

select * from coordinates;

insert into coordinates(id, name, number) values(nextval('id'), 'x', 3);

select * from coordinates;

insert into coordinates(id, name, number) values(nextval('id'), 'y', 8);

select * from coordinates;

drop table if exists status;

select nextval('id');

create table status(id int, name text);

select * from status;

insert into status(id, name) values(nextval('id'), 'HIRED');

select * from status;

insert into status(id, name) values(nextval('id'), 'FIRED');

select * from status;

insert into status(id, name) values(nextval('id'), 'RECOMMENDED FOR PROMOTION');

select * from status;

insert into status(id, name) values(nextval('id'), 'REGULAR');

select * from status;

insert into status(id, name) values(nextval('id'), 'PROBATION');

select * from status;

drop table if exists person;

select nextval('id');

create table person (id int, height int check(height > 0), weight int check (weight > 0));

select * from person;

insert into person(id, height) values (nextval('id'), 23);

select * from person;

insert into person(id, weight) values (nextval('id'), 232);

select * from person;

drop table if exists worker;

select nextval('id');

create table worker(id int, name text, creationDate date, salary int check(salary >= 0)
, startDate date check(startDate = null), endDate date);

select * from worker;

select current_date;

insert into worker(id, name, creationDate, salary, startDate, endDate) values (nextval('id'), 'frfrfrefr', current_date,
5434,'2002-03-04', '2002-03-04 00:02:03');

select * from worker;

drop table if exists user;

select nextval('id');

create table user(id int, username text, userpassword text, creationDate timestamp, updateDate timestamp)






































