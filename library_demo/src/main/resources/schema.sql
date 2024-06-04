drop table if exists tbl_student;
create table tbl_student
(
    id       int         not null auto_increment,
    stuNum   varchar(16) not null,
    name     varchar(16) not null,
    password varchar(16) not null,
    primary key (id),
    unique (stuNum)
);

drop table if exists tbl_studyroom;
create table tbl_studyroom
(
    id           int         not null auto_increment,
    buildingNum  varchar(16) not null,
    classRoomNum varchar(16) not null,
    seat_Id_Set  varchar(255) not null,
    if_Open      boolean,
    close_Time        varchar(16) not null,
    open_Time          varchar(16) not null,
    primary key (id)
);

drop table if exists tbl_seat;
create table tbl_seat
(
    id          int not null auto_increment,
    buildingNum varchar(16) not null,
    classRoomNum varchar(16) not null,
    seat_Id varchar(16) not null,
    if_Open boolean,
    open_Time varchar(16) not null,
    close_Time varchar(16) not null,
    if_Near_Window boolean,
    if_Near_Power boolean,
    primary key (id, classRoomNum)
);

drop table if exists tbl_booking_xxx;
create table tbl_booking_xxx
(
    id            int not null auto_increment,
    buildingNum   varchar(16) not null,
    classroomNum  varchar(16) not null,
    seat_Id        varchar(16) not null,
    begin_Time    varchar(16) not null,
    end_Time      varchar(16) not null,
    if_Online     boolean,
    stuId         varchar(16) not null,
    primary key (id)
);