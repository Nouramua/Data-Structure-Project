import java.util.Date;

public class Order {
    private String orderId;
    private String customerRef;
    private LinkedList<Product> listOfProducts;
    private double totalPrice;
    private Date orderDate;
    private String status; 
    
    public Order(String orderId, String customerRef, LinkedList<Product> listOfProducts) {
        this.orderId = orderId;
        this.customerRef = customerRef;
        this.listOfProducts = listOfProducts;
        this.totalPrice = calculateTotalPrice();
        this.orderDate = new Date();
        this.status = "pending";
    }
    
    private double calculateTotalPrice() {
        double total = 0.0;
        for (int i = 0; i < listOfProducts.size(); i++) {
            Product product = listOfProducts.get(i);
            total += product.getPrice();
        }
        return total;
    }
    
    public String getOrderId() { return orderId; }
    public String getCustomerRef() { return customerRef; }
    public LinkedList<Product> getListOfProducts() { return listOfProducts; }
    public double getTotalPrice() { return totalPrice; }
    public Date getOrderDate() { return orderDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    @Override
    public String toString() {
        return String.format("Order[ID: %s, Customer: %s, Total: $%.2f, Status: %s]", 
                           orderId, customerRef, totalPrice, status);
    }
}