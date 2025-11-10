import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {
    private String orderId;
    private Customer customer;
    private LinkedList<Product> listOfProducts;
    private double totalPrice;
    private Date orderDate;
    private String status;

    public Order(String orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.listOfProducts = new LinkedList<>();
        this.totalPrice = 0.0;          
        this.orderDate = new Date();    
        this.status = "Pending";        
    }

    public void addProduct(Product p) {
        if (p == null) return;
        listOfProducts.insert(p);
        totalPrice += p.getPrice();
    }

    public double calculateTotalPrice() {
        double total = 0;
        Node<Product> current = listOfProducts.getHead();
        while (current != null) {
            total += current.data.getPrice();
            current = current.next;
        }
        return total;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(
                "Order[ID: %s, Customer: %s, Date: %s, Status: %s, Total: $%.2f]\n",
                orderId,
                customer != null ? customer.getName() : "N/A",
                orderDate != null ? sdf.format(orderDate) : "N/A",
                status,
                totalPrice
        ));
        sb.append("Products:\n");

        Node<Product> current = listOfProducts.getHead();
        int count = 1;
        while (current != null) {
            Product p = current.data;
            sb.append(String.format("  %d. %s - $%.2f\n", count, p.getName(), p.getPrice()));
            current = current.next;
            count++;
        }

        return sb.toString();
    }

    public String toCSV() {
        StringBuilder productIds = new StringBuilder();
        Node<Product> current = listOfProducts.getHead();
        while (current != null) {
            productIds.append(current.data.getProductId());
            if (current.next != null) {
                productIds.append(";");
            }
            current = current.next;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = (orderDate != null) ? sdf.format(orderDate) : "";

        return orderId + ","
                + (customer != null ? customer.getCustomerId() : "") + ","
                + productIds + ","
                + totalPrice + ","
                + dateStr + ","
                + status;
    }

    // Getters & setters
    public String getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public LinkedList<Product> getListOfProducts() { return listOfProducts; }
    public LinkedList<Product> getProducts() { return listOfProducts; }

    public double getTotalPrice() { return totalPrice; }
    public Date getOrderDate() { return orderDate; }
    public String getStatus() { return status; }

    public void setCustomer(Customer customer) { this.customer = customer; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
    public void setStatus(String status) { this.status = status; }
}
