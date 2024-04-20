create table rdm_connect_info
(
    id           INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) primary key,
    title        varchar(64),
    host         varchar(64),
    port         int,
    username     varchar(64),
    password     varchar(64)
)

