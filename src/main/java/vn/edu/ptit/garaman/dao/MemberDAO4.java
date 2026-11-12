package vn.edu.ptit.garaman.dao;
import vn.edu.ptit.garaman.model.Member4;
import vn.edu.ptit.garaman.security.Password4;
import java.sql.*;
public class MemberDAO4 extends DAO4 {
    public Member4 login(String username,String password)throws SQLException {
        if(username==null||username.isBlank()||password==null)return null;
        String sql="SELECT memberId,username,fullName,email,phone,role,passwordHash FROM tblMember4 WHERE username=?";
        try(Connection c=getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,username.trim());try(ResultSet r=p.executeQuery()){
                if(!r.next()||!Password4.verify(password,r.getString("passwordHash")))return null;
                Member4 m=new Member4();m.setId(r.getInt("memberId"));m.setUsername(r.getString("username"));
                m.setFullName(r.getString("fullName"));m.setEmail(r.getString("email"));
                m.setPhone(r.getString("phone"));m.setRole(r.getString("role"));return m;
            }
        }
    }
    public Member4 getCustomer(int id)throws SQLException{
        String sql="SELECT m.memberId,m.username,m.fullName,m.email,m.phone,m.role FROM tblMember4 m JOIN tblCustomer4 c ON c.memberId=m.memberId WHERE m.memberId=?";
        try(Connection c=getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setInt(1,id);try(ResultSet r=p.executeQuery()){
                if(!r.next())return null;Member4 m=new Member4();m.setId(r.getInt("memberId"));m.setUsername(r.getString("username"));
                m.setFullName(r.getString("fullName"));m.setEmail(r.getString("email"));m.setPhone(r.getString("phone"));m.setRole(r.getString("role"));return m;
            }
        }
    }
}
