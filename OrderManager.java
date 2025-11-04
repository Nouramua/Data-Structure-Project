import java.io.*;
import java.util.Date;

public class OrderManager {
    private static LinkedList<Order> orders = new LinkedList<>();

    public static void loadFromCSV(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 3) continue;

                String orderId = parts[0];
                String customerRef = parts[1];
                Date orderDate = new Date(); // simplified: could parse date if saved in file

                Order o = new Order(orderId, customerRef);
                orders.insert(o);
            }
            System.out.println("Orders loaded successfully from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading orders: " + e.getMessage());
        }
    }

    public static void saveToCSV(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            Node<Order> node = orders.getHead();
            while (node != null) {
                Order o = node.data;
                bw.write(o.getOrderId() + "," + o.getCustomerRef() + "," + o.getOrderStatus());
                bw.newLine();
                node = node.next;
            }
        } catch (IOException e) {
            System.out.println("Error saving orders: " + e.getMessage());
        }
    }

    public static LinkedList<Order> getOrders() {
        return orders;
    }

    public static void addOrder(Order o) {
        orders.insert(o);
    }
}
