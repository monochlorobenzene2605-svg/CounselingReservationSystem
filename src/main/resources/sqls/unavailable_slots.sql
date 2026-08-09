CREATE TABLE IF NOT EXISTS unavailable_slots (
    id INT AUTO_INCREMENT PRIMARY KEY,
    counselor_id INT NOT NULL,
    date DATE NOT NULL,
    slot_template_id INT NOT NULL,
    FOREIGN KEY (counselor_id) REFERENCES users(id),
    FOREIGN KEY (slot_template_id) REFERENCES slot_templates(id),
    -- (counselor_id, date, slot_template_id) で複合unique
    CONSTRAINT uq_unavailable_slots UNIQUE (counselor_id, date, slot_template_id)
);

