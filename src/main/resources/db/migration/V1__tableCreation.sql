create table customer
(
    id                  integer primary key auto_increment,
    name                varchar(200)    not null,
    document            varchar(200)    not null,
    active              boolean         not null default true
);

create table product
(
    id                  integer primary key auto_increment,
    name                varchar(200)    not null,
    price               decimal(10, 2)  not null
);

create table purchase_order
(
    id                  integer primary key auto_increment,
    delivery_address    varchar(200)    not null,
    status              varchar(20)     not null,
    customer_id         integer             not null,

    foreign key (customer_id) references customer(id)
);

create table order_item
(
    id                  integer primary key auto_increment,
    amount              integer not null default 1,
    product_id          integer not null,
    order_id            integer not null,

    foreign key (product_id) references product(id),
    foreign key (order_id) references purchase_order(id)
);