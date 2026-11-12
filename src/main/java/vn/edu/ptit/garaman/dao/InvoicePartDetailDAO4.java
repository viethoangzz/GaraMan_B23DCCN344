package vn.edu.ptit.garaman.dao;
import java.sql.*;
import java.util.*;
import vn.edu.ptit.garaman.model.InvoicePartDetail4;
public class InvoicePartDetailDAO4 extends DAO4 {
    public List<InvoicePartDetail4> getPartDetailsByInvoice(int invoiceId)throws SQLException {
        List<InvoicePartDetail4> list=new ArrayList<>();
        String sql="SELECT d.detailId,d.invoiceId,d.partId,d.quantity,d.unitPrice,d.amount,s.partCode,s.name FROM tblInvoicePartDetail4 d JOIN tblSparePart4 s ON s.partId=d.partId WHERE d.invoiceId=? ORDER BY d.detailId";
        try(Connection c=getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setInt(1,invoiceId);try(ResultSet r=p.executeQuery()){
                while(r.next()){
                    InvoicePartDetail4 d=new InvoicePartDetail4();d.setId(r.getInt("detailId"));
                    d.setInvoiceId(r.getInt("invoiceId"));d.setPartId(r.getInt("partId"));
                    d.setCode(r.getString("partCode"));d.setName(r.getString("name"));d.setQuantity(r.getInt("quantity"));
                    d.setUnitPrice(r.getBigDecimal("unitPrice"));d.setAmount(r.getBigDecimal("amount"));list.add(d);
                }
            }
        }return list;
    }
}
