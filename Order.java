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
        this.totalPrice = calculateTotalPrice();
        this.orderDate = new Date();
        this.status = "pending";
    }

    public void addProduct(Product p) {
        listOfProducts.insert(p);
        totalPrice = calculateTotalPrice();
    }

    private double calculateTotalPrice() {
        double total = 0;
        LinkedList.Node<Product> current = listOfProducts.getHead();
        while (current != null) {
            total += current.data.getPrice();
            current = current.next;
        }
        return total;
    }

    public String getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public LinkedList<Product> getProducts() { return listOfProducts; }
    public double getTotalPrice() { return totalPrice; }
    public Date getOrderDate() { return orderDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String toCSV() {
        StringBuilder productIds = new StringBuilder();
        LinkedList.Node<Product> current = listOfProducts.getHead();
        while (current != null) {
            productIds.append(current.data.getProductId());
            if (current.next != null) productIds.append("|"); // use | for multiple products
            current = current.next;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return orderId + "," + customer.getCustomerId() + "," + productIds + "," + totalPrice + "," + sdf.format(orderDate) + "," + status;
    }
}
