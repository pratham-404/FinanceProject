CREATE DATABASE finance_app;

USE finance_app;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    dob DATE NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_no VARCHAR(15) NOT NULL UNIQUE,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    address TEXT,
    card_type VARCHAR(10) NOT NULL CHECK (card_type IN ('gold', 'platinum')),
    bank_name VARCHAR(100) NOT NULL,
    account_no VARCHAR(30) NOT NULL UNIQUE,
    ifsc_code VARCHAR(15) NOT NULL,
    total_credit DECIMAL(15, 2) DEFAULT 0.00,
    used_credit DECIMAL(15, 2) DEFAULT 0.00, 
    is_active BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    card_validity TIMESTAMP DEFAULT (DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 3 YEAR))
);

DELIMITER $$
CREATE TRIGGER set_credit_before_insert
BEFORE INSERT ON users
FOR EACH ROW
BEGIN
    IF NEW.card_type = 'gold' THEN
        SET NEW.total_credit = 100000.00;  
        SET NEW.used_credit = 0.00; 
    ELSEIF NEW.card_type = 'platinum' THEN
        SET NEW.total_credit = 300000.00;  
        SET NEW.used_credit = 0.00; 
    END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER update_total_credit_before_update
BEFORE UPDATE ON users
FOR EACH ROW
BEGIN
    IF OLD.card_type <> NEW.card_type THEN
        IF NEW.card_type = 'gold' THEN
            SET NEW.total_credit = 100000.00;
        ELSEIF NEW.card_type = 'platinum' THEN
            SET NEW.total_credit = 300000.00;
        ELSE
            SET NEW.total_credit = 0.00;
        END IF;
    END IF;
END$$
DELIMITER ;

CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    product_details TEXT NOT NULL,
    cost DECIMAL(10, 2) NOT NULL
);

CREATE TABLE purchases (
    purchase_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    product_id INT,
    emi_period VARCHAR(10) NOT NULL CHECK (emi_period IN ('3 months', '6 months', '9 months', '12 months')),
    purchase_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,  
    amount_paid DECIMAL(10, 2) DEFAULT 0.00,  
    installment_count INT DEFAULT 0,  
    installment_amount DECIMAL(10, 2) DEFAULT 3,  
    payment_status ENUM('completed', 'pending', 'failed') DEFAULT 'pending',
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

CREATE TABLE installments (
    installment_id INT AUTO_INCREMENT PRIMARY KEY,
    purchase_id INT,
    installment_due_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    amount DECIMAL(10, 2) NOT NULL,
    payment_status ENUM('paid', 'pending') DEFAULT 'pending',
    FOREIGN KEY (purchase_id) REFERENCES purchases(purchase_id)
);

DELIMITER $$
CREATE TRIGGER insert_installments_after_purchase
AFTER INSERT ON purchases
FOR EACH ROW
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE installment_amount DECIMAL(10, 2);
    DECLARE due_date TIMESTAMP;
    SET installment_amount = NEW.total_amount / NEW.installment_count;
    SET due_date = CURRENT_TIMESTAMP;
    WHILE i < NEW.installment_count DO
        INSERT INTO installments (purchase_id, installment_due_date, amount, payment_status)
        VALUES (NEW.purchase_id, due_date, installment_amount, 'pending');
        SET i = i + 1;
        SET due_date = DATE_ADD(due_date, INTERVAL 1 MONTH);
    END WHILE;
END$$
DELIMITER ;

CREATE TABLE admin (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (name, dob, email, phone_no, username, password, address, card_type, bank_name, account_no, ifsc_code, is_active)
VALUES 
('Amit Sharma', '1990-05-14', 'amit.sharma@example.com', '9876543210', 'amit90', 'password123', '123 MG Road, Mumbai, Maharashtra', 'gold', 'HDFC Bank', '123456789012', 'HDFC0001234', TRUE),
('Priya Gupta', '1995-09-23', 'priya.gupta@example.com', '8765432109', 'priya95', 'password456', '456 MG Road, Bangalore, Karnataka', 'platinum', 'SBI', '234567890123', 'SBIN0001234', TRUE),
('Rahul Verma', '1988-12-11', 'rahul.verma@example.com', '7654321098', 'rahul88', 'password789', '789 MG Road, Delhi', 'gold', 'ICICI Bank', '345678901234', 'ICIC0001234', FALSE),
('Sita Rani', '1992-03-04', 'sita.rani@example.com', '6543210987', 'sita92', 'password101', '321 MG Road, Kolkata, West Bengal', 'platinum', 'Axis Bank', '456789012345', 'UTIB0001234', TRUE),
('Vikram Singh', '1985-07-30', 'vikram.singh@example.com', '5432109876', 'vikram85', 'password202', '987 MG Road, Chennai, Tamil Nadu', 'gold', 'Punjab National Bank', '567890123456', 'PUNB0001234', TRUE);

INSERT INTO products (product_name, product_details, cost)
VALUES 
('Smartphone', 'Latest Android smartphone with 128GB storage.', 30000.00),
('Laptop', '15-inch laptop with Intel i5 processor and 8GB RAM.', 60000.00),
('Smartwatch', 'Water-resistant smartwatch with fitness tracking.', 10000.00),
('Wireless Headphones', 'Noise-cancelling wireless headphones.', 5000.00),
('Tablet', '10-inch tablet with WiFi connectivity and 64GB storage.', 20000.00);

INSERT INTO purchases (user_id, product_id, emi_period, total_amount, installment_count)
VALUES 
(1, 1, '12 months', 30000.00, 12),
(2, 2, '6 months', 60000.00, 6),
(3, 3, '3 months', 10000.00, 3),
(4, 4, '9 months', 5000.00, 9),
(5, 5, '12 months', 20000.00, 12);

INSERT INTO admin (username, password) VALUES ('admin', 'adminpass123');
