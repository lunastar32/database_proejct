-- 주문 내용 수정
UPDATE orderitem
SET quantity = ?
WHERE order_item_id = ?;

-- 고객 정보 수정
UPDATE customer
SET phone_number = ?
WHERE customer_id = ?;