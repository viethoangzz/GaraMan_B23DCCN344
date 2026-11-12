<%@ include file="_header.jspf" %>
<nav class="crumb"><a href="${pageContext.request.contextPath}/app/dashboard">Trang chủ</a> / <a href="${pageContext.request.contextPath}/app/search">Search</a> / Service / Part Detail</nav>
<div class="eyebrow">MODULE 1 · VIEW DETAIL</div><h1>Thông tin dịch vụ / phụ tùng</h1>
<section class="card"><table class="details"><tbody>
<c:choose><c:when test="${not empty service}">
<tr><th>Loại</th><td>Service</td></tr><tr><th>Mã Service</th><td><c:out value="${service.serviceCode}"/></td></tr>
<tr><th>Name</th><td><c:out value="${service.name}"/></td></tr><tr><th>Mô tả</th><td><c:out value="${service.description}"/></td></tr>
<tr><th>Đơn giá</th><td><fmt:formatNumber value="${service.unitPrice}" maxFractionDigits="0"/> VNĐ</td></tr>
</c:when><c:otherwise>
<tr><th>Loại</th><td>Spare Part</td></tr><tr><th>Mã Spare Part</th><td><c:out value="${part.partCode}"/></td></tr>
<tr><th>Name</th><td><c:out value="${part.name}"/></td></tr><tr><th>Mô tả</th><td><c:out value="${part.description}"/></td></tr>
<tr><th>Đơn giá</th><td><fmt:formatNumber value="${part.unitPrice}" maxFractionDigits="0"/> VNĐ</td></tr>
<tr><th>Tồn kho</th><td><c:out value="${part.quantity}"/></td></tr>
</c:otherwise></c:choose></tbody></table>
<c:choose><c:when test="${not empty keyword}"><c:url var="backResults" value="/app/search"><c:param name="keyword" value="${keyword}"/></c:url><a href="${fn:escapeXml(backResults)}" class="button light">← Quay lại Search Results</a></c:when><c:otherwise><a href="${pageContext.request.contextPath}/app/search" class="button light">← Quay lại Search</a></c:otherwise></c:choose></section>
<%@ include file="_footer.jspf" %>
