DROP
DATABASE IF EXISTS generate;
DROP
USER IF EXISTS 'code'@'%';
-- 支持emoji：需要mysql数据库参数： character_set_server=utf8mb4
CREATE
DATABASE generate DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE
generate;
CREATE
USER 'code'@'%' IDENTIFIED BY 'code123456';
GRANT ALL PRIVILEGES ON generate.* TO
'code'@'%';
FLUSH
PRIVILEGES;
