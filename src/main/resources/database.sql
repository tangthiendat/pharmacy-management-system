create database pharmacy;
use pharmacy;

create table admin
(
    id       int auto_increment
        primary key,
    username varchar(100) null,
    password varchar(100) null
);

create table medicine
(
    medicineID  int auto_increment
        primary key,
    brand       varchar(100) null,
    productName varchar(100) null,
    type        varchar(100) null,
    price       double       null,
    status      varchar(100) null
);

create table `order`
(
    orderID     int auto_increment
        primary key,
    orderNumber varchar(12) null,
    createdDate timestamp   null,
    totalValue  float       null,
    constraint order_pk
        unique (orderNumber)
);

create table order_details
(
    orderID    int not null,
    medicineID int not null,
    quantity   int null,
    primary key (orderID, medicineID),
    constraint FK_medicineID
        foreign key (medicineID) references medicine (medicineID),
    constraint FK_orderID
        foreign key (orderID) references `order` (orderID)
);