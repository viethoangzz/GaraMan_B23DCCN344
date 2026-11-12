package vn.edu.ptit.garaman.model;

/** Entity ServiceVisit4 matching GaraMan report (suffix 4). */
public class ServiceVisit4 {
    private int id;
    private String visitCode;
    private int customerId;
    private java.time.LocalDateTime visitTime;
    private Integer invoiceId;
    private String invoiceCode;
    private java.math.BigDecimal invoiceAmount;
    public ServiceVisit4() {}
    public int getId() { return id; }
    public void setId(int value) { this.id = value; }
    public String getVisitCode() { return visitCode; }
    public void setVisitCode(String value) { this.visitCode = value; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int value) { this.customerId = value; }
    public java.time.LocalDateTime getVisitTime() { return visitTime; }
    public void setVisitTime(java.time.LocalDateTime value) { this.visitTime = value; }
    public Integer getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Integer value) { this.invoiceId = value; }
    public String getInvoiceCode() { return invoiceCode; }
    public void setInvoiceCode(String value) { this.invoiceCode = value; }
    public java.math.BigDecimal getInvoiceAmount() { return invoiceAmount; }
    public void setInvoiceAmount(java.math.BigDecimal value) { this.invoiceAmount = value; }
    private boolean paid;
    public boolean isPaid() { return paid; }
    public void setPaid(boolean value) { this.paid = value; }
    /** dd/MM/yyyy, used by the Service Visits screen. */
    public String getVisitDateText() {
        return visitTime == null ? "" : visitTime.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
