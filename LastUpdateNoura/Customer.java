package DSProject;
import java.io.*;

public class Customer {
    private String customerId;
    private String name;
    private String email;
    private LinkedList<Order> orders;
    
    private static LinkedList<Customer> customers = new LinkedList<Customer>();;


    public Customer(String customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        orders = new LinkedList<Order>();
    }
    
    public static void loadFromCSV(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        br.readLine(); // skip header
        
        int count = 0;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                String id = parts[0];
                String name = parts[1];
                String email = parts[2];
                customers.insert(new Customer(id, name, email));
                count++;
            }
        }
        br.close();
        System.out.println("✅ Loaded " + count + " customers");
    }
    
    public static void saveToCSV(String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write("customerId,name,email");
        bw.newLine();
        Node<Customer> current = customers.getHead();
        while (current != null) {
            bw.write(current.data.toCSV());
            bw.newLine();
            current = current.next;
        }
        bw.close();
    }
    
    public static boolean registerCustomer(Customer c) {        
        boolean isUnique = true;
        
        if (!customers.empty()) {
            customers.findfirst();
            
            while (!customers.last()) {
                if (c.getCustomerId().equals(customers.retrieve().getCustomerId())) {
                    isUnique = false;
                    break;
                }
                customers.findnext();
            }
            
            if (c.getCustomerId().equals(customers.retrieve().getCustomerId())) {
                isUnique = false;
            }
        }
        if (isUnique) {
        customers.insert(c);
        }
        return isUnique;
    }
    
    public static boolean placeCustomerOrder(Customer c, Order o) {    	
        boolean isUnique = true;
        
        if (!Order.getOrders().empty()) {
        	Order.getOrders().findfirst();
            
            while (!Order.getOrders().last()) {
                if (o.getOrderId().equals(Order.getOrders().retrieve().getOrderId())) {
                    isUnique = false;
                    break;
                }
                Order.getOrders().findnext();
            }
            
            if (o.getOrderId().equals(Order.getOrders().retrieve().getOrderId())) {
                isUnique = false;
            }
        }
        if (isUnique) {
        	Order.getOrders().insert(o);
        	c.orders.insert(o);
        }
        return isUnique;	
    }
    
    public static Customer findCustomerById(String id) {
        Node<Customer> current = customers.getHead();
        while (current != null) {
            if (current.data.getCustomerId().equals(id)) return current.data;
            current = current.next;
        }
        return null;
        
    }
    
    public static void viewOrderHistory(Customer c) {
        if (c.orders == null || c.orders.empty()) {
            System.out.println("No orders found for customer: " + c.getName());
            return;
        }
        
        System.out.println("=== ORDER HISTORY ===");
        System.out.println("Customer: " + c.getName());
        System.out.println("=====================");
        
        int orderNumber = 1;
        c.orders.findfirst();
        
        while (true) {
            Order currentOrder = c.orders.retrieve();
            
            System.out.println("\nOrder #" + orderNumber);
            System.out.println("ID: " + currentOrder.getOrderId());
            System.out.println("Date: " + currentOrder.getOrderDate());
            System.out.println("Total: $" + currentOrder.getTotalPrice());
            System.out.println("Status: " + currentOrder.getStatus());
            
            LinkedList<Product> products = currentOrder.getListOfProducts();
            if (products == null || products.empty()) {
                System.out.println("Products: This order is empty");
            } else {
                System.out.println("Products:");
                products.findfirst();
                int productNumber = 1;
                
                while (true) {
                    Product p = products.retrieve();
                    System.out.println("  " + productNumber + ". " + p.getName() + " - $" + p.getPrice());
                    
                    if (products.last()) break;
                    products.findnext();
                    productNumber++;
                }
            }
            
            if (c.orders.last()) break;
            c.orders.findnext();
            orderNumber++;
        }
        
        System.out.println("=====================");
        System.out.println("Total Orders: " + orderNumber);
        System.out.println("=====================");
    }
    
    //Setters & Getters
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public LinkedList<Order> getOrders() { return orders; }
    public static LinkedList<Customer> getCustomers() { return customers; }
    
    public String toString() {
        return String.format("Customer[ID: %s, Name: %s, Email: %s, Orders: %d]", 
                            customerId, name, email, orders.getSize());
    }

    public String toCSV() {
        return customerId + "," + name + "," + email;
    }
}