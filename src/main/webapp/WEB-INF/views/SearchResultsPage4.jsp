<%@ include file="_header.jspf" %>
<nav class="crumb"><a href="${pageContext.request.contextPath}/app/dashboard">Trang chủ</a> / <a href="${pageContext.request.contextPath}/app/search">Search</a> / Search Results</nav>
<div class="eyebrow">MODULE 1 · SEARCH RESULTS</div><h1>Kết quả tìm kiếm</h1>
<p class="lead">Keyword: <strong><c:out value="${keyword}"/></strong></p>
<section class="card"><h2>Danh sách Service / Spare Part</h2>
<c:choose><c:when test="${empty services and empty parts}"><div class="empty">Không tìm thấy kết quả phù hợp.</div></c:when>
<c:otherwise><div class="table-scroll"><table><thead><tr><th>Loại</th><th>Mã</th><th>Name</th><th>Đơn giá (VNĐ)</th><th>Thao tác</th></tr></thead><tbody>
<c:forEach var="s" items="${services}"><tr><td><span class="pill">Service</span></td><td><c:out value="${s.serviceCode}"/></td><td><c:out value="${s.name}"/></td><td><fmt:formatNumber value="${s.unitPrice}" maxFractionDigits="0"/></td><td><c:url var="u" value="/app/detail"><c:param name="type" value="service"/><c:param name="id" value="${s.id}"/><c:param name="keyword" value="${keyword}"/></c:url><a href="${fn:escapeXml(u)}">View Detail</a></td></tr></c:forEach>
<c:forEach var="p" items="${parts}"><tr><td><span class="pill purple">Spare Part</span></td><td><c:out value="${p.partCode}"/></td><td><c:out value="${p.name}"/></td><td><fmt:formatNumber value="${p.unitPrice}" maxFractionDigits="0"/></td><td><c:url var="u" value="/app/detail"><c:param name="type" value="part"/><c:param name="id" value="${p.id}"/><c:param name="keyword" value="${keyword}"/></c:url><a href="${fn:escapeXml(u)}">View Detail</a></td></tr></c:forEach>
</tbody></table></div></c:otherwise></c:choose><a class="button light" href="${pageContext.request.contextPath}/app/search">Tìm từ khóa khác</a></section>
<%@ include file="_footer.jspf" %>
