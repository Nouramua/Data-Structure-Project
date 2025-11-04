public class Main {
    public static void main(String[] args) {
        loadData(); // Load all CSVs once
        showMainMenu(); // Start menu system
        saveData(); // Save any changes before exit 
    }

        public static void showMainMenu() {

    while (true) {
    System.out.println("\n=== MAIN MENU ===");
    System.out.println("1. Manage Customers");
    System.out.println("2. Manage Orders");
    System.out.println("3. Manage Products");
    System.out.println("4. Manage Reviews");
    System.out.println("5. Exit");
    
    int choice = getIntInput("Choose an option: ");
    
    switch (choice) {
        case 1: CustomerManager.showMenu();
        case 2: OrderManager.showMenu();
        case 3: ProductManager.showMenu();
        case 4: ReviewManager.showMenu();
        case 5: System.exit(0);
        default: System.out.println("Invalid option.");
    }
}
}


    public static void loadData() {
        CustomerManager.loadFromCSV("customers.csv");
        ProductManager.loadFromCSV("products.csv");
        OrderManager.loadFromCSV("orders.csv");
        ReviewManager.loadFromCSV("reviews.csv");
    }

    public static void saveData() {
        CustomerManager.saveToCSV("customers.csv");
        ProductManager.saveToCSV("products.csv");
        OrderManager.saveToCSV("orders.csv");
        ReviewManager.saveToCSV("reviews.csv");
    }
}
