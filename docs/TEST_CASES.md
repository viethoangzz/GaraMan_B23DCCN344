# Test cases đối chiếu với kịch bản report GaraMan (Đề 30)

| ID | Vai trò | Thao tác | Kết quả mong đợi |
|---|---|---|---|
| AUTH-01 | Guest | Login `customer` + `Demo@12345` | Chuyển đến CustomerMainPage4 |
| AUTH-02 | Guest | Sai mật khẩu | Hiển thị thông báo chung, không đăng nhập |
| AUTH-03 | Customer | Truy cập `/app/reports` | HTTP 403 |
| S-01 | Customer | Search keyword `phanh` | DV005, PT012, PT013 |
| S-02 | Customer | Search keyword rỗng | Thông báo nhập Keyword |
| S-03 | Customer | Search keyword không tồn tại | Danh sách rỗng và thông báo không tìm thấy |
| S-04 | Customer | Click DV005 | Chi tiết "Thay má phanh", 1.000.000 VNĐ |
| S-05 | Customer | Click PT012 | Chi tiết "Má phanh trước", 1.250.000 VNĐ |
| R-01 | Manager | Date Range trống 1 đầu | Thông báo chọn đủ ngày |
| R-02 | Manager | Start Date > End Date | Báo lỗi và nhập lại |
| R-03 | Manager | 2026-08-01 → 2026-08-31 | KH001 8.500.000; KH002 5.200.000; KH003 2.700.000 VNĐ, giảm dần |
| R-04 | Manager | Click KH001 | LV001 (3.500.000), LV007 (5.000.000) |
| R-05 | Manager | Click LV007 | HD007 có 3 dòng; tổng 5.000.000 |
| R-06 | Manager | Range không có Invoice | Thông báo không có dữ liệu |
| R-07 | Manager | Click KH002 (01/08–31/08) | Có LV008 (HD008) và LV010 hiển thị "Chưa có Invoice", không có link View Invoice cho LV010 |
| SEC-01 | User | POST thiếu CSRF token | HTTP 403 |
| R-08 | Manager | `/app/reports/invoice?visitId=10` (visit chưa có hóa đơn, gõ URL) | Thông báo "Service Visit chưa có Invoice tương ứng" |
| R-09 | Manager | Customer Revenue 01/08–31/08, tích "Chỉ tính hóa đơn đã thanh toán" | Chỉ còn KH001 = 8.500.000 |
| R-10 | Manager | Customer Revenue 01/08–31/08 (không tích) | Tổng 16.400.000; đã thanh toán 8.500.000; chưa thanh toán 7.900.000; số lần đến 4 |
| R-11 | Manager | Customer Revenue 21/08–21/08 | KH001 = 5.000.000 (lần đến 09:00 ngày kết thúc vẫn được tính) |
| R-12 | Manager | Từ LV007 bấm "Quay lại Service Visits" | Về danh sách visit của KH001, giữ Date Range |
| R-13 | Manager | `/app/reports/customer-visits?customerId=1&startDate=abc&endDate=2026-08-31` | Thông báo "Định dạng ngày không hợp lệ", không HTTP 500 |
| S-06 | Customer | Search `phanh`, click DV005, bấm Quay lại | Về Search Results của `phanh` |
| S-07 | Customer | Search `%` | Không trả toàn bộ dữ liệu (chỉ khớp ký tự % theo nghĩa đen → rỗng) |
| SCOPE-01 | Manager | Xem b?o c?o | Ch? c? th?ng k? kh?ch h?ng theo doanh thu, m? tr?c ti?p form ch?n ng?y |
| SCOPE-02 | Manager | Truy c?p ???ng d?n ch?c n?ng ?? b? | HTTP 404 |
| AUTH-04 | Manager | Truy c?p `/app/search` | HTTP 403 |
