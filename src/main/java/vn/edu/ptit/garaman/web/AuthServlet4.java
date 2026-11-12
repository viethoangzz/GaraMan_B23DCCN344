package vn.edu.ptit.garaman.web;
import vn.edu.ptit.garaman.dao.MemberDAO4;
import vn.edu.ptit.garaman.model.Member4;
import vn.edu.ptit.garaman.security.Csrf4;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns={"/login","/logout"})
public class AuthServlet4 extends BaseServlet4 {
    private final MemberDAO4 dao=new MemberDAO4();
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        String path=req.getServletPath();
        if("/logout".equals(path)){req.getSession().invalidate();resp.sendRedirect(go(req,"/login"));return;}
        if(req.getSession().getAttribute("user")!=null){resp.sendRedirect(go(req,"/app/dashboard"));return;}
        Csrf4.token(req.getSession());page(req,resp,"login");
    }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        if(!Csrf4.valid(req)){resp.sendError(403,"CSRF token không hợp lệ");return;}
        try {
            Member4 user=dao.login(req.getParameter("username"),req.getParameter("password"));
            if(user==null || !("CUSTOMER".equals(user.getRole()) || "MANAGER".equals(user.getRole()))){req.setAttribute("error","Tên tài khoản hoặc mật khẩu không đúng");page(req,resp,"login");return;}
            req.getSession().invalidate();HttpSession session=req.getSession(true);session.setAttribute("user",user);
            Csrf4.token(session);resp.sendRedirect(go(req,"/app/dashboard"));
        }catch(SQLException ex){getServletContext().log("DB error",ex);req.setAttribute("error","Không thể truy cập CSDL. Kiểm tra cấu hình MySQL và bảng dữ liệu.");page(req,resp,"login");}
         catch(IllegalArgumentException ex){req.setAttribute("error",ex.getMessage());page(req,resp,"login");}
    }
}
