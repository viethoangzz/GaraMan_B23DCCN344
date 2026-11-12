package vn.edu.ptit.garaman.web;

import vn.edu.ptit.garaman.dao.*;
import vn.edu.ptit.garaman.model.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/** Customer revenue statistics and invoice drill-down. */
@WebServlet(urlPatterns = {
    "/app/reports", "/app/reports/customer-revenue", "/app/reports/customer-visits",
    "/app/reports/invoice"})
public class StatisticsServlet4 extends BaseServlet4 {
    private static final DateTimeFormatter SHOW = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String MSG_MISSING = "Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc";
    private static final String MSG_ORDER = "Ngày bắt đầu không được lớn hơn ngày kết thúc";
    private static final String MSG_FORMAT = "Định dạng ngày không hợp lệ";

    private final CustomerStatisticsDAO4 customerStatisticsDAO = new CustomerStatisticsDAO4();
    private final ServiceVisitDAO4 visitsDAO = new ServiceVisitDAO4();
    private final InvoiceDAO4 invoiceDAO = new InvoiceDAO4();
    private final InvoiceServiceDetailDAO4 serviceDetailDAO = new InvoiceServiceDetailDAO4();
    private final InvoicePartDetailDAO4 partDetailDAO = new InvoicePartDetailDAO4();
    private final MemberDAO4 memberDAO = new MemberDAO4();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!role(req, resp, "MANAGER")) return;
        try {
            switch (req.getServletPath()) {
                case "/app/reports": page(req, resp, "ReportSelectionPage4"); return;
                case "/app/reports/customer-revenue": customerRevenue(req, resp); return;
                case "/app/reports/customer-visits": serviceVisits(req, resp); return;
                case "/app/reports/invoice": invoiceDetail(req, resp); return;
                default: resp.sendError(404);
            }
        } catch (SQLException e) {
            getServletContext().log("Report error", e);
            error(req, resp, "Lỗi truy vấn CSDL khi xem báo cáo.");
        } catch (IllegalArgumentException e) {
            error(req, resp, e.getMessage());
        }
    }

    // ---------- shared helpers ----------

    private static void dates(HttpServletRequest req, LocalDate start, LocalDate end) {
        req.setAttribute("startDate", start);
        req.setAttribute("endDate", end);
        req.setAttribute("startText", start.format(SHOW));
        req.setAttribute("endText", end.format(SHOW));
    }

    private static LocalDate parseDate(String value) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(MSG_MISSING);
        try {
            return LocalDate.parse(value.trim());
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException(MSG_FORMAT);
        }
    }

    private static int intParam(HttpServletRequest req, String name) {
        try {
            return Integer.parseInt(req.getParameter(name).trim());
        } catch (RuntimeException ex) { // null (NPE) or malformed number
            throw new IllegalArgumentException("Tham số " + name + " không hợp lệ");
        }
    }

    /**
     * Reads startDate/endDate from the request. When nothing was submitted, or the input is invalid, it renders
     * {@code formPage} (with an error message when needed) and returns null; otherwise returns {start, end}.
     */
    private LocalDate[] readRange(HttpServletRequest req, HttpServletResponse resp, String formPage, String formAction)
            throws ServletException, IOException {
        req.setAttribute("formAction", formAction);
        String a = req.getParameter("startDate"), b = req.getParameter("endDate");
        if (a == null && b == null) { page(req, resp, formPage); return null; }
        LocalDate start, end;
        try {
            start = parseDate(a);
            end = parseDate(b);
        } catch (IllegalArgumentException ex) {
            req.setAttribute("error", ex.getMessage());
            page(req, resp, formPage);
            return null;
        }
        if (start.isAfter(end)) {
            req.setAttribute("error", MSG_ORDER);
            page(req, resp, formPage);
            return null;
        }
        dates(req, start, end);
        return new LocalDate[]{start, end};
    }

    // ---------- Customer Revenue Statistics (Chapter 8) ----------

    private void customerRevenue(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        LocalDate[] range = readRange(req, resp, "CustomerRevenueStatisticsPage4", "/app/reports/customer-revenue");
        if (range == null) return;
        boolean paidOnly = "1".equals(req.getParameter("paidOnly"));
        List<CustomerStatistics4> result = customerStatisticsDAO.getCustomerRevenueStatistics(range[0], range[1], paidOnly);
        BigDecimal revenue = BigDecimal.ZERO, paid = BigDecimal.ZERO;
        int visits = 0;
        for (CustomerStatistics4 s : result) {
            revenue = revenue.add(s.getCustomerRevenue());
            paid = paid.add(s.getPaidAmount());
            visits += s.getVisitCount();
        }
        req.setAttribute("statistics", result);
        req.setAttribute("paidOnly", paidOnly);
        req.setAttribute("totalRevenue", revenue);
        req.setAttribute("totalPaid", paid);
        req.setAttribute("totalUnpaid", revenue.subtract(paid));
        req.setAttribute("totalVisits", visits);
        page(req, resp, "CustomerStatisticsPage4");
    }

    private void serviceVisits(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        int customerId = intParam(req, "customerId");
        LocalDate start = parseDate(req.getParameter("startDate")), end = parseDate(req.getParameter("endDate"));
        if (start.isAfter(end)) throw new IllegalArgumentException(MSG_ORDER);
        boolean paidOnly = "1".equals(req.getParameter("paidOnly"));
        Member4 customer = memberDAO.getCustomer(customerId);
        if (customer == null) { resp.sendError(404, "Không tồn tại khách hàng"); return; }
        List<ServiceVisit4> visits = visitsDAO.getServiceVisits(customerId, start, end, paidOnly);
        // Customer Revenue shown here is the sum of the listed invoices, so it always equals the row in the statistics table.
        BigDecimal revenue = BigDecimal.ZERO, paid = BigDecimal.ZERO;
        for (ServiceVisit4 v : visits) {
            if (v.getInvoiceAmount() == null) continue;
            revenue = revenue.add(v.getInvoiceAmount());
            if (v.isPaid()) paid = paid.add(v.getInvoiceAmount());
        }
        dates(req, start, end);
        req.setAttribute("customer", customer);
        req.setAttribute("visits", visits);
        req.setAttribute("paidOnly", paidOnly);
        req.setAttribute("revenue", revenue);
        req.setAttribute("paidAmount", paid);
        req.setAttribute("unpaidAmount", revenue.subtract(paid));
        page(req, resp, "CustomerServiceVisitsPage4");
    }

    private void invoiceDetail(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        int visitId = intParam(req, "visitId");
        Invoice4 invoice = invoiceDAO.getInvoiceByVisit(visitId);
        if (invoice == null) {
            req.setAttribute("error", "Service Visit chưa có Invoice tương ứng");
            page(req, resp, "InvoiceDetailPage4");
            return;
        }
        // Keep the Date Range of the drill-down so that "Quay lại" returns to this customer's Service Visits.
        LocalDate start, end;
        try {
            start = parseDate(req.getParameter("startDate"));
            end = parseDate(req.getParameter("endDate"));
            if (start.isAfter(end)) throw new IllegalArgumentException(MSG_ORDER);
        } catch (IllegalArgumentException ex) {
            start = end = invoice.getVisitTime().toLocalDate();
        }
        dates(req, start, end);
        req.setAttribute("paidOnly", "1".equals(req.getParameter("paidOnly")));
        req.setAttribute("invoice", invoice);
        req.setAttribute("serviceDetails", serviceDetailDAO.getServiceDetailsByInvoice(invoice.getId()));
        req.setAttribute("partDetails", partDetailDAO.getPartDetailsByInvoice(invoice.getId()));
        page(req, resp, "InvoiceDetailPage4");
    }

}
