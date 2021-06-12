-- set schema 'public';

-- select * from tables;

drop table if exists user_worker cascade;

drop sequence if exists user_seq;

create sequence user_seq
    increment by 1
    no maxvalue
    no minvalue
    cache 1;

create table user_worker(id integer default nextval('user_seq') not null primary key,
    username text,
    userpassword text,
    creationdate timestamp not null default current_timestamp,
    updatedate timestamp);

select * from user_worker;

select id, username, userpassword from user_worker;

drop table if exists color cascade;

drop sequence if exists color_id_seq;

create sequence color_id_seq
    increment by 1
    no maxvalue
    no minvalue
    cache 1;

create table color(
    id integer default nextval('color_id_seq') not null primary  key,
    name text
);

insert into color(name) values('brown');

select * from color;

insert into color(name) values('black');

select * from color;

insert into color(name) values('white');

select * from color;

drop table if exists coordinates cascade;

drop sequence if exists coordinates_id_seq;

create sequence coordinates_id_seq
    increment by 1
    no maxvalue
    no minvalue
    cache 1;

create table coordinates(id integer default nextval('coordinates_id_seq') not null primary key,
    x numeric,
    y int check (y <= 92));

select * from coordinates;

insert into coordinates(x, y) values(2.2,5);

select * from coordinates;

drop table if exists status cascade;

drop sequence if exists status_id_seq;

create sequence status_id_seq
    increment by 1
    no maxvalue
    no minvalue
    cache 1;

create table status (id integer default nextval('status_id_seq') not null primary key,
    name text);

select * from status;

insert into status(name) values('hired');

select * from status;

insert into status(name) values('fired');

select * from status;

insert into status(name) values('recommended for promotion');

select * from status;

insert into status(name) values('regular');

select * from status;

insert into status(name) values('probation');

select * from status;

drop table if exists person cascade;

drop sequence if exists person_id_seq;

create sequence person_id_seq
    increment by 1
    no maxvalue
    no minvalue
    cache 1;

create table person (id integer default nextval('person_id_seq') not null primary key,
    height numeric  check(height > 0.0),
    weight int check (weight > 0),
    color_id int references color
    );

select * from person;

insert into person(height, weight, color_id) values (23.0, 32, 1);

select * from person;

drop table if exists worker cascade;

drop sequence if exists worker_id_seq;

create sequence worker_id_seq
    increment by 1
    no maxvalue
    no minvalue
    cache 1;

create table worker(id integer default nextval('worker_id_seq') not null primary key,
    worker_id int,
    name text,
    creationdate timestamp not null default current_timestamp,
    salary int check(salary >= 0),
    startdate date check(startdate = null),
    enddate date,
    coordinates_id int references coordinates,
    status_id int references status,
    person_id int references person,
    user_id int references user_worker
    );

select * from worker;

select current_date;

insert into worker(name, creationdate, salary, startdate, enddate, coordinates_id, status_id, person_id)
    values ('frfrfrefr',
    current_date,
    5434,
    '2002-03-04',
    '2002-03-04 00:02:03',
    1,
    1,
    1);

delete from worker where worker_id = 89991;

select * from worker;

select
    worker.name as worker_name, worker.creationdate, worker.salary, worker.startdate, worker.enddate,
    coordinates.x, coordinates.y,
    status."name" as status_name,
    color."name" as color_name,
    person."height", person.weight,
    user_worker.username, user_worker.userpassword
from worker
    inner join coordinates on worker.coordinates_id = coordinates."id"
    inner join status on worker.status_id = status."id"
    inner join person on worker.person_id = person."id"
    inner join color on person.color_id = color."id"
    inner join user_worker on worker.user_id = user_worker."id";






































