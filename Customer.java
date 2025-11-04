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

    public String toCSV() {
        return customerId + "," + name + "," + email;
    }
}
