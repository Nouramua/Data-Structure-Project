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
        br.readLine(); 

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int count = 0;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                String orderId = parts[0].trim();
                String customerId = parts[1].trim();
                String productsStr = parts[2].replace("\"", "").trim();
                String orderDateStr = parts[4].trim();
                String status = parts[5].trim();

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

                    if (!orderDateStr.isEmpty()) {
                        try {
                            Date d = sdf.parse(orderDateStr);
                            order.setOrderDate(d);
                        } catch (ParseException e) {
                        }
                    }

                    order.setStatus(status);

                    if (!productsStr.isEmpty()) {
                        String[] productIds = productsStr.split(";");
                        for (String pid : productIds) {
                            pid = pid.trim();
                            Node<Product> pNode = products.getHead();
                            while (pNode != null) {
                                if (pNode.data.getProductId().equals(pid)) {
                                    order.addProduct(pNode.data);
                                    break;
                                }
                                pNode = pNode.next;
                            }
                        }
                    }

                    addOrderFromCSV(order);
                    customer.getOrders().insert(order);
                    count++;
                }
            }
        }

        br.close();
        System.out.println("Loaded " + count + " orders");
    }

    private void addOrderFromCSV(Order o) {
        orders.insert(o);
    }

    public boolean addOrder(Order o) {
        Node<Order> tmp = orders.getHead();
        while (tmp != null) {
            if (tmp.data.getOrderId().equals(o.getOrderId())) {
                return false;
            }
            tmp = tmp.next;
        }
        orders.insert(o);
        return true;
    }

 
    public void createOrder(Scanner sc, CustomerManager cm) {
        System.out.print("Enter order ID: ");
        String ordID = sc.nextLine().trim();

        if (ordID.isEmpty()) {
            System.out.println("Order ID is required.");
            return;
        }

        if (findOrderById(ordID) != null) {
            System.out.println("Order ID already exists.");
            return;
        }

        System.out.print("Enter customer ID: ");
        String custID = sc.nextLine().trim();

        Customer c = cm.findCustomerById(custID);
        if (c == null) {
            System.out.println("Customer not found.");
            return;
        }

        Order o = new Order(ordID, c);

        if (!addOrder(o)) {
            System.out.println("Order ID already exists, order not created.");
            return;
        }

        c.getOrders().insert(o);

        System.out.println("Order created and linked to customer successfully!");
    }

    public void cancelOrder(Scanner sc) {
        System.out.print("Enter order ID to cancel: ");
        String oid = sc.nextLine().trim();
        Order o = findOrderById(oid);
        if (o == null) {
            System.out.println("Order not found.");
        } else {
            o.setStatus("Cancelled");
            System.out.println("Order cancelled.");
        }
    }

    public void updateOrderStatus(Scanner sc) {
        System.out.print("Enter order ID to update: ");
        String oid = sc.nextLine().trim();
        Order o = findOrderById(oid);
        if (o == null) {
            System.out.println("Order not found.");
        } else {
            System.out.print("Enter new status: ");
            String status = sc.nextLine().trim();
            o.setStatus(status);
            System.out.println("Order status updated.");
        }
    }

    public void searchOrderById(Scanner sc) {
        System.out.print("Enter order ID to search: ");
        String oid = sc.nextLine().trim();
        Order o = findOrderById(oid);
        if (o == null) {
            System.out.println("Order not found.");
        } else {
            System.out.println(o.toString());
        }
    }

    public void showOrdersBetweenDates(Scanner sc) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            System.out.print("Enter start date (yyyy-MM-dd): ");
            Date start = sdf.parse(sc.nextLine().trim());
            System.out.print("Enter end date (yyyy-MM-dd): ");
            Date end = sdf.parse(sc.nextLine().trim());

            if (start.after(end)) {
                System.out.println("Start date cannot be after end date.");
                return;
            }

            Node<Order> current = orders.getHead();
            boolean found = false;
            while (current != null) {
                Date d = current.data.getOrderDate();
                if (d != null && !d.before(start) && !d.after(end)) {
                    System.out.println(current.data);
                    found = true;
                }
                current = current.next;
            }

            if (!found) {
                System.out.println("No orders found between these dates.");
            }
        } catch (ParseException e) {
            System.out.println("Invalid date format.");
        }
    }

    public Order findOrderById(String id) {
        Node<Order> current = orders.getHead();
        while (current != null) {
            if (current.data.getOrderId().equals(id)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public void saveToCSV(String fileName) throws IOException {
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

    public LinkedList<Order> getOrders() {
        return orders;
    }
}