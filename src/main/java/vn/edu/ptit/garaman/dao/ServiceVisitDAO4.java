package vn.edu.ptit.garaman.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import vn.edu.ptit.garaman.model.ServiceVisit4;

public class ServiceVisitDAO4 extends DAO4 {
    public List<ServiceVisit4> getServiceVisits(int customerId, LocalDate startDate, LocalDate endDate) throws SQLException {
        return getServiceVisits(customerId, startDate, endDate, false);
    }

    public List<ServiceVisit4> getServiceVisits(int customerId, LocalDate startDate, LocalDate endDate, boolean paidOnly) throws SQLException {
        String sql = "SELECT v.visitId, v.visitCode, v.customerId, v.visitTime, i.invoiceId, i.invoiceCode, i.totalAmount, p.paymentId " +
            "FROM tblServiceVisit4 v LEFT JOIN tblInvoice4 i ON i.visitId=v.visitId " +
            "LEFT JOIN tblPayment4 p ON p.invoiceId=i.invoiceId " +
            "WHERE v.customerId=? AND v.visitTime >= ? AND v.visitTime < ? " + (paidOnly ? "AND p.paymentId IS NOT NULL " : "") +
            "ORDER BY v.visitTime, v.visitId";
        List<ServiceVisit4> list = new ArrayList<>();
        try (Connection c = getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, customerId);
            setDateRange(p, 2, startDate, endDate);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    ServiceVisit4 v = new ServiceVisit4();
                    v.setId(r.getInt("visitId"));
                    v.setVisitCode(r.getString("visitCode"));
                    v.setCustomerId(r.getInt("customerId"));
                    v.setVisitTime(r.getTimestamp("visitTime").toLocalDateTime());
                    int invoiceId = r.getInt("invoiceId");
                    v.setInvoiceId(r.wasNull() ? null : Integer.valueOf(invoiceId));
                    v.setInvoiceCode(r.getString("invoiceCode"));
                    v.setInvoiceAmount(r.getBigDecimal("totalAmount"));
                    r.getInt("paymentId");
                    v.setPaid(!r.wasNull());
                    list.add(v);
                }
            }
        }
        return list;
    }
}
