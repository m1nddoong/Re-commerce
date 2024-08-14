-- 중고 거래 물품 등록
-- INSERT INTO trade_item (id, title, description, image, price, item_status, user_id)
-- VALUES (1, '중고 TV', '고성능 TV', NULL, 500000, 'ON_SALE', 1),
--        (2, '중고 컴퓨터', '고성능 컴퓨터', NULL, 50000, 'ON_SALE', 2),
--        (3, '중고 노트북', '고성능 노트북', NULL, 1200000, 'ON_SALE', 3);

-- -- 중고 거래 물품 구매 제안
-- INSERT INTO trade_offer (id, item_id, offering_user_id, offer_status)
-- VALUES (1, 1, 2, 'Wait'),
--        (2, 1, 3, 'Wait'),
--        (3, 1, 4, 'Wait');

-- 쇼핑몰
INSERT INTO shop (id, user_id, name, introduction, category, status, address, coordinates)
VALUES (1, 2, '유재석의 가게', '유재석의 전문 매장.', 'FASHION', 'OPEN', '서울시 강남구', '37.5665, 126.978'),
       (2, 3, '박명수의 매장', '박명수의 전문 매장', 'ELECTRONICS', 'OPEN', '서울시 송파구', '37.564, 126.989'),
       (3, 4, '정준하의 매장', '정준하의 전문 매장', 'BEAUTY', 'OPEN', '서울시 송파구', '37.564, 126.989'),
       (4, 5, '정형돈의 매장', '정형돈의 전문 매장', 'HOME', 'OPEN_REQUEST', '서울시 송파구', '37.564, 126.989'),
       (5, 6, '길의 매장', '길의 전문 매장', 'SPORT', 'OPEN_REQUEST', '서울시 송파구', '37.564, 126.989'),
       (6, 7, '노홍철의 매장', '노홍철의 전문 매장', 'FASHION', 'OPEN_REQUEST', '서울시 송파구', '37.564, 126.989'),
       (7, 8, '하하의 매장', '하하의 전문 매장', 'ELECTROINCS', 'OPEN_REQUEST', '서울시 송파구', '37.564, 126.989');
