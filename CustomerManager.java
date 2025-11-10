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
        br.readLine(); 
        
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
        System.out.println("Loaded " + count + " customers");
    }
    
    public boolean addCustomer(Customer c) {
        Node<Customer> tmp = customers.getHead();
        while (tmp != null) {
            if (c.getCustomerId().equals(tmp.data.getCustomerId())) {
                return false;
            }
            tmp = tmp.next;
        }
        customers.insert(c);
        return true;
    }

    public void createCustomer(Scanner sc, ProductManager pm, CustomerManager cm) {
        System.out.print("Enter customer ID: ");
        String id = sc.nextLine().trim();

        System.out.print("Enter name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter email: ");
        String email = sc.nextLine().trim();

        if (id.isEmpty() || name.isEmpty() || email.isEmpty()) {
            System.out.println("All fields are required, try again.");
            return;
        }

        Customer c = new Customer(id, name, email);
        boolean result = addCustomer(c);

        if (result) {
            System.out.println("Customer registered!");
        } else {
            System.out.println("Customer ID already exists. Customer not registered.");
        }
    }


    public void displayAllCustomers() {
    Node<Customer> current = customers.getHead();
    System.out.println("\n--- Customer List ---");
    while (current != null) {
        Customer c = current.data;
        System.out.println(c.getCustomerId() + " | " + c.getName() + " | " + c.getEmail());
        current = current.next;
    }
}

public void viewOrderHistory(Scanner sc) {
    System.out.print("Enter customer ID: ");
    String cid = sc.nextLine();
    Customer c = findCustomerById(cid);
    if (c == null) {
        System.out.println("Customer not found.");
        return;
    }
    c.displayOrderHistory();
}

public void searchCustomerById(Scanner sc) {
    System.out.print("Enter customer ID to search: ");
    String cid = sc.nextLine();
    Customer c = findCustomerById(cid);
    if (c == null) System.out.println("customer not found.");
    else System.out.println(c.toString());
}

public Customer findCustomerById(String id) {
    Node<Customer> current = customers.getHead();
    while (current != null) {
        if (current.data.getCustomerId().equals(id)) return current.data;
        current = current.next;
    }
    return null;
}


    public void saveToCSV(String fileName) throws IOException {
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

    public LinkedList<Customer> getCustomers() { return customers; }

}
