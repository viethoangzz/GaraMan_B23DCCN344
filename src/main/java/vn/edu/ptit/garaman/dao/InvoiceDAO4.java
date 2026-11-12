package vn.edu.ptit.garaman.dao;

import java.sql.*;
import vn.edu.ptit.garaman.model.Invoice4;

public class InvoiceDAO4 extends DAO4 {
    public Invoice4 getInvoiceByVisit(int visitId) throws SQLException {
        String sql = "SELECT i.invoiceId, i.invoiceCode, i.visitId, i.totalAmount, m.fullName, v.customerId, v.visitTime, v.visitCode, " +
            "c.customerCode, p.paymentId, p.paymentTime " +
            "FROM tblInvoice4 i JOIN tblServiceVisit4 v ON v.visitId=i.visitId " +
            "JOIN tblMember4 m ON v.customerId=m.memberId JOIN tblCustomer4 c ON c.memberId=v.customerId " +
            "LEFT JOIN tblPayment4 p ON p.invoiceId=i.invoiceId WHERE i.visitId=?";
        try (Connection c = getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, visitId);
            try (ResultSet r = p.executeQuery()) {
                if (!r.next()) return null;
                Invoice4 i = new Invoice4();
                i.setId(r.getInt("invoiceId"));
                i.setInvoiceCode(r.getString("invoiceCode"));
                i.setVisitId(r.getInt("visitId"));
                i.setTotalAmount(r.getBigDecimal("totalAmount"));
                i.setCustomerName(r.getString("fullName"));
                i.setCustomerId(r.getInt("customerId"));
                i.setCustomerCode(r.getString("customerCode"));
                i.setVisitCode(r.getString("visitCode"));
                i.setVisitTime(r.getTimestamp("visitTime").toLocalDateTime());
                Timestamp paid = r.getTimestamp("paymentTime");
                if (paid != null) {
                    i.setPaid(true);
                    i.setPaymentTime(paid.toLocalDateTime());
                }
                return i;
            }
        }
    }
}
