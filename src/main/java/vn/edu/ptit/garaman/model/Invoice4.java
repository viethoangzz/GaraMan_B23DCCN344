package vn.edu.ptit.garaman.model;

public class Invoice4 {
    private java.time.LocalDateTime visitTime;
    private String customerCode;
    private String visitCode;
    public java.time.LocalDateTime getVisitTime(){return visitTime;}
    public void setVisitTime(java.time.LocalDateTime value){visitTime=value;}
    public String getCustomerCode(){return customerCode;}
    public void setCustomerCode(String value){customerCode=value;}
    public String getVisitCode(){return visitCode;}
    public void setVisitCode(String value){visitCode=value;}
    private int id;
    private String invoiceCode;
    private int visitId;
    private java.math.BigDecimal totalAmount;
    private String customerName;
    public Invoice4() {}
    public int getId() { return id; }
    public void setId(int value) { this.id = value; }
    public String getInvoiceCode() { return invoiceCode; }
    public void setInvoiceCode(String value) { this.invoiceCode = value; }
    public int getVisitId() { return visitId; }
    public void setVisitId(int value) { this.visitId = value; }
    public java.math.BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(java.math.BigDecimal value) { this.totalAmount = value; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String value) { this.customerName = value; }
    private int customerId;
    private boolean paid;
    private java.time.LocalDateTime paymentTime;
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int value) { this.customerId = value; }
    public boolean isPaid() { return paid; }
    public void setPaid(boolean value) { this.paid = value; }
    public java.time.LocalDateTime getPaymentTime() { return paymentTime; }
    public void setPaymentTime(java.time.LocalDateTime value) { this.paymentTime = value; }
    public String getVisitDateText() {
        return visitTime == null ? "" : visitTime.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    public String getPaymentTimeText() {
        return paymentTime == null ? "" : paymentTime.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
