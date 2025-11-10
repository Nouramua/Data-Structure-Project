public class Customer {

    private String customerId;
    private String name;
    private String email;
    private LinkedList<Order> orders;

    public Customer(String customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.orders = new LinkedList<>();
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public LinkedList<Order> getOrders() { return orders; }

    public void displayOrderHistory() {
        if (orders == null || orders.empty()) {
            System.out.println("No order history found for " + this.name);
            return;
        }

        System.out.println("=== ORDER HISTORY ===");
        System.out.println("Customer: " + this.name);

        Node<Order> currentNode = orders.getHead();
        int orderNumber = 1;

        while (currentNode != null) {
            Order currentOrder = currentNode.data;

            System.out.println("\n--- Order #" + orderNumber + " ---");
            System.out.println("Order ID: " + currentOrder.getOrderId());
            System.out.println("Date: " + currentOrder.getOrderDate());
            System.out.println("Total: $" + String.format("%.2f", currentOrder.getTotalPrice()));
            System.out.println("Status: " + currentOrder.getStatus());

            LinkedList<Product> products = currentOrder.getListOfProducts();
            if (products != null && !products.empty()) {
                System.out.println("Products:");
                Node<Product> currentProductNode = products.getHead();
                while (currentProductNode != null) {
                    Product currentProduct = currentProductNode.data;
                    System.out.println("  - " + currentProduct.getName()
                            + " @ $" + String.format("%.2f", currentProduct.getPrice()));
                    currentProductNode = currentProductNode.next;
                }
            }

            currentNode = currentNode.next;
            orderNumber++;
        }

        System.out.println("=====================");
    }

    @Override
    public String toString() {
        return String.format(
            "Customer[ID: %s, Name: %s, Email: %s, Orders: %d]",
            customerId, name, email,
            orders != null ? orders.getSize() : 0
        );
    }

    public String toCSV() {
        return customerId + "," + name + "," + email;
    }
}

