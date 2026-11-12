<%@ include file="_header.jspf" %>
<nav class="crumb"><a href="${pageContext.request.contextPath}/app/dashboard">Trang chủ</a> / <a href="${pageContext.request.contextPath}/app/reports">Report Selection</a> / <a href="${pageContext.request.contextPath}/app/reports">Customer Statistics</a> / <a href="${pageContext.request.contextPath}/app/reports/customer-revenue">Date Range</a> / Customer Revenue</nav>
<div class="eyebrow">CUSTOMER STATISTICS · REVENUE</div><h1>Thống kê khách hàng theo doanh thu</h1>
<p class="lead">Từ <strong><c:out value="${startText}"/></strong> đến <strong><c:out value="${endText}"/></strong> · Sắp xếp doanh thu giảm dần</p>
<c:if test="${paidOnly}"><p class="note">Đang chỉ tính các hóa đơn đã có thanh toán.</p></c:if>
<section class="card"><c:choose><c:when test="${empty statistics}"><div class="empty">Không có dữ liệu trong khoảng thời gian đã chọn.</div></c:when><c:otherwise>
<div class="table-scroll"><table><thead><tr><th>TT</th><th>Mã Customer</th><th>Họ tên Customer</th><th class="number">Số lần đến</th><th class="number">Customer Revenue (VNĐ)</th><th class="number">Đã thanh toán</th><th class="number">Chưa thanh toán</th><th>Thao tác</th></tr></thead><tbody>
<c:forEach var="s" items="${statistics}" varStatus="st"><tr><td><c:out value="${st.count}"/></td><td><c:out value="${s.customerCode}"/></td><td><c:out value="${s.fullName}"/></td>
<td class="number"><c:out value="${s.visitCount}"/></td>
<td class="number"><fmt:formatNumber value="${s.customerRevenue}" maxFractionDigits="0"/></td>
<td class="number"><fmt:formatNumber value="${s.paidAmount}" maxFractionDigits="0"/></td>
<td class="number"><fmt:formatNumber value="${s.unpaidAmount}" maxFractionDigits="0"/></td>
<td><c:url var="detailUrl" value="/app/reports/customer-visits"><c:param name="customerId" value="${s.id}"/><c:param name="startDate" value="${startDate}"/><c:param name="endDate" value="${endDate}"/><c:param name="paidOnly" value="${paidOnly ? '1' : ''}"/></c:url><a href="${fn:escapeXml(detailUrl)}">View Detail</a></td></tr></c:forEach>
</tbody><tfoot><tr><td colspan="3">Tổng cộng</td><td class="number"><c:out value="${totalVisits}"/></td><td class="number"><fmt:formatNumber value="${totalRevenue}" maxFractionDigits="0"/></td><td class="number"><fmt:formatNumber value="${totalPaid}" maxFractionDigits="0"/></td><td class="number"><fmt:formatNumber value="${totalUnpaid}" maxFractionDigits="0"/></td><td></td></tr></tfoot></table></div></c:otherwise></c:choose>
<a class="button light" href="${pageContext.request.contextPath}/app/reports/customer-revenue">← Chọn lại Date Range</a></section>
<%@ include file="_footer.jspf" %>
