import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class OrderManager {
    private LinkedList<Order> orders;

    public OrderManager() {
        orders = new LinkedList<>();
    }

    public void loadFromCSV(String fileName, LinkedList<Customer> customers, LinkedList<Product> products) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        br.readLine(); // skip header
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",", -1);
            String orderId = parts[0];
            int customerId = Integer.parseInt(parts[1]);

            Customer customer = null;
            LinkedList.Node<Customer> cNode = customers.getHead();
            while (cNode != null) {
                if (cNode.data.getCustomerId() == customerId) {
                    customer = cNode.data;
                    break;
                }
                cNode = cNode.next;
            }
            if (customer == null) continue;

            Order order = new Order(orderId, customer);

            String[] productIds = parts[2].split("\\|");
            for (String pid : productIds) {
                int pId = Integer.parseInt(pid);
                LinkedList.Node<Product> pNode = products.getHead();
                while (pNode != null) {
                    if (pNode.data.getProductId() == pId) {
                        order.addProduct(pNode.data);
                        break;
                    }
                    pNode = pNode.next;
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            order.setStatus(parts[5]);
            orders.insert(order);
            customer.getOrders().insert(order);
        }
        br.close();
    }

    public void createOrderInteractive(Scanner sc, ProductManager pm, CustomerManager cm) {
    System.out.print("Enter customer ID: ");
    int cid = sc.nextInt(); sc.nextLine();
    Customer c = cm.findCustomerById(cid);
    if (c == null) {
        System.out.println("Customer not found.");
        return;
    }
    createOrderInteractiveForCustomer(sc, pm, c);
}

public void createOrderInteractiveForCustomer(Scanner sc, ProductManager pm, Customer c) {
    LinkedList<Product> selectedProducts = new LinkedList<>();
    boolean adding = true;
    while (adding) {
        System.out.print("Enter product ID to add (0 to finish): ");
        int pid = sc.nextInt(); sc.nextLine();
        if (pid == 0) break;
        Product p = pm.findProductById(pid);
        if (p == null) System.out.println("Product not found.");
        else selectedProducts.insert(p);
    }
    if (selectedProducts.getSize() == 0) {
        System.out.println("No products selected. Order cancelled.");
        return;
    }
    Order order = new Order(generateOrderId(), c.getCustomerId(), selectedProducts);
    orders.insert(order);
    c.addOrder(order);
    System.out.println("Order created successfully. ID: " + order.getOrderId());
}

public void cancelOrder(Scanner sc) {
    System.out.print("Enter order ID to cancel: ");
    String oid = sc.nextLine();
    Order o = findOrderById(oid);
    if (o == null) System.out.println("Order not found.");
    else {
        o.setStatus("Cancelled");
        System.out.println("Order cancelled.");
    }
}

public void updateOrderStatus(Scanner sc) {
    System.out.print("Enter order ID to update: ");
    String oid = sc.nextLine();
    Order o = findOrderById(oid);
    if (o == null) System.out.println("Order not found.");
    else {
        System.out.print("Enter new status: ");
        String status = sc.nextLine();
        o.setStatus(status);
        System.out.println("Order status updated.");
    }
}

public void searchOrderById(Scanner sc) {
    System.out.print("Enter order ID to search: ");
    String oid = sc.nextLine();
    Order o = findOrderById(oid);
    if (o == null) System.out.println("Order not found.");
    else System.out.println(o);
}

public void showOrdersBetweenDates(Scanner sc) {
    try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.print("Enter start date (yyyy-MM-dd): ");
        Date start = sdf.parse(sc.nextLine());
        System.out.print("Enter end date (yyyy-MM-dd): ");
        Date end = sdf.parse(sc.nextLine());

        LinkedList.Node<Order> current = orders.getHead();
        boolean found = false;
        while (current != null) {
            Date d = current.data.getOrderDate();
            if (!d.before(start) && !d.after(end)) {
                System.out.println(current.data);
                found = true;
            }
            current = current.next;
        }
        if (!found) System.out.println("No orders found between these dates.");
    } catch (ParseException e) {
        System.out.println("Invalid date format.");
    }
}

private String generateOrderId() {
    return "ORD" + (orders.getSize() + 1);
}

private Order findOrderById(String id) {
    LinkedList.Node<Order> current = orders.getHead();
    while (current != null) {
        if (current.data.getOrderId().equals(id)) return current.data;
        current = current.next;
    }
    return null;
}


    public void saveToCSV(String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write("orderId,customerId,productIds,totalPrice,orderDate,status");
        bw.newLine();
        LinkedList.Node<Order> current = orders.getHead();
        while (current != null) {
            bw.write(current.data.toCSV());
            bw.newLine();
            current = current.next;
        }
        bw.close();
    }

    public LinkedList<Order> getOrders() { return orders; }
}
