<%@ include file="_header.jspf" %>
<div class="authwrap"><section class="card auth"><div class="eyebrow">HỆ THỐNG QUẢN LÝ GARA Ô TÔ</div><h1>Đăng nhập GaraMan</h1>
<p>Đăng nhập để sử dụng chức năng theo vai trò.</p>
<form method="post" action="${pageContext.request.contextPath}/login"><input type="hidden" name="csrf" value="${sessionScope.csrf}">
<label>Tài khoản<input type="text" name="username" required maxlength="60" autocomplete="username" placeholder="customer / manager"></label>
<label>Mật khẩu<input type="password" name="password" required autocomplete="current-password" placeholder="Mật khẩu của bạn"></label>
<button class="button primary full" type="submit">Đăng nhập</button></form>
<p class="hint">Demo: customer, manager · Mật khẩu: <code>Demo@12345</code></p>
</section></div>
<%@ include file="_footer.jspf" %>
