-- 주문 내용 수정
UPDATE orderitem 
SET cake_id = ?, quantity = ? 
WHERE orders_id = ? AND cake_id = ?

-- 고객 정보 수정
UPDATE customer
SET phone_number = ?
WHERE customer_id = ?;
