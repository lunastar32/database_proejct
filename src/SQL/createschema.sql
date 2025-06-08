CREATE TABLE customer (
    customer_id CHAR(4),
    customer_name VARCHAR(100),
    phone_number VARCHAR(20),
    PRIMARY KEY(customer_id)
);

CREATE TABLE orders (
    orders_id CHAR(4),
    customer_id CHAR(4),
    orders_date DATE,
    PRIMARY KEY(orders_id),
    FOREIGN KEY(customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE cake (
    cake_id CHAR(4),
    cake_name VARCHAR(100),
    price INT,
    PRIMARY KEY(cake_id)
);

CREATE TABLE inventory (
    cake_id CHAR(4),
    quantity_available INT,
    PRIMARY KEY(cake_id),
    FOREIGN KEY(cake_id) REFERENCES cake(cake_id)
);

CREATE TABLE orderitem (
    order_item_id CHAR(4),
    orders_id CHAR(4),
    cake_id CHAR(4),
    quantity INT,
    PRIMARY KEY(order_item_id),
    FOREIGN KEY(orders_id) REFERENCES orders(orders_id),
    FOREIGN KEY(cake_id) REFERENCES cake(cake_id)
);

CREATE VIEW customer_order_view AS
SELECT 
    o.orders_id,
    o.customer_id,
    o.orders_date,
    c.customer_name
FROM orders o
JOIN customer c ON o.customer_id = c.customer_id;

CREATE VIEW cake_view AS
SELECT 
    c.cake_id,
    c.cake_name,
    c.price,
    i.quantity_available AS quantity
FROM cake c
JOIN inventory i ON c.cake_id = i.cake_id;
