import java.util.Scanner;

public class Main {

    public static Scanner sc = new Scanner(System.in);

    public static ProductManager pm = new ProductManager();
    public static CustomerManager cm = new CustomerManager();
    public static OrderManager om = new OrderManager();
    public static ReviewManager rm = new ReviewManager();

    public static void main(String[] args) throws Exception {

        System.out.println("=== LOADING FILES ===");

        cm.loadFromCSV("dataset/customers.csv");
        pm.loadFromCSV("dataset/prodcuts.csv"); 
        om.loadFromCSV("dataset/orders.csv", cm.getCustomers(), pm.getProducts());
        rm.loadFromCSV("dataset/reviews.csv", cm.getCustomers(), pm.getProducts());

        int mainChoice;
        do {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Customers Menu");
            System.out.println("2. Orders Menu");
            System.out.println("3. Products Menu");
            System.out.println("4. Reviews Menu");
            System.out.println("5. Reports Menu");
            System.out.println("6. Exit");

            mainChoice = readInt("Choose an option: ");

            switch (mainChoice) {
                case 1:
                    customersMenu();
                    break;
                case 2:
                    ordersMenu();
                    break;
                case 3:
                    productsMenu();
                    break;
                case 4:
                    reviewsMenu();
                    break;
                case 5:
                    reportsMenu();
                    break;
                case 6:
                    exitProgram();
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        } while (mainChoice != 6);
    }


    public static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, please try again.");
            }
        }
    }

    public static void exitProgram() throws Exception {
        System.out.println("Saving data before exiting...");
        pm.saveToCSV("products.csv");
        cm.saveToCSV("customers.csv");
        om.saveToCSV("orders.csv");
        rm.saveToCSV("reviews.csv");
        sc.close();
        System.out.println("Exiting... All data saved!");
    }

    private static void productsMenu() {
        int choice;
        do {
            System.out.println("\n--- PRODUCTS MENU ---");
            System.out.println("1. Add Product");
            System.out.println("2. Remove Product");
            System.out.println("3. Update Product");
            System.out.println("4. Search Product");
            System.out.println("5. Track Out-of-Stock");
            System.out.println("6. Back");

            choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    pm.createProduct(sc);    
                    break;
                case 2:
                    pm.removeProductById(sc);
                    break;
                case 3:
                    pm.updateProduct(sc);
                    break;
                case 4:
                    pm.searchProduct(sc);
                    break;
                case 5:
                    pm.trackOutOfStock();
                    break;
                case 6:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        } while (choice != 6);
    }

    private static void customersMenu() {
        int choice;
        do {
            System.out.println("\n--- CUSTOMERS MENU ---");
            System.out.println("1. Register Customer");
            System.out.println("2. View Customers");
            System.out.println("3. View Customer Order History");
            System.out.println("4. Back");

            choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    cm.createCustomer(sc, pm, cm);
                    break;
                case 2:
                    cm.displayAllCustomers();
                    break;
                case 3:
                    cm.viewOrderHistory(sc);
                    break;
                case 4:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        } while (choice != 4);
    }

    private static void ordersMenu() {
        int choice;
        do {
            System.out.println("\n--- ORDERS MENU ---");
            System.out.println("1. Create Order");
            System.out.println("2. Cancel Order");
            System.out.println("3. Update Order Status");
            System.out.println("4. Search Order by ID");
            System.out.println("5. Back");

            choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    om.createOrder(sc, cm);
                    break;
                case 2:
                    om.cancelOrder(sc);
                    break;
                case 3:
                    om.updateOrderStatus(sc);
                    break;
                case 4:
                    om.searchOrderById(sc);
                    break;
                case 5:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        } while (choice != 5);
    }

    private static void reviewsMenu() {
        int choice;
        do {
            System.out.println("\n--- REVIEWS MENU ---");
            System.out.println("1. Add Review");
            System.out.println("2. Edit Review");
            System.out.println("3. Get Average Rating of Product");
            System.out.println("4. Extract Reviews by Customer");
            System.out.println("5. Back");

            choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    rm.createReview(sc, cm, pm);
                    break;
                case 2:
                    rm.editReview(sc, pm, cm, rm);
                    break;
                case 3:
                    rm.AverageRating(sc, pm);
                    break;
                case 4:
                    rm.displayReviewsByCustomer(sc, cm);
                    break;
                case 5:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        } while (choice != 5);
    }

    private static void reportsMenu() {
        int choice;
        do {
            System.out.println("\n--- REPORTS MENU ---");
            System.out.println("1. Top 3 Products by Average Rating");
            System.out.println("2. All Orders Between Two Dates");
            System.out.println("3. Common Products Reviewed by Two Customers > 4");
            System.out.println("4. Back");

            choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    rm.showTop3Products();
                    break;
                case 2:
                    om.showOrdersBetweenDates(sc);
                    break;
                case 3:
                    System.out.print("Enter first customer ID: ");
                    String c1 = sc.nextLine().trim();
                    System.out.print("Enter second customer ID: ");
                    String c2 = sc.nextLine().trim();
                    rm.showCommonReviewedProducts(c1, c2);
                    break;
                case 4:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        } while (choice != 4);
    }
}