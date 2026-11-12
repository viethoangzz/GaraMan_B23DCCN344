package vn.edu.ptit.garaman.model;

/** Entity InvoicePartDetail4 matching GaraMan report (suffix 4). */
public class InvoicePartDetail4 {
    private int id;
    private int invoiceId;
    private int partId;
    private String code;
    private String name;
    private int quantity;
    private java.math.BigDecimal unitPrice;
    private java.math.BigDecimal amount;
    public InvoicePartDetail4() {}
    public int getId() { return id; }
    public void setId(int value) { this.id = value; }
    public int getInvoiceId() { return invoiceId; }
    public void setInvoiceId(int value) { this.invoiceId = value; }
    public int getPartId() { return partId; }
    public void setPartId(int value) { this.partId = value; }
    public String getCode() { return code; }
    public void setCode(String value) { this.code = value; }
    public String getName() { return name; }
    public void setName(String value) { this.name = value; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int value) { this.quantity = value; }
    public java.math.BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(java.math.BigDecimal value) { this.unitPrice = value; }
    public java.math.BigDecimal getAmount() { return amount; }
    public void setAmount(java.math.BigDecimal value) { this.amount = value; }
}
