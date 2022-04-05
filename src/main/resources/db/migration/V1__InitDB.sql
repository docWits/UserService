CREATE table "usr"
(
    id bigint not null constraint "usr_pkey" primary key,
    name varchar (256) not null,
    login varchar (256) not null,
    password varchar (256) not null,
    active varchar (256) default 'ACTIVE',
    last_visit date
);
alter table "usr"
    owner to postgres;

CREATE table "roles"
(
    id bigint not null constraint "role_pkey" primary key,
    name varchar (100) not null
);
alter table "roles"
    owner to postgres;

create table "user_roles"
(
    user_id bigint not null constraint user_id references "usr" on update cascade on delete cascade,
    role_id bigint not null constraint role_id references "roles" on update cascade on delete cascade
);

alter table "user_roles"
    owner to postgres;


insert into roles (id,name) values (1,'USER');
insert into roles (id, name) VALUES (2, 'ADMIN');

insert into usr (id, name, login,password,active, last_visit) VALUES (1, 'User','User','User','ACTIVE',null);
insert into usr (id, name, login,password,active, last_visit) VALUES (2, 'Admin','Admin','Admin','ACTIVE',null);

insert into user_roles (user_id, role_id) VALUES (1,1);
insert into user_roles (user_id, role_id) VALUES (2,1);
insert into user_roles (user_id, role_id) VALUES (2,2);


