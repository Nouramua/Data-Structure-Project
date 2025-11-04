import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        // Managers
        ProductManager pm = new ProductManager();
        CustomerManager cm = new CustomerManager();
        OrderManager om = new OrderManager();
        ReviewManager rm = new ReviewManager();

        // Load CSV files
        pm.loadFromCSV("dataset/products.csv");
        cm.loadFromCSV("dataset/customers.csv");
        om.loadFromCSV("dataset/orders.csv", cm.getCustomers(), pm.getProducts());
        rm.loadFromCSV("dataset/reviews.csv", cm.getCustomers(), pm.getProducts());

        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Products Menu");
            System.out.println("2. Customers Menu");
            System.out.println("3. Orders Menu");
            System.out.println("4. Reviews Menu");
            System.out.println("5. Reports Menu");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int mainChoice = sc.nextInt();
            sc.nextLine();

            switch (mainChoice) {
                case 1: productsMenu(sc, pm);
                case 2: customersMenu(sc, cm, pm, om);
                case 3: ordersMenu(sc, om, pm, cm);
                case 4: reviewsMenu(sc, rm, pm, cm);
                case 5: reportsMenu(sc, pm, om, rm, cm);
                case 6: exit = true;
                default: System.out.println("Invalid option!");
            }
        }

        // Save CSV files
        pm.saveToCSV("products.csv");
        cm.saveToCSV("customers.csv");
        om.saveToCSV("orders.csv");
        rm.saveToCSV("reviews.csv");

        sc.close();
        System.out.println("Exiting... All data saved!");
    }

    // ------------------- PRODUCTS MENU -------------------
    private static void productsMenu(Scanner sc, ProductManager pm) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- PRODUCTS MENU ---");
            System.out.println("1. Add Product");
            System.out.println("2. Remove Product");
            System.out.println("3. Update Product");
            System.out.println("4. Search Product");
            System.out.println("5. Track Out-of-Stock");
            System.out.println("6. Back");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1: {
                    System.out.print("Enter product ID: "); int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Enter name: "); String name = sc.nextLine();
                    System.out.print("Enter price: "); double price = sc.nextDouble(); sc.nextLine();
                    System.out.print("Enter stock: "); int stock = sc.nextInt(); sc.nextLine();
                    pm.getProducts().insert(new Product(id, name, price, stock));
                    System.out.println("Product added.");
                }
                case 2: {
                    System.out.print("Enter product ID to remove: "); int removeId = sc.nextInt(); sc.nextLine();
                    pm.removeProductById(removeId);
                }
                case 3: {
                    System.out.print("Enter product ID to update: "); int updateId = sc.nextInt(); sc.nextLine();
                    System.out.print("Enter new name: "); String newName = sc.nextLine();
                    System.out.print("Enter new price: "); double newPrice = sc.nextDouble(); sc.nextLine();
                    System.out.print("Enter new stock: "); int newStock = sc.nextInt(); sc.nextLine();
                    pm.updateProduct(updateId, newName, newPrice, newStock);
                }
                case 4: {
                    System.out.print("Enter product ID or name to search: "); String keyword = sc.nextLine();
                    pm.searchProduct(keyword);
                }
                case 5: pm.trackOutOfStock();
                case 6: back = true;
                default: System.out.println("Invalid option!");
            }
        }
    }

    // ------------------- CUSTOMERS MENU -------------------
    private static void customersMenu(Scanner sc, CustomerManager cm, ProductManager pm, OrderManager om) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- CUSTOMERS MENU ---");
            System.out.println("1. Register Customer");
            System.out.println("2. View Customers");
            System.out.println("3. Place Order for Customer");
            System.out.println("4. View Customer Order History");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1: {
                    System.out.print("Enter customer ID: "); int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Enter name: "); String name = sc.nextLine();
                    System.out.print("Enter email: "); String email = sc.nextLine();
                    cm.getCustomers().insert(new Customer(id, name, email));
                    System.out.println("Customer registered.");
                }
                case 2: cm.displayAllCustomers();
                case 3: cm.placeOrderForCustomer(sc, pm, om);
                case 4: cm.viewOrderHistory(sc);
                case 5: back = true;
                default: System.out.println("Invalid option!");
            }
        }
    }

    // ------------------- ORDERS MENU -------------------
    private static void ordersMenu(Scanner sc, OrderManager om, ProductManager pm, CustomerManager cm) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- ORDERS MENU ---");
            System.out.println("1. Create Order");
            System.out.println("2. Cancel Order");
            System.out.println("3. Update Order Status");
            System.out.println("4. Search Order by ID");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1: om.createOrderInteractive(sc, pm, cm);
                case 2: om.cancelOrder(sc);
                case 3: om.updateOrderStatus(sc);
                case 4: om.searchOrderById(sc);
                case 5: back = true;
                default: System.out.println("Invalid option!");
            }
        }
    }

    // ------------------- REVIEWS MENU -------------------
    private static void reviewsMenu(Scanner sc, ReviewManager rm, ProductManager pm, CustomerManager cm) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- REVIEWS MENU ---");
            System.out.println("1. Add Review");
            System.out.println("2. Edit Review");
            System.out.println("3. Get Average Rating of Product");
            System.out.println("4. Extract Reviews by Customer");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1: rm.addReviewInteractive(sc, pm, cm);
                case 2: rm.editReviewInteractive(sc, pm, cm);
                case 3: rm.getAverageRatingInteractive(sc, pm);
                case 4: rm.extractReviewsByCustomerInteractive(sc, cm);
                case 5: back = true;
                default: System.out.println("Invalid option!");
            }
        }
    }

    // ------------------- REPORTS MENU -------------------
    private static void reportsMenu(Scanner sc, ProductManager pm, OrderManager om, ReviewManager rm, CustomerManager cm) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- REPORTS MENU ---");
            System.out.println("1. Top 3 Products by Average Rating");
            System.out.println("2. All Orders Between Two Dates");
            System.out.println("3. Common Products Reviewed by Two Customers >4");
            System.out.println("4. Back");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1: rm.showTop3Products(pm.getProducts());
                case 2: om.showOrdersBetweenDates(sc);
                case 3: rm.showCommonReviewedProductsInteractive(sc, cm);
                case 4: back = true;
                default: System.out.println("Invalid option!");
            }
        }
    }
}
