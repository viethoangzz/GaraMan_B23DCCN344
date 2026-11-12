package vn.edu.ptit.garaman.dao;

import vn.edu.ptit.garaman.model.Service4;
import java.sql.*;
import java.util.*;

public class ServiceDAO4 extends DAO4 {
    public List<Service4> getServicesByKeyword(String keyword) throws SQLException {
        String sql = "SELECT serviceId,serviceCode,name,description,unitPrice FROM tblService4 WHERE LOWER(name) LIKE LOWER(?) ORDER BY name, serviceId";
        List<Service4> list = new ArrayList<>();
        try (Connection con=getConnection(); PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setString(1, likeContains(keyword));
            try (ResultSet rs=ps.executeQuery()) { while(rs.next()) list.add(read(rs)); }
        }
        return list;
    }
    public Service4 getServiceDetail(int serviceId) throws SQLException {
        try (Connection con=getConnection(); PreparedStatement ps=con.prepareStatement("SELECT * FROM tblService4 WHERE serviceId=?")) {
            ps.setInt(1,serviceId);
            try (ResultSet rs=ps.executeQuery()){return rs.next()?read(rs):null;}
        }
    }
    static Service4 read(ResultSet rs)throws SQLException {
        Service4 s=new Service4();s.setId(rs.getInt("serviceId"));s.setServiceCode(rs.getString("serviceCode"));
        s.setName(rs.getString("name"));s.setDescription(rs.getString("description"));s.setUnitPrice(rs.getBigDecimal("unitPrice"));return s;
    }
}
