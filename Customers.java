public class Customers {

    private LinkedList<Customer> customers = new LinkedList<>();

    public Customers() { } // empty constructor, needed later for CSV loading

    public Customers(LinkedList<Customer> input_customers) {
        this.customers = (input_customers != null) ? input_customers : new LinkedList<>();
    }

    public LinkedList<Customer> getCustomers() {
        return customers;
    }

    public boolean register(Customer c) {
        if (c == null) {
            System.out.println("Cannot add a null customer");
            return false;
        }
        if (findById(c.getCustomerId()) != null) {
            System.out.println("Customer already exists");
            return false;
        }
        customers.add(c);
        System.out.println("Customer Successfully added");
        return true;
    }

    public Customer findById(int customerId) {
        if (customers == null || customers.isEmpty()) return null;
        for (int i = 0; i < customers.size(); i++) {
            Customer cur = customers.get(i);
            if (cur != null && cur.getCustomerId() == customerId) return cur;
        }
        return null;
    }

    public Customer findByName(String name) {
        if (name == null || customers == null || customers.isEmpty()) return null;
        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            if (c != null && name.equalsIgnoreCase(c.getName())) return c;
        }
        return null;
    }

    public LinkedList<Customer> all() {
        return customers;
    }

    public void loadCustomers(String filename) {
        try (Scanner read = new Scanner(new File(filename))) {
            if (read.hasNextLine()) read.nextLine(); // skip header

            while (read.hasNextLine()) {
                String line = read.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] a = line.split(",");
                if (a.length < 3) continue;

                int id = Integer.parseInt(a[0].trim());
                String name = a[1].trim();
                String email = a[2].trim();

                register(new Customer(id, name, email)); // enforces unique ids
            }
            System.out.println("Customers loaded successfully!");
        } catch (Exception e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }
    }
}
