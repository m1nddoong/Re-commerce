INSERT INTO role
VALUES
    (1, 'INACTIVE_USER'),
    (2, 'ACTIVE_USER'),
    (3, 'BUSINESS_USER'),
    (4, 'ADMIN');

-- SQLite 는 싱글 쿼터를 ('') 사용하며
-- 쿼리문 마지막에 콤마(;)를 기입해야지 정상적으로 작동한다.