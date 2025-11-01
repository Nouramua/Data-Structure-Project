public class OrderManager {
    private LinkedList<Order> orders;
    private int orderCounter;
    
    public OrderManager() {
        this.orders = new LinkedList<>();
        this.orderCounter = 1;
    }
    
    /** Time Complexity: O(n) where n is number of products */
    public Order createOrder(String customerRef, LinkedList<Product> products) {
        if (customerRef == null || customerRef.isEmpty()) {
            System.out.println("Error: Customer reference cannot be empty");
            return null;
        }
        
        if (products == null || products.isEmpty()) {
            System.out.println("Error: Products list cannot be empty");
            return null;
        }
        
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (product.getStock() <= 0) {
                System.out.println("Error: Product " + product.getName() + " is out of stock");
                return null;
            }
        }
        
        String orderId = "ORD" + String.format("%04d", orderCounter++);
        Order newOrder = new Order(orderId, customerRef, products);
        
        updateProductStock(products);
        orders.add(newOrder);
        
        System.out.println("Order created successfully: " + newOrder);
        return newOrder;
    }
    
    /* Get all orders between two dates (functional requirement) */
    public LinkedList<Order> getAllOrdersBetweenDates(Date startDate, Date endDate) {
        LinkedList<Order> result = new LinkedList<>();
        
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            Date orderDate = order.getOrderDate();
            
            if (orderDate.compareTo(startDate) >= 0 && orderDate.compareTo(endDate) <= 0) {
                result.add(order);
            }
        }
        
        System.out.println("Found " + result.size() + " orders between the dates");
        return result;
    }
    
    /* Search order by ID (functional requirement) */
    public Order searchOrderById(String orderId) {
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        System.out.println("Order not found: " + orderId);
        return null;
    }
    
    /* Cancel an order (required operation) */
    public boolean cancelOrder(String orderId) {
        Order order = searchOrderById(orderId);
        if (order != null && order.getStatus().equals("pending")) {
            order.setStatus("canceled");
            restoreProductStock(order.getListOfProducts());
            System.out.println("Order canceled: " + orderId);
            return true;
        }
        System.out.println("Cannot cancel order: " + orderId);
        return false;
    }
    
    /* Update order status (required operation)*/
    public boolean updateStatus(String orderId, String newStatus) {
        Order order = searchOrderById(orderId);
        if (order != null) {
            order.setStatus(newStatus);
            System.out.println("Order status updated to: " + newStatus);
            return true;
        }
        return false;
    }
    
    /* Update product stock after order creation */
    private void updateProductStock(LinkedList<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            product.setStock(product.getStock() - 1);
        }
    }
    
    /* Restore product stock when order is canceled */
    private void restoreProductStock(LinkedList<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            product.setStock(product.getStock() + 1);
        }
    }
    

    public void displayAllOrders() {
        System.out.println("\n=== ALL ORDERS ===");
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }
        
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            System.out.println((i + 1) + ". " + order);
        }
    }
}