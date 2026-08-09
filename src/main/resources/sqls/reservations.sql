CREATE TABLE IF NOT EXISTS reservations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    counselor_id INT NOT NULL,
    date DATE NOT NULL,
    slot_template_id INT NOT NULL,
    summary VARCHAR(255) NOT NULL,
    details TEXT,
    FOREIGN KEY (student_id) REFERENCES users(id),
    FOREIGN KEY (counselor_id) REFERENCES users(id),
    FOREIGN KEY (slot_template_id) REFERENCES slot_templates(id),
    -- 相談員の重複予約防止 (counselor_id, date, slot_template_id)
    CONSTRAINT uq_reservations_counselor UNIQUE (counselor_id, date, slot_template_id),
    -- 受講者の重複予約防止 (student_id, date, slot_template_id)
    CONSTRAINT uq_reservations_student UNIQUE (student_id, date, slot_template_id)
);
