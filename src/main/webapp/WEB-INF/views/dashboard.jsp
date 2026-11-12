<%@ include file="_header.jspf" %>
<div class="eyebrow">GARAMAN</div><h1>Xin chào, <c:out value="${sessionScope.user.fullName}"/></h1>
<p class="lead">Chọn chức năng để tiếp tục.</p><div class="grid-cards">
<c:if test="${sessionScope.user.role == 'CUSTOMER'}">
<a class="card tile" href="${pageContext.request.contextPath}/app/search"><span class="tile-icon">⌕</span><h2>Tìm thông tin dịch vụ/phụ tùng</h2><p>Tìm theo tên và xem thông tin chi tiết dịch vụ, phụ tùng.</p><span class="cta">Tìm kiếm →</span></a>
</c:if>
<c:if test="${sessionScope.user.role == 'MANAGER'}">
<a class="card tile" href="${pageContext.request.contextPath}/app/reports"><span class="tile-icon">▥</span><h2>Xem báo cáo</h2><p>Thống kê khách hàng theo doanh thu, xem các lần sửa xe và hóa đơn chi tiết.</p><span class="cta">Xem báo cáo →</span></a>
</c:if></div>
<%@ include file="_footer.jspf" %>
