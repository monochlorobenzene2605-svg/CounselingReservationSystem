CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL, -- bcryptでハッシュ化して登録
    role VARCHAR(50) NOT NULL
);

-- TODO: 受講者/相談員の初期データを登録するINSERT文をここに追加する
