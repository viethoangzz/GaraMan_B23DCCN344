package vn.edu.ptit.garaman.web;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import vn.edu.ptit.garaman.model.Member4;

abstract class BaseServlet4 extends HttpServlet {
    protected void page(HttpServletRequest request,HttpServletResponse response,String jsp)throws ServletException,IOException {
        request.getRequestDispatcher("/WEB-INF/views/"+jsp+".jsp").forward(request,response);
    }
    protected boolean role(HttpServletRequest request,HttpServletResponse response,String...roles)throws IOException {
        Member4 m=(Member4)request.getSession().getAttribute("user");
        if(m==null){response.sendRedirect(request.getContextPath()+"/login");return false;}
        for(String role:roles)if(role.equals(m.getRole()))return true;
        response.sendError(HttpServletResponse.SC_FORBIDDEN,"Bạn không có quyền sử dụng chức năng này.");return false;
    }
    protected static int number(String input){return Integer.parseInt(input);}
    protected static Integer optionalInt(String input){return input==null||input.isBlank()?null:Integer.parseInt(input);}
    protected static String required(String value){
        if(value==null||value.trim().isEmpty())throw new IllegalArgumentException("Vui lòng nhập đầy đủ thông tin bắt buộc");
        return value.trim();
    }
    protected void error(HttpServletRequest req,HttpServletResponse res,String msg)throws ServletException,IOException{
        req.setAttribute("error",msg);page(req,res,"error");
    }
    protected String go(HttpServletRequest req,String path){return req.getContextPath()+path;}
}
