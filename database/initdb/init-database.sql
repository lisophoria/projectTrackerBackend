drop table public.task;
drop table public.category;
drop table public.token;
drop table public._user;

create table if not exists public._user (
    userId serial primary key not null,
    firstname varchar (255),
    lastname varchar (255),
    email varchar (255),
    password varchar (255),
    role varchar (255)
);

create table if not exists public.token (
    tokenId serial primary key not null,
    token varchar (255),
    tokenType varchar (255),
    revoked boolean,
    expired boolean,
    userId int references public._user (userId)
);

create table if not exists public.category (
   categoryId serial primary key not null,
   categoryName varchar (255),
   userId serial references public._user (userId)
);

create table if not exists public.task (
   taskId serial primary key not null,
   categoryId serial references public.category (categoryId),
   taskName varchar (255),
   description varchar (255),
   status boolean,
   taskDate date
);

create or replace function onUserDelete()
    returns trigger as $$
begin
    delete from token where userId = old.userId;
    delete from category where userId = old.userId;
    return old;
end; $$
    language plpgsql;

create trigger beforeUserDelete
    before delete on public._user
    for each row execute function onUserDelete();

create or replace function onCategoryDelete()
    returns trigger as $$
begin
    delete from task where categoryId = old.categoryId;
    return old;
end; $$
    language plpgsql;

create trigger beforeCategoryDelete
    before delete on public.category
    for each row execute function onCategoryDelete();