<%@ include file="_header.jspf" %>
<nav class="crumb"><a href="${pageContext.request.contextPath}/app/dashboard">Trang chủ</a> / <a href="${pageContext.request.contextPath}/app/reports">Report Selection</a> / Invoice Detail</nav>
<div class="eyebrow">CUSTOMER STATISTICS · INVOICE DETAIL</div><h1>Chi tiết hóa đơn</h1>
<c:choose><c:when test="${not empty invoice}">
<c:url var="backVisits" value="/app/reports/customer-visits"><c:param name="customerId" value="${invoice.customerId}"/><c:param name="startDate" value="${startDate}"/><c:param name="endDate" value="${endDate}"/><c:param name="paidOnly" value="${paidOnly ? '1' : ''}"/></c:url>
<section class="card"><h2>Thông tin chung</h2>
<table class="details"><tr><th>Mã Invoice</th><td><c:out value="${invoice.invoiceCode}"/></td></tr><tr><th>Khách hàng</th><td><c:out value="${invoice.customerName}"/></td></tr>
<tr><th>Mã Customer</th><td><c:out value="${invoice.customerCode}"/></td></tr>
<tr><th>Mã Service Visit</th><td><c:out value="${invoice.visitCode}"/></td></tr>
<tr><th>Ngày lập (theo Service Visit)</th><td><c:out value="${invoice.visitDateText}"/></td></tr>
<tr><th>Thanh toán</th><td><c:choose><c:when test="${invoice.paid}"><span class="pill green">Đã thanh toán</span> <c:out value="${invoice.paymentTimeText}"/></c:when><c:otherwise><span class="pill orange">Chưa thanh toán</span></c:otherwise></c:choose></td></tr>
<tr><th>Tổng tiền</th><td><strong><fmt:formatNumber value="${invoice.totalAmount}" maxFractionDigits="0"/> VNĐ</strong></td></tr></table>
<h2>Danh sách Service / Spare Part</h2><div class="table-scroll"><table><thead><tr><th>TT</th><th>Loại</th><th>Mã</th><th>Name</th><th class="number">Số lượng</th><th class="number">Đơn giá (VNĐ)</th><th class="number">Thành tiền (VNĐ)</th></tr></thead><tbody>
<c:set var="n" value="0"/>
<c:forEach var="d" items="${serviceDetails}"><c:set var="n" value="${n + 1}"/><tr><td><c:out value="${n}"/></td><td>Service</td><td><c:out value="${d.code}"/></td><td><c:out value="${d.name}"/></td><td class="number"><c:out value="${d.quantity}"/></td><td class="number"><fmt:formatNumber value="${d.unitPrice}" maxFractionDigits="0"/></td><td class="number"><fmt:formatNumber value="${d.amount}" maxFractionDigits="0"/></td></tr></c:forEach>
<c:forEach var="d" items="${partDetails}"><c:set var="n" value="${n + 1}"/><tr><td><c:out value="${n}"/></td><td>Spare Part</td><td><c:out value="${d.code}"/></td><td><c:out value="${d.name}"/></td><td class="number"><c:out value="${d.quantity}"/></td><td class="number"><fmt:formatNumber value="${d.unitPrice}" maxFractionDigits="0"/></td><td class="number"><fmt:formatNumber value="${d.amount}" maxFractionDigits="0"/></td></tr></c:forEach>
</tbody><tfoot><tr><td colspan="6">Tổng cộng</td><td class="number"><fmt:formatNumber value="${invoice.totalAmount}" maxFractionDigits="0"/></td></tr></tfoot></table></div>
<a class="button light" href="${fn:escapeXml(backVisits)}">← Quay lại Service Visits</a></section>
</c:when><c:otherwise><section class="card"><a class="button light" href="${pageContext.request.contextPath}/app/reports/customer-revenue">← Quay lại thống kê</a></section></c:otherwise></c:choose>
<%@ include file="_footer.jspf" %>
