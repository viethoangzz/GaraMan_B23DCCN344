# GaraMan — hai chức năng chính

Java 17 · JSP/Servlet 4 · JDBC · MySQL · Tomcat 9 · Maven.

## Phạm vi

1. Khách hàng: chọn **Tìm thông tin dịch vụ/phụ tùng** → nhập tên → xem danh sách có tên chứa từ khóa → chọn dịch vụ/phụ tùng → xem chi tiết.
2. Nhân viên (vai trò MANAGER): chọn **Xem báo cáo** → **Thống kê khách hàng theo doanh thu** → chọn ngày bắt đầu/kết thúc → chọn khách hàng → xem các lần sửa xe → chọn lần sửa xe → xem hóa đơn.

Đăng nhập/đăng xuất và phân quyền hỗ trợ hai luồng trên.


| Tài khoản | Mật khẩu demo | Chức năng |
|---|---|---|
| customer | Demo@12345 | Tìm kiếm dịch vụ/phụ tùng |
| manager | Demo@12345 | Thống kê khách hàng theo doanh thu |

## Cài đặt trên máy khác

Cài JDK 17+, Maven và Tomcat 9; dùng MySQL 8. Chạy `01_schema.sql` rồi `02_seed_demo.sql` trong `src/main/resources/sql` trên database mới. Schema mới có 9 bảng phục vụ hai luồng; dữ liệu thanh toán chỉ dùng để hiển thị trạng thái hóa đơn. Không chạy lại seed trên database đã có dữ liệu.

Đặt `DB_USER`, `DB_PASSWORD` cho tiến trình Tomcat. `DB_URL` mặc định là `jdbc:mysql://localhost:3306/garaman?useUnicode=true&characterEncoding=UTF-8&connectionTimeZone=SERVER`.
Chạy `mvn clean package`, chép `target/GaraMan.war` vào `webapps` của Tomcat 9 rồi khởi động Tomcat. Dùng cổng HTTP được cấu hình trên máy đó.

Database đang sử dụng không bị xóa bảng/dữ liệu cũ khi cập nhật ứng dụng. Các chức năng cũ đã được loại khỏi servlet, giao diện và mã DAO/model; các tài khoản vai trò SALES/WAREHOUSE không đăng nhập được vào phiên bản này.

## Kiểm tra dữ liệu mẫu

- Tìm `phanh`: DV005, PT012, PT013.
- Thống kê 01/08/2026–31/08/2026: KH001 = 8.500.000, KH002 = 5.200.000, KH003 = 2.700.000 VNĐ.
- KH001 → LV007 → HD007: tổng 5.000.000 VNĐ, gồm ba dòng chi tiết.
- Khoảng ngày bao gồm cả ngày kết thúc; mặc định cộng mọi hóa đơn. Có tùy chọn chỉ tính hóa đơn đã thanh toán.

`mvn test` chạy kiểm thử mật khẩu và từ khóa LIKE. Kịch bản chức năng nằm trong `docs/TEST_CASES.md`.
Khi server local đang chạy, chạy `powershell -ExecutionPolicy Bypass -File scripts/verify-scope.ps1` từ thư mục dự án để kiểm tra hai luồng, phân quyền và các đường dẫn đã xóa. Script dùng tài khoản/dữ liệu demo và chỉ đọc dữ liệu.
