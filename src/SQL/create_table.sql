USE project;

DROP TABLE IF EXISTS orderitem;
DROP TABLE IF EXISTS inventory;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS cake;
DROP VIEW IF EXISTS customer_order_view;
DROP VIEW IF ExISTS cake_view;

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

-- 메뉴2번 . 주문 내역 조회
-- view 작성
CREATE VIEW customer_order_view AS
SELECT 
    o.orders_id,
    o.customer_id,
    o.orders_date,
    c.customer_name
FROM orders o
JOIN customer c ON o.customer_id = c.customer_id;

-- 케이크 메뉴 및 내용 조회
CREATE VIEW cake_view AS
SELECT 
    c.cake_id,
    c.cake_name,
    c.price,
    i.quantity_available AS quantity
FROM cake c
JOIN inventory i ON c.cake_id = i.cake_id;
