package vn.edu.ptit.garaman.model;

/** Entity CustomerStatistics4 matching GaraMan report (suffix 4). */
public class CustomerStatistics4 extends Customer4 {
    private java.time.LocalDate startDate;
    private java.time.LocalDate endDate;
    private java.math.BigDecimal customerRevenue;
    public CustomerStatistics4() {}
    public java.time.LocalDate getStartDate() { return startDate; }
    public void setStartDate(java.time.LocalDate value) { this.startDate = value; }
    public java.time.LocalDate getEndDate() { return endDate; }
    public void setEndDate(java.time.LocalDate value) { this.endDate = value; }
    public java.math.BigDecimal getCustomerRevenue() { return customerRevenue; }
    public void setCustomerRevenue(java.math.BigDecimal value) { this.customerRevenue = value; }
    private int visitCount;
    private java.math.BigDecimal paidAmount = java.math.BigDecimal.ZERO;
    public int getVisitCount() { return visitCount; }
    public void setVisitCount(int value) { this.visitCount = value; }
    public java.math.BigDecimal getPaidAmount() { return paidAmount; }
    public void setPaidAmount(java.math.BigDecimal value) { this.paidAmount = value; }
    /** Derived: customerRevenue - paidAmount. */
    public java.math.BigDecimal getUnpaidAmount() {
        if (customerRevenue == null) return java.math.BigDecimal.ZERO;
        return customerRevenue.subtract(paidAmount == null ? java.math.BigDecimal.ZERO : paidAmount);
    }
}
