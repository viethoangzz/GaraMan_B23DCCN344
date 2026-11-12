package vn.edu.ptit.garaman.dao;

import vn.edu.ptit.garaman.model.CustomerStatistics4;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;


public class CustomerStatisticsDAO4 extends DAO4 {
    public List<CustomerStatistics4> getCustomerRevenueStatistics(LocalDate startDate, LocalDate endDate) throws SQLException {
        return getCustomerRevenueStatistics(startDate, endDate, false);
    }

    public List<CustomerStatistics4> getCustomerRevenueStatistics(LocalDate startDate, LocalDate endDate, boolean paidOnly) throws SQLException {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) throw new IllegalArgumentException("Khoảng ngày không hợp lệ");
        String sql = "SELECT m.memberId, c.customerCode, m.fullName, COUNT(v.visitId) AS visitCount, " +
            "SUM(i.totalAmount) AS customerRevenue, " +
            "SUM(CASE WHEN p.paymentId IS NOT NULL THEN i.totalAmount ELSE 0 END) AS paidAmount " +
            "FROM tblCustomer4 c JOIN tblMember4 m ON m.memberId=c.memberId " +
            "JOIN tblServiceVisit4 v ON v.customerId=c.memberId " +
            "JOIN tblInvoice4 i ON i.visitId=v.visitId " +
            "LEFT JOIN tblPayment4 p ON p.invoiceId=i.invoiceId " +
            "WHERE v.visitTime >= ? AND v.visitTime < ? " + (paidOnly ? "AND p.paymentId IS NOT NULL " : "") +
            "GROUP BY m.memberId, c.customerCode, m.fullName " +
            "ORDER BY customerRevenue DESC, m.memberId";
        List<CustomerStatistics4> list = new ArrayList<>();
        try (Connection c = getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            setDateRange(p, 1, startDate, endDate);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    CustomerStatistics4 s = new CustomerStatistics4();
                    s.setId(r.getInt("memberId"));
                    s.setCustomerCode(r.getString("customerCode"));
                    s.setFullName(r.getString("fullName"));
                    s.setStartDate(startDate);
                    s.setEndDate(endDate);
                    s.setVisitCount(r.getInt("visitCount"));
                    s.setCustomerRevenue(r.getBigDecimal("customerRevenue"));
                    s.setPaidAmount(r.getBigDecimal("paidAmount"));
                    list.add(s);
                }
            }
        }
        return list;
    }
}
