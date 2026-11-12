package vn.edu.ptit.garaman.web;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;
import vn.edu.ptit.garaman.security.Csrf4;

@WebFilter(urlPatterns="/app/*")
public class AuthFilter4 implements Filter {
    @Override public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain)throws IOException,ServletException {
        HttpServletRequest req=(HttpServletRequest)request;HttpServletResponse res=(HttpServletResponse)response;
        res.setHeader("Cache-Control","no-store");res.setHeader("X-Content-Type-Options","nosniff");
        if(req.getSession(false)==null||req.getSession(false).getAttribute("user")==null){
            res.sendRedirect(req.getContextPath()+"/login");return;
        }
        if("POST".equalsIgnoreCase(req.getMethod())&&!Csrf4.valid(req)){
            res.sendError(HttpServletResponse.SC_FORBIDDEN,"CSRF token không hợp lệ");return;
        }
        chain.doFilter(request,response);
    }
}
