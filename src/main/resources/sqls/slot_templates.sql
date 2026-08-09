CREATE TABLE IF NOT EXISTS slot_templates (
    id INT AUTO_INCREMENT PRIMARY KEY,
    period_no INT NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL
);


-- TODO: コマ定義するINSERT文をここに追加する 
