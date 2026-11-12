package vn.edu.ptit.garaman.dao;
import java.sql.*;
import java.util.*;
import vn.edu.ptit.garaman.model.InvoiceServiceDetail4;
public class InvoiceServiceDetailDAO4 extends DAO4 {
    public List<InvoiceServiceDetail4> getServiceDetailsByInvoice(int invoiceId)throws SQLException {
        List<InvoiceServiceDetail4> list=new ArrayList<>();
        String sql="SELECT d.detailId,d.invoiceId,d.serviceId,d.quantity,d.unitPrice,d.amount,s.serviceCode,s.name FROM tblInvoiceServiceDetail4 d JOIN tblService4 s ON s.serviceId=d.serviceId WHERE d.invoiceId=? ORDER BY d.detailId";
        try(Connection c=getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setInt(1,invoiceId);try(ResultSet r=p.executeQuery()){
                while(r.next()){
                    InvoiceServiceDetail4 d=new InvoiceServiceDetail4();d.setId(r.getInt("detailId"));
                    d.setInvoiceId(r.getInt("invoiceId"));d.setServiceId(r.getInt("serviceId"));
                    d.setCode(r.getString("serviceCode"));d.setName(r.getString("name"));d.setQuantity(r.getInt("quantity"));
                    d.setUnitPrice(r.getBigDecimal("unitPrice"));d.setAmount(r.getBigDecimal("amount"));list.add(d);
                }
            }
        }return list;
    }
}
