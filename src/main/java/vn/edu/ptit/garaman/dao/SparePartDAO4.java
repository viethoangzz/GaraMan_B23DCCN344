package vn.edu.ptit.garaman.dao;

import vn.edu.ptit.garaman.model.SparePart4;
import java.sql.*;
import java.util.*;

public class SparePartDAO4 extends DAO4 {
    public List<SparePart4> getSparePartsByKeyword(String keyword) throws SQLException {
        List<SparePart4> list = new ArrayList<>();
        try(Connection con=getConnection();PreparedStatement ps=con.prepareStatement("SELECT * FROM tblSparePart4 WHERE LOWER(name) LIKE LOWER(?) ORDER BY name,partId")){
            ps.setString(1, likeContains(keyword));
            try(ResultSet rs=ps.executeQuery()){while(rs.next())list.add(read(rs));}
        }
        return list;
    }
    public SparePart4 getSparePartDetail(int partId) throws SQLException {
        try(Connection c=getConnection();PreparedStatement p=c.prepareStatement("SELECT * FROM tblSparePart4 WHERE partId=?")){
            p.setInt(1,partId);try(ResultSet r=p.executeQuery()){return r.next()?read(r):null;}
        }
    }
    static SparePart4 read(ResultSet rs)throws SQLException {
        SparePart4 v=new SparePart4();v.setId(rs.getInt("partId"));v.setPartCode(rs.getString("partCode"));
        v.setName(rs.getString("name"));v.setDescription(rs.getString("description"));
        v.setUnitPrice(rs.getBigDecimal("unitPrice"));v.setQuantity(rs.getInt("quantity"));return v;
    }
}
