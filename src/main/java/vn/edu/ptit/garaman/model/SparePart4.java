package vn.edu.ptit.garaman.model;

/** Entity SparePart4 matching GaraMan report (suffix 4). */
public class SparePart4 {
    private int id;
    private String partCode;
    private String name;
    private String description;
    private java.math.BigDecimal unitPrice;
    private int quantity;
    public SparePart4() {}
    public int getId() { return id; }
    public void setId(int value) { this.id = value; }
    public String getPartCode() { return partCode; }
    public void setPartCode(String value) { this.partCode = value; }
    public String getName() { return name; }
    public void setName(String value) { this.name = value; }
    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }
    public java.math.BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(java.math.BigDecimal value) { this.unitPrice = value; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int value) { this.quantity = value; }
}
