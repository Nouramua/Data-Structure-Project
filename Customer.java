public class Customer {
    private int customerId;
    private String name;
    private String email;
    private LinkedList<Order> orders;

    public Customer(int customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.orders = new LinkedList<>();
    }

    public int getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public LinkedList<Order> getOrders() { return orders; }

    // Creates a new order for this customer
    public Order createOrder(String orderId, String customerRef) {
        Order o = new Order(orderId, customerRef);
        orders.insert(o);
        System.out.println("✅ Order created for customer " + name);
        return o;
    }

    public void displayOrders() {
        if (orders.getSize() == 0) {
            System.out.println(name + " has no orders.");
            return;
        }
        Node<Order> current = orders.getHead();
        while (current != null) {
            current.data.displayOrder();
            current = current.next;
        }
    }
}
