-- =============================================================================
-- 1. PEMBERSIHAN & PEMBUATAN DATABASE
-- =============================================================================
DROP DATABASE IF EXISTS db_ebanking;
CREATE DATABASE db_ebanking;
USE db_ebanking;

-- =============================================================================
-- 2. DDL PEMBUATAN TABEL
-- =============================================================================

-- Tabel Authentication
CREATE TABLE m_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    cif_number VARCHAR(20) UNIQUE NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    failed_attempts INT DEFAULT 0,
    last_failed_login TIMESTAMP NULL,
    suspend_until TIMESTAMP NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE m_password_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (username) REFERENCES m_user(username)
) ENGINE=InnoDB;

CREATE TABLE h_login_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cif_number VARCHAR(20) NOT NULL,
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ip_address VARCHAR(50),
    device_id VARCHAR(100),
    user_agent TEXT,
    status ENUM('SUCCESS', 'FAILED', 'LOCKED') DEFAULT 'SUCCESS'
) ENGINE=InnoDB;

-- Tabel Core Banking
CREATE TABLE m_product_type (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE m_customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cif_number VARCHAR(20) UNIQUE NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    customer_phone VARCHAR(20),
    customer_email VARCHAR(100),
    classification INT DEFAULT 1,
    client_pin VARCHAR(255) NOT NULL,
    need_authorized_unblock BOOLEAN DEFAULT FALSE,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE m_account (
    account_number VARCHAR(20) PRIMARY KEY,
    cif_number VARCHAR(20) NOT NULL,
    product_type_id INT NOT NULL,
    balance DECIMAL(15, 2) DEFAULT 0.00,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_account_customer FOREIGN KEY (cif_number) REFERENCES m_customer(cif_number),
    CONSTRAINT fk_account_product FOREIGN KEY (product_type_id) REFERENCES m_product_type(id)
) ENGINE=InnoDB;

CREATE TABLE m_limit (
    id INT AUTO_INCREMENT PRIMARY KEY,
    feature_code VARCHAR(10) NOT NULL,
    classification INT NOT NULL,
    limit_amount DECIMAL(15, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE m_feature (
    feature_code VARCHAR(10) PRIMARY KEY,
    feature_name VARCHAR(100) NOT NULL,
    fee DECIMAL(15, 2) DEFAULT 0.00
) ENGINE=InnoDB;

CREATE TABLE m_response_code (
    response_code VARCHAR(5) PRIMARY KEY,
    response_message VARCHAR(150) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE t_transaction (
    id_transaction BIGINT AUTO_INCREMENT PRIMARY KEY,
    reference_number VARCHAR(50) UNIQUE NOT NULL,
    cif_number VARCHAR(20) NOT NULL,
    from_account_number VARCHAR(20) NOT NULL,
    customer_reference VARCHAR(50) NOT NULL,
    transaction_amount DECIMAL(15, 2) NOT NULL,
    fee DECIMAL(15, 2) NOT NULL,
    transaction_status VARCHAR(20) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    feature_code VARCHAR(10),
    response_code VARCHAR(5),
    ipaddress VARCHAR(50),
    biller_name VARCHAR(100),
    location VARCHAR(100),
    
    CONSTRAINT fk_trx_customer FOREIGN KEY (cif_number) REFERENCES m_customer(cif_number),
    CONSTRAINT fk_trx_account FOREIGN KEY (from_account_number) REFERENCES m_account(account_number),
    CONSTRAINT fk_trx_feature FOREIGN KEY (feature_code) REFERENCES m_feature(feature_code),
    CONSTRAINT fk_trx_response FOREIGN KEY (response_code) REFERENCES m_response_code(response_code)
) ENGINE=InnoDB;

CREATE TABLE h_audit_trail (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_name VARCHAR(50),
    action_type ENUM('INSERT', 'UPDATE', 'DELETE'),
    record_id VARCHAR(50),
    old_value TEXT,
    new_value TEXT,
    action_by VARCHAR(50),
    action_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- =============================================================================
-- 3. VIEWS
-- =============================================================================

CREATE OR REPLACE VIEW lihat_transaksi AS
SELECT 
    t.cif_number,
    mc.customer_name,
    t.transaction_amount,
    mf.feature_name,
    mf.fee 
FROM t_transaction t
JOIN m_customer mc ON mc.cif_number = t.cif_number
JOIN m_feature mf ON mf.feature_code = t.feature_code
WHERE t.transaction_date >= DATE_SUB(NOW(), INTERVAL 7 DAY);

-- =============================================================================
-- 4. DML DATA DUMMY
-- =============================================================================

INSERT INTO m_product_type (product_name) VALUES ('TABUNGAN'), ('GIRO'), ('DEPOSITO');

INSERT INTO m_feature (feature_code, feature_name, fee) VALUES
('101', 'Transfer Sesama Bank', 0.00),
('102', 'Transfer Antar Bank', 6500.00),
('201', 'Pembayaran PLN', 3000.00);

INSERT INTO m_response_code (response_code, response_message) VALUES
('00', 'Success'),
('51', 'Insufficient Fund'),
('61', 'Exceeds Daily Limit'),
('68', 'External Timeout'),
('99', 'System Error');

INSERT INTO m_limit (feature_code, classification, limit_amount) VALUES
('101', 1, 10000000.00),
('101', 2, 50000000.00),
('102', 1, 5000000.00);

INSERT INTO m_customer (cif_number, customer_name, customer_phone, customer_email, classification, client_pin) VALUES
('CIF001', 'BUDI HARTONO', '081234567890', 'budi@gmail.com', 1, '$2a$12$hashedpin1'),
('CIF002', 'SITI AMINAH', '081122334455', 'siti@gmail.com', 2, '$2a$12$hashedpin2');

INSERT INTO m_account (account_number, cif_number, product_type_id, balance) VALUES
('1001001', 'CIF001', 1, 5000000.00),
('1001002', 'CIF001', 1, 1000000.00),
('2001001', 'CIF002', 2, 75000000.00);

INSERT INTO m_user (username, password, cif_number, status) VALUES
('budi_hartono', '$2a$12$eImiTxAk4vmMZdG84IXtneX', 'CIF001', 'ACTIVE'),
('siti_aminah', '$2a$12$L8b0VbXOnW1vN9oI2K2OueE', 'CIF002', 'ACTIVE');

-- =============================================================================
-- 5. STORED PROCEDURES
-- =============================================================================

DELIMITER //

CREATE PROCEDURE sp_login_user(
    IN p_username VARCHAR(50),
    IN p_password VARCHAR(255),
    OUT r_response_code VARCHAR(5)
)
BEGIN
    DECLARE v_status VARCHAR(20);
    DECLARE v_db_password VARCHAR(255);
    DECLARE v_cif_number VARCHAR(20);

    SELECT status, password, cif_number 
    INTO v_status, v_db_password, v_cif_number
    FROM m_user WHERE username = p_username;

    IF v_status IS NULL THEN
        SET r_response_code = '01';
    ELSEIF v_status = 'LOCKED' THEN
        SET r_response_code = '02';
    ELSE
        IF v_db_password = p_password THEN
            INSERT INTO h_login_log (cif_number, status) VALUES (v_cif_number, 'SUCCESS');
            SET r_response_code = '00';
            -- Return Menu List
            SELECT menu_title, route_path FROM m_menu WHERE is_active = 1;
        ELSE
            INSERT INTO h_login_log (cif_number, status) VALUES (v_cif_number, 'FAILED');
            SET r_response_code = '03';
        END IF;
    END IF;
END //

CREATE PROCEDURE mutasi(IN nomor_cif VARCHAR(20))
BEGIN
    SELECT 
        mc.cif_number, mc.customer_name, t.reference_number, t.transaction_amount,
        t.biller_name, mf.fee, mr.response_message, t.transaction_date, t.location 
    FROM t_transaction t
    JOIN m_customer mc ON mc.cif_number = t.cif_number
    JOIN m_feature mf ON mf.feature_code = t.feature_code
    JOIN m_response_code mr ON mr.response_code = t.response_code
    WHERE mc.cif_number = nomor_cif 
    AND DATE(t.transaction_date) = CURDATE()
    ORDER BY t.transaction_date DESC;
END //

CREATE PROCEDURE tutup_buku()
BEGIN
    SELECT 
        mc.cif_number, mc.customer_name, COUNT(*) AS 'Total', 
        SUM(transaction_amount) AS 'Jumlah Transaksi' 
    FROM t_transaction t
    JOIN m_customer mc ON mc.cif_number = t.cif_number
    WHERE DATE(t.transaction_date) = CURDATE()
    GROUP BY mc.cif_number, mc.customer_name;
END //

DELIMITER ;

-- =============================================================================
-- 6. TRIGGERS
-- =============================================================================

DELIMITER //

CREATE TRIGGER trg_login_success AFTER INSERT ON h_login_log
FOR EACH ROW 
BEGIN
    IF NEW.status = 'SUCCESS' THEN
        UPDATE m_user SET failed_attempts = 0, updated = NOW() WHERE cif_number = NEW.cif_number;
    END IF;
END //

CREATE TRIGGER trg_login_failed AFTER INSERT ON h_login_log
FOR EACH ROW 
BEGIN
    IF NEW.status = 'FAILED' THEN
        UPDATE m_user
        SET 
            failed_attempts = failed_attempts + 1,
            last_failed_login = NOW(),
            status = CASE WHEN failed_attempts + 1 >= 3 THEN 'LOCKED' ELSE status END,
            updated = NOW()
        WHERE cif_number = NEW.cif_number;
    END IF;
END //

DELIMITER ;
THEN 'LOCKED' ELSE status END,
            updated = NOW()
        WHERE cif_number = NEW.cif_number;
    END IF;
END //

DELIMITER ;
