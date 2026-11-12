<%@ include file="_header.jspf" %>
<nav class="crumb"><a href="${pageContext.request.contextPath}/app/dashboard">Trang chủ</a> / Search</nav>
<div class="eyebrow">MODULE 1 · SEARCH</div><h1>Tìm kiếm dịch vụ / phụ tùng</h1>
<p class="lead">Nhập tên dịch vụ hoặc phụ tùng, hệ thống tìm tất cả bản ghi có tên chứa từ khóa.</p>
<section class="card"><form method="get" action="${pageContext.request.contextPath}/app/search" class="searchbar">
<label class="grow">Keyword<input autofocus name="keyword" required maxlength="100" placeholder="Ví dụ: phanh" value="${fn:escapeXml(keyword)}"/></label>
<button class="button primary" type="submit">Search</button></form></section>
<%@ include file="_footer.jspf" %>
