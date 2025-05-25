-- 주문 추가
INSERT INTO orders (order_id, customer_id, order_date) VALUES (?, ?, CURRENT_DATE);

-- 고객 정보 추가
INSERT INTO customer(customer_id, customer_name, phone_number) VALUES (?, ?, ?);
