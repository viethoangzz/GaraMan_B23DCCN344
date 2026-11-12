package vn.edu.ptit.garaman.web;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
@WebServlet("/app/dashboard")
public class DashboardServlet4 extends BaseServlet4 {
 @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
   page(req,resp,"dashboard");
 }
}
