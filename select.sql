-- 1. 케이크 재고 현황(메뉴와 재고를 함께 출력)
SELECT 
    c.cake_name,
    i.quantity_available AS quantity_avilable
FROM cake c
JOIN inventory i ON c.cake_id = i.cake_id;




-- 2. 해당 주문 목록(orders_id를 입력받아 주문 수량, 가격 출력)
SELECT 
    c.cake_name,
    SUM(oi.quantity) AS total_quantity,
    SUM(oi.quantity * c.price) AS total_price
FROM orderitem oi
JOIN cake c ON oi.cake_id = c.cake_id
WHERE oi.orders_id = ?
GROUP BY c.cake_name;




-- 3. 주문 내역 확인(customer_id를 입력받아 고객의 모든 과거 주문 내역 출력을 orders_id와 date로 출력)
-- view 작성
CREATE VIEW customer_order_view AS
SELECT 
    o.orders_id,
    o.customer_id,
    o.orders_date,
    c.customer_name
FROM orders o
JOIN customer c ON o.customer_id = c.customer_id;

-- customer_id를 입력 받아 order_id와 date 출력
SELECT 
    v.orders_id,
    v.orders_date
FROM customer_order_view v
WHERE v.customer_id = ?;




-- 4. 고객 개인 정보 확인
SELECT 
    customer_id,
    customer_name,
    phone_number
FROM customer
WHERE customer_id = ?;





-- 5. 많이 팔린 케이크 순위
SELECT 
    c.cake_name,
    SUM(oi.quantity) AS total_sold
FROM orderitem oi
JOIN cake c ON oi.cake_id = c.cake_id
GROUP BY c.cake_id, c.cake_name
ORDER BY total_sold DESC;
