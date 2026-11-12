<%@ include file="_header.jspf" %>
<nav class="crumb"><a href="${pageContext.request.contextPath}/app/dashboard">Trang chủ</a> / <a href="${pageContext.request.contextPath}/app/reports">Report Selection</a> / Customer Revenue Statistics</nav>
<div class="eyebrow">CUSTOMER STATISTICS · DATE RANGE</div><h1>Thống kê khách hàng theo doanh thu</h1>
<p class="lead">Chọn ngày bắt đầu và ngày kết thúc (tính cả hai ngày). Doanh thu của khách hàng là tổng giá trị hóa đơn của các lần đến trong khoảng đó.</p>
<section class="card"><form class="grid-2" method="get" action="${pageContext.request.contextPath}/app/reports/customer-revenue">
<label>Ngày bắt đầu<input type="date" name="startDate" required value="${fn:escapeXml(param.startDate)}"/></label>
<label>Ngày kết thúc<input type="date" name="endDate" required value="${fn:escapeXml(param.endDate)}"/></label>
<label class="check"><input type="checkbox" name="paidOnly" value="1" ${param.paidOnly == '1' ? 'checked' : ''}/> Chỉ tính hóa đơn đã thanh toán</label>
<div><button class="button primary" type="submit">Xem thống kê khách hàng</button> <a class="button light" href="${pageContext.request.contextPath}/app/reports">Quay lại</a></div>
</form>
<p class="hint">Mặc định tính mọi hóa đơn (theo quy ước của báo cáo) và hiển thị riêng phần đã/chưa thanh toán. Dữ liệu demo nằm trong 01/08/2026 – 31/08/2026.</p></section>
<%@ include file="_footer.jspf" %>
