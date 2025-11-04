import java.util.Date;

public class Order {
    private String orderId;
    private String customerRef;
    private Date orderDate;
    private double totalPrice;
    private String orderStatus;

    private LinkedList<Product> products;

    public Order(String orderId, String customerRef) {
        this.orderId = orderId;
        this.customerRef = customerRef;
        this.products = new LinkedList<>();
        this.orderDate = new Date();
        this.totalPrice = 0;
        orderStatus = "pending";
    }

    public void addProduct(Product p) {
        products.insert(p);
        totalPrice += p.getPrice();
    }

    public double getTotalPrice() { return totalPrice; }
    public String getCustomerRef() { return customerRef; }
    public String getOrderStatus() { return orderStatus; }
    public String getOrderId() { return orderId; }
    public Date getOrderDate() { return orderDate; }
    public LinkedList<Product> getProducts() { return products; }

    public void displayOrder() {
        System.out.println("\nOrder ID: " + orderId + " | Date: " + orderDate);
        System.out.println("Products:");
        Node<Product> current = products.getHead();
        while (current != null) {
            Product p = current.data;
            System.out.println(" - " + p.getName() + " (" + p.getPrice() + " SAR)");
            current = current.next;
        }
        System.out.println("Total Price: " + totalPrice + " SAR");
    }
}
