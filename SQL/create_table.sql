USE cake1;

DROP TABLE IF EXISTS orderitem;
DROP TABLE IF EXISTS inventory;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS cake;

-- 고객 테이블
CREATE TABLE customer (
    customer_id char(4),
    customer_name varchar(100),
    phone_number varchar(20),
    primary key(customer_id)
);

-- 주문 테이블
CREATE TABLE orders (
    orders_id char(4),
    customer_id char(4),
    orders_date date,
    primary key(orders_id),
    foreign key(customer_id) references customer(customer_id)
); 

-- 케이크 테이블
CREATE TABLE cake (
	cake_id char(4),
    cake_name varchar(100),
    price int,
    primary key(cake_id)
);

-- 재고 테이블
CREATE TABLE inventory (
	cake_id char(4),
    quantity_available int,
    primary key(cake_id),
    foreign key(cake_id) references cake(cake_id)
);

-- 주문 목록 테이블
CREATE TABLE orderitem (
	order_item_id char(4),
    orders_id char(4),
    cake_id char(4),
    quantity int,
    primary key(order_item_id),
    foreign key(orders_id) references orders(orders_id),
    foreign key(cake_id) references cake(cake_id)
);
