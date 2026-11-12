-- GaraMan: tables for search and customer revenue statistics.
CREATE DATABASE IF NOT EXISTS garaman CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE garaman;

CREATE TABLE IF NOT EXISTS tblMember4 (
 memberId INT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(60) NOT NULL UNIQUE,
 passwordHash VARCHAR(255) NOT NULL, fullName VARCHAR(150) NOT NULL,
 address VARCHAR(255), email VARCHAR(160), phone VARCHAR(24),
 role ENUM('CUSTOMER','MANAGER') NOT NULL
);
CREATE TABLE IF NOT EXISTS tblCustomer4 (memberId INT PRIMARY KEY, customerCode VARCHAR(25) NOT NULL UNIQUE,
 FOREIGN KEY (memberId) REFERENCES tblMember4(memberId));

CREATE TABLE IF NOT EXISTS tblService4 (serviceId INT PRIMARY KEY AUTO_INCREMENT,
 serviceCode VARCHAR(25) NOT NULL UNIQUE, name VARCHAR(200) NOT NULL, description TEXT,
 unitPrice DECIMAL(15,2) NOT NULL, CHECK (unitPrice >= 0));
CREATE TABLE IF NOT EXISTS tblSparePart4 (partId INT PRIMARY KEY AUTO_INCREMENT,
 partCode VARCHAR(25) NOT NULL UNIQUE, name VARCHAR(200) NOT NULL, description TEXT,
 unitPrice DECIMAL(15,2) NOT NULL, quantity INT NOT NULL DEFAULT 0,
 CHECK (unitPrice >= 0), CHECK (quantity >= 0));

CREATE TABLE IF NOT EXISTS tblServiceVisit4 (visitId INT PRIMARY KEY AUTO_INCREMENT,
 visitCode VARCHAR(25) NOT NULL UNIQUE, customerId INT NOT NULL,
 visitTime DATETIME NOT NULL,
 FOREIGN KEY (customerId) REFERENCES tblCustomer4(memberId), INDEX idx_visit_date(visitTime));

CREATE TABLE IF NOT EXISTS tblInvoice4 (invoiceId INT PRIMARY KEY AUTO_INCREMENT,
 invoiceCode VARCHAR(25) NOT NULL UNIQUE, visitId INT NOT NULL UNIQUE,
 totalAmount DECIMAL(15,2) NOT NULL DEFAULT 0, CHECK(totalAmount >= 0),
 FOREIGN KEY (visitId) REFERENCES tblServiceVisit4(visitId));
CREATE TABLE IF NOT EXISTS tblInvoiceServiceDetail4 (detailId INT PRIMARY KEY AUTO_INCREMENT,
 invoiceId INT NOT NULL, serviceId INT NOT NULL, quantity INT NOT NULL DEFAULT 1,
 unitPrice DECIMAL(15,2) NOT NULL, amount DECIMAL(15,2) NOT NULL,
 CHECK(quantity > 0), FOREIGN KEY (invoiceId) REFERENCES tblInvoice4(invoiceId),
 FOREIGN KEY (serviceId) REFERENCES tblService4(serviceId));
CREATE TABLE IF NOT EXISTS tblInvoicePartDetail4 (detailId INT PRIMARY KEY AUTO_INCREMENT,
 invoiceId INT NOT NULL, partId INT NOT NULL, quantity INT NOT NULL,
 unitPrice DECIMAL(15,2) NOT NULL, amount DECIMAL(15,2) NOT NULL,
 CHECK(quantity > 0), FOREIGN KEY (invoiceId) REFERENCES tblInvoice4(invoiceId),
 FOREIGN KEY (partId) REFERENCES tblSparePart4(partId));
CREATE TABLE IF NOT EXISTS tblPayment4 (paymentId INT PRIMARY KEY AUTO_INCREMENT,
 paymentCode VARCHAR(25) NOT NULL UNIQUE, invoiceId INT NOT NULL UNIQUE,
 amount DECIMAL(15,2) NOT NULL, paymentTime DATETIME NOT NULL,
 CHECK (amount >= 0), FOREIGN KEY (invoiceId) REFERENCES tblInvoice4(invoiceId));
-- Do not create statistics tables: these are derived query outputs, as specified in the report.
