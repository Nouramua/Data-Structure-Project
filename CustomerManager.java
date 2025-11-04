import java.io.*;

public class CustomerManager {
    private static LinkedList<Customer> customers = new LinkedList<>();

    public static void loadFromCSV(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 3) continue;

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String email = parts[2];

                customers.insert(new Customer(id, name, email));
            }
            System.out.println("Customers loaded successfully from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }
    }

    public static void saveToCSV(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            Node<Customer> node = customers.getHead();
            while (node != null) {
                Customer c = node.data;
                bw.write(c.getCustomerId() + "," + c.getName() + "," + c.getEmail());
                bw.newLine();
                node = node.next;
            }
        } catch (IOException e) {
            System.out.println("Error saving customers: " + e.getMessage());
        }
    }

    public static LinkedList<Customer> getCustomers() {
        return customers;
    }

    public static void addCustomer(Customer c) {
        customers.insert(c);
    }
}
