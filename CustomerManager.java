import java.io.*;
import java.util.Scanner;

public class CustomerManager {
    private LinkedList<Customer> customers;

    public CustomerManager() {
        customers = new LinkedList<>();
    }

    public void loadFromCSV(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        br.readLine(); // skip header
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",", -1);
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            String email = parts[2];
            customers.insert(new Customer(id, name, email));
        }
        br.close();
    }

    public void displayAllCustomers() {
    LinkedList.Node<Customer> current = customers.getHead();
    System.out.println("\n--- Customer List ---");
    while (current != null) {
        Customer c = current.data;
        System.out.println(c.getCustomerId() + " | " + c.getName() + " | " + c.getEmail());
        current = current.next;
    }
}

public void placeOrderForCustomer(Scanner sc, ProductManager pm, OrderManager om) {
    System.out.print("Enter customer ID: ");
    int cid = sc.nextInt(); sc.nextLine();
    Customer c = findCustomerById(cid);
    if (c == null) {
        System.out.println("Customer not found.");
        return;
    }
    om.createOrderInteractiveForCustomer(sc, pm, c);
}

public void viewOrderHistory(Scanner sc) {
    System.out.print("Enter customer ID: ");
    int cid = sc.nextInt(); sc.nextLine();
    Customer c = findCustomerById(cid);
    if (c == null) {
        System.out.println("Customer not found.");
        return;
    }
    c.displayOrderHistory();
}

private Customer findCustomerById(int id) {
    LinkedList.Node<Customer> current = customers.getHead();
    while (current != null) {
        if (current.data.getCustomerId() == id) return current.data;
        current = current.next;
    }
    return null;
}


    public void saveToCSV(String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write("customerId,name,email");
        bw.newLine();
        LinkedList.Node<Customer> current = customers.getHead();
        while (current != null) {
            bw.write(current.data.toCSV());
            bw.newLine();
            current = current.next;
        }
        bw.close();
    }

    public LinkedList<Customer> getCustomers() { return customers; }
}
