package vn.edu.ptit.garaman.web;
import vn.edu.ptit.garaman.dao.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

/** Pages align with dashboard -> SearchPage4 -> SearchResultsPage4 -> ServicePartDetailPage4. */
@WebServlet(urlPatterns={"/app/search","/app/detail"})
public class SearchServlet4 extends BaseServlet4 {
    private final ServiceDAO4 serviceDAO=new ServiceDAO4();
    private final SparePartDAO4 partDAO=new SparePartDAO4();
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        if(!role(req,resp,"CUSTOMER"))return;
        try {
            if("/app/detail".equals(req.getServletPath())) {
                int id=number(req.getParameter("id"));String type=req.getParameter("type");
                String back=req.getParameter("keyword");req.setAttribute("keyword",back==null?"":back);
                if("service".equals(type)){
                    req.setAttribute("service",serviceDAO.getServiceDetail(id));
                }else if("part".equals(type)){
                    req.setAttribute("part",partDAO.getSparePartDetail(id));
                }else throw new IllegalArgumentException("Loại kết quả không hợp lệ");
                if(req.getAttribute("service")==null&&req.getAttribute("part")==null){resp.sendError(404,"Không tìm thấy dịch vụ hoặc phụ tùng");return;}
                page(req,resp,"ServicePartDetailPage4");return;
            }
            String keyword=req.getParameter("keyword");
            if(keyword==null){page(req,resp,"SearchPage4");return;}
            req.setAttribute("keyword",keyword);
            if(keyword.isBlank()){
                req.setAttribute("error","Vui lòng nhập Keyword cần tìm");page(req,resp,"SearchPage4");return;
            }
            if(keyword.length()>100)throw new IllegalArgumentException("Từ khóa tối đa 100 ký tự");
            // Both DAOs are invoked as in Chapter 7, Scenario v3.0 steps 6-17.
            req.setAttribute("services",serviceDAO.getServicesByKeyword(keyword));
            req.setAttribute("parts",partDAO.getSparePartsByKeyword(keyword));
            page(req,resp,"SearchResultsPage4");
        }catch(SQLException ex){getServletContext().log("Search database failure",ex);error(req,resp,"Không thể tìm kiếm vì có lỗi kết nối CSDL.");}
         catch(IllegalArgumentException ex){error(req,resp,ex.getMessage());}
    }
}
