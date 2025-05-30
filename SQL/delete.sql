-- 주문 삭제
DELETE FROM orders WHERE order_id = ?;
DELETE FROM orderitem WHERE orders_id = ?
-- 고객 정보 삭제(탈퇴)
DELETE FROM customer WHERE customer_id = ?;
