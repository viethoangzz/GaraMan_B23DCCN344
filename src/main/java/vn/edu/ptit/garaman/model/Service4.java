package vn.edu.ptit.garaman.model;

/** Entity Service4 matching GaraMan report (suffix 4). */
public class Service4 {
    private int id;
    private String serviceCode;
    private String name;
    private String description;
    private java.math.BigDecimal unitPrice;
    public Service4() {}
    public int getId() { return id; }
    public void setId(int value) { this.id = value; }
    public String getServiceCode() { return serviceCode; }
    public void setServiceCode(String value) { this.serviceCode = value; }
    public String getName() { return name; }
    public void setName(String value) { this.name = value; }
    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }
    public java.math.BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(java.math.BigDecimal value) { this.unitPrice = value; }
}
