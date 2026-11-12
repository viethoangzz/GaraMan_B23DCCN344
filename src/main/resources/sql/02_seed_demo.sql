USE garaman;
-- Demonstration data. DO NOT use these accounts/passwords in production.
-- All demo accounts use Demo@12345 (PBKDF2-HMAC-SHA256).
-- Run once on fresh database after 01_schema.sql.
INSERT INTO tblMember4(memberId,username,passwordHash,fullName,email,phone,role) VALUES (1,'customer','pbkdf2$120000$iB1DVYTmcVJrLD0T3QJV/Q==$S6MXxWnkdCrzGBCjEpRdsR0tOeUin9PGiIpjYh0SK0U=','Nguyễn Văn An','customer@demo.local','0900000001','CUSTOMER');
INSERT INTO tblCustomer4(memberId,customerCode) VALUES (1,'KH001');
INSERT INTO tblMember4(memberId,username,passwordHash,fullName,email,phone,role) VALUES (2,'manager','pbkdf2$120000$kqw7rL0iBlJxVIxlCTzylQ==$soxoLcyIuMoZFmcdwYs0lPESIByrJvBAs9a4lroA6z4=','Nguyễn Thu Hà','manager@demo.local','0900000002','MANAGER');

INSERT INTO tblMember4(memberId,username,passwordHash,fullName,email,phone,role) VALUES (5,'customer2','pbkdf2$120000$i+v9RituqbDSMN8rnl7t4w==$PEu8FJ9mZB//oFQiMpaE+v2NnFJQDoWE+JMI1+JOtO4=','Trần Thị Bình','customer2@demo.local','0900000005','CUSTOMER');
INSERT INTO tblCustomer4(memberId,customerCode) VALUES (5,'KH002');
INSERT INTO tblMember4(memberId,username,passwordHash,fullName,email,phone,role) VALUES (6,'customer3','pbkdf2$120000$q1HOLfeAXMReQyGwvrtsqg==$BwBmBxg0QCMgHPnYF/6YOTRMZIPaRuB1X+dhiZJsPq8=','Lê Minh Cường','customer3@demo.local','0900000006','CUSTOMER');
INSERT INTO tblCustomer4(memberId,customerCode) VALUES (6,'KH003');
INSERT INTO tblService4(serviceId,serviceCode,name,description,unitPrice) VALUES
 (2,'DV002','Bảo dưỡng định kỳ','Kiểm tra và bảo dưỡng xe',1500000),
 (5,'DV005','Thay má phanh','Kiểm tra hệ thống phanh và thay má phanh phù hợp với tình trạng xe',1000000),
 (6,'DV006','Thay dầu động cơ','Thay dầu và lọc dầu',750000);
INSERT INTO tblSparePart4(partId,partCode,name,description,unitPrice,quantity) VALUES
 (12,'PT012','Má phanh trước','Má phanh trước chính hãng',1250000,20),
 (13,'PT013','Má phanh sau','Má phanh sau chính hãng',1200000,18),
 (14,'PT014','Lọc dầu','Lọc dầu xe ô tô',350000,30);

INSERT INTO tblServiceVisit4(visitId,visitCode,customerId,visitTime) VALUES
 (1,'LV001',1,'2026-08-05 10:00:00'),(7,'LV007',1,'2026-08-21 09:00:00'),
 (8,'LV008',5,'2026-08-14 09:00:00'),(9,'LV009',6,'2026-08-26 09:00:00');
INSERT INTO tblInvoice4(invoiceId,invoiceCode,visitId,totalAmount) VALUES
 (1,'HD001',1,3500000),(7,'HD007',7,5000000),(8,'HD008',8,5200000),(9,'HD009',9,2700000);
INSERT INTO tblInvoiceServiceDetail4(invoiceId,serviceId,quantity,unitPrice,amount) VALUES
 (7,2,1,1500000,1500000),(7,5,1,1000000,1000000),
 (1,2,1,1500000,1500000),(8,5,1,1000000,1000000),(9,6,1,750000,750000);
INSERT INTO tblInvoicePartDetail4(invoiceId,partId,quantity,unitPrice,amount) VALUES
 (7,12,2,1250000,2500000),(1,12,1,1250000,1250000),(1,14,2,375000,750000),
 (8,13,3,1200000,3600000),(8,14,1,600000,600000),
 (9,13,1,1200000,1200000),(9,14,3,250000,750000);
INSERT INTO tblPayment4(paymentCode,invoiceId,amount,paymentTime) VALUES('TT001',1,3500000,'2026-08-05 11:00:00'),('TT007',7,5000000,'2026-08-21 11:00:00');

-- Extra data for the statistics module -------------------------------------------------
-- A visit without invoice: appears in KH002's Service Visits list as "Chưa có Invoice", does not change any revenue.
INSERT INTO tblServiceVisit4(visitId,visitCode,customerId,visitTime) VALUES (10,'LV010',5,'2026-08-30 15:00:00');

