package DSProject;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {
    private String orderId;
    private Customer customer;
    private double totalPrice;
    private String orderDate;
    private String status;
    private LinkedList<Product> products;

    private static LinkedList<Order> orders = new LinkedList<Order>();


    public Order(String orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.products = new LinkedList<Product>();
        this.totalPrice = calculateTotalPrice();
        this.orderDate = getCurrentDate();
        this.status = "pending";
    }
    
    public static void loadFromCSV(String fileName, LinkedList<Customer> customers, LinkedList<Product> products) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        br.readLine(); // skip header
        
        int count = 0;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                String orderId = parts[0];
                String customerId = parts[1];
                String productsStr = parts[2].replace("\"", "");
                double totalPrice = Double.parseDouble(parts[3]);
                String orderDate = parts[4];
                String status = parts[5];

                // Find customer
                Customer customer = null;
                Node<Customer> cNode = customers.getHead();
                while (cNode != null) {
                    if (cNode.data.getCustomerId().equals(customerId)) {
                        customer = cNode.data;
                        break;
                    }
                    cNode = cNode.next;
                }

                if (customer != null) {
                    Order order = new Order(orderId, customer);
                    order.setOrderDate(orderDate);
                    order.products = new LinkedList<Product>();
                    order.setTotalPrice(totalPrice);
                    order.setStatus(status);

                    // Add products
                    String[] productIds = productsStr.split(";");
                    for (String pid : productIds) {
                        Node<Product> pNode = products.getHead();
                        while (pNode != null) {
                            if (pNode.data.getProductId().equals(pid)) {
                                order.addProduct(pNode.data);
                                break;
                            }
                            pNode = pNode.next;
                        }
                    }

                    orders.insert(order);
                    customer.getOrders().insert(order);
                    count++;
                }
            }
        }
        br.close();
        System.out.println("✅ Loaded " + count + " orders");
    }
    
    public static void saveToCSV(String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write("orderId,customerId,productIds,totalPrice,orderDate,status");
        bw.newLine();
        Node<Order> current = orders.getHead();
        while (current != null) {
            bw.write(current.data.toCSV());
            bw.newLine();
            current = current.next;
        }
        bw.close();
    }
    
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
    
	public static boolean createOrder(Customer c, Order o) {
	    boolean result = Customer.placeCustomerOrder(c, o);
	    return result;
	}
	
	public static void cancelOrder(Order o) {
		o.setStatus("Cancelled");
	}
	
	public static Order findOrderById(String id) {
	    Node<Order> current = orders.getHead();
	    while (current != null) {
	        if (current.data.getOrderId().equals(id)) return current.data;
	        current = current.next;
	    }
	    return null;
	}
	
	public static void updateOrderStatus(Order o, String s) {
	    o.setStatus(s);
	}
	    
    public void addProduct(Product p) {
        products.insert(p);
        setTotalPrice(calculateTotalPrice());
    }

    private double calculateTotalPrice() {
        double total = 0;
        Node<Product> current = products.getHead();
        while (current != null) {
            total += current.data.getPrice();
            current = current.next;
        }
        return total;
    }
    
    public static void showOrdersBetweenDates(String start, String end) {
        boolean found = false;
        
        if (orders.empty()) {
            System.out.println("No orders found in the system.");
            return;
        }
        
        System.out.println("=== ORDERS BETWEEN " + start + " AND " + end + " ===");
        
        orders.findfirst();
        
        while (true) {
            Order currentOrder = orders.retrieve();
            String orderDate = currentOrder.getOrderDate();
            
            if (orderDate.compareTo(start) >= 0 && orderDate.compareTo(end) <= 0) {
                System.out.println(currentOrder.toString());
                System.out.println("----------------------------------------");
                found = true;
            }
            
            if (orders.last()) break;
            orders.findnext();
        }
        
        Order lastOrder = orders.retrieve();
        String lastOrderDate = lastOrder.getOrderDate();
        if (lastOrderDate.compareTo(start) >= 0 && lastOrderDate.compareTo(end) <= 0) {
            System.out.println(lastOrder.toString());
            System.out.println("----------------------------------------");
            found = true;
        }
        
        if (!found) {
            System.out.println("No orders found between these dates.");
        }
    }
    
    //Setters & Getters
    public void setCustomer(Customer customer) {this.customer = customer;}
    public void setTotalPrice(double totalPrice) {this.totalPrice = totalPrice;}
    public void setOrderDate(String orderDate) {this.orderDate = orderDate;}

    public String getOrderId() {return orderId;}
    public Customer getCustomer() {return customer;}
    public LinkedList<Product> getListOfProducts() {return products;}
    public double getTotalPrice() {return totalPrice;}
    public String getOrderDate() {return orderDate;}
    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
    public static LinkedList<Order> getOrders() { return orders; }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Order[ID: %s, Customer: %s, Date: %s, Status: %s, Total: $%.2f]", 
                              orderId, customer.getName(), orderDate, status, totalPrice));
        
        if (products != null && !products.empty()) {
            sb.append("\nProducts:");
            products.findfirst();
            int count = 1;
            
            while (true) {
                Product p = products.retrieve();
                sb.append(String.format("\n  %d. %s - $%.2f", count, p.getName(), p.getPrice()));
                count++;
                
                if (products.last()) break;
                products.findnext();
            }
        } else {
            sb.append("\nProducts: Empty");
        }
        
        return sb.toString();
    }

    public String toCSV() {
        StringBuilder productIds = new StringBuilder();
        Node<Product> current = getListOfProducts().getHead();
        while (current != null) {
            productIds.append(current.data.getProductId());
            if (current.next != null) productIds.append("|");
            current = current.next;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return getOrderId() + "," + getCustomer().getCustomerId() + "," + productIds + "," + getTotalPrice() + "," + sdf.format(getOrderDate()) + "," + getStatus();
    }
    
}