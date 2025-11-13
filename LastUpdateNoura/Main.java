package DSProject;
import java.util.Scanner;

public class Main {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        System.out.println("=== LOADING FILES ===");

        Customer.loadFromCSV("dataset/customers.csv");
        Product.loadFromCSV("dataset/prodcuts.csv"); 
        Order.loadFromCSV("dataset/orders.csv", Customer.getCustomers(), Product.getProducts());
        Review.loadFromCSV("dataset/reviews.csv", Customer.getCustomers(), Product.getProducts());

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
        Product.saveToCSV("products.csv");
        Customer.saveToCSV("customers.csv");
        Order.saveToCSV("orders.csv");
        Review.saveToCSV("reviews.csv");
        sc.close();
        System.out.println("Exiting... All data saved!");
    }

    private static void customersMenu() {
        int choice;
        do {
            System.out.println("\n--- CUSTOMERS MENU ---");
            System.out.println("1. Register Customer");
            System.out.println("2. Place Order For Customer");
            System.out.println("3. View Customer Order History");
            System.out.println("4. Back");

            choice = readInt("Choose an option: ");

            switch (choice) {
                case 1://Register Customer
                	
                	System.out.print("Enter customer ID: "); String id = sc.nextLine();
                    System.out.print("Enter customer name: "); String name = sc.nextLine();
                    System.out.print("Enter customer email: "); String email = sc.nextLine();
                    
                    Customer c1 = new Customer(id, name, email);
                    
                    boolean customerResult = Customer.registerCustomer(c1);
                    
                    if (customerResult)
                    System.out.println("Customer registered.");
                    else
                    System.out.println("Customer couldn't register, try again.");
                	
                    break;
                    
                case 2://Place Order For Customer
                	System.out.print("Enter customer ID: "); String custID = sc.nextLine();
        		    Customer c2 = Customer.findCustomerById(custID);
        		    
        		    if (c2 == null) {
        		        System.out.println("customer not found.");
        		        return;
        		    }
        		    
        		    System.out.print("Enter order ID: "); String ordID = sc.nextLine();
        		    Order o1 = new Order(ordID, c2);
        		    
        		    boolean orderResult = Customer.placeCustomerOrder(c2, o1);
        		    if (orderResult)
        		    System.out.println("Order Created.");
        		    else
        		    System.out.println("Order is not created, try again."); 
                    break;
                    
                case 3://View Customer Order History
                	System.out.print("Enter customer ID: "); String cID = sc.nextLine();
                    Customer c = Customer.findCustomerById(cID);
                    
                    if (c == null) {
                        System.out.println("Customer not found.");
                        return;
                    }
                    Customer.viewOrderHistory(c);
                    break;
                    
                case 4://Back
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
                case 1://Create Order
                	System.out.print("Enter customer ID: "); String custID1 = sc.nextLine();
        		    Customer c1 = Customer.findCustomerById(custID1);
        		    
        		    if (c1 == null) {
        		        System.out.println("customer not found.");
        		        return;
        		    }
        		    
        		    System.out.print("Enter order ID: "); String ordID1 = sc.nextLine();
        		    Order o1 = new Order(ordID1, c1);
        		    
        		    boolean orderResult = Order.createOrder(c1, o1);
        		    if (orderResult)
        		    System.out.println("Order Created.");
        		    else
        		    System.out.println("Order is not created, try again."); 
                    break;
                    
                case 2://Cancel Order
                	
            	    System.out.print("Enter order ID to cancel: "); String ordID2 = sc.nextLine();
            	    Order o2 = Order.findOrderById(ordID2);
            	    
            	    if (o2 == null) {
            	    	System.out.println("Order not found.");
            	    	return;
            	    }
            	    
            	    Order.cancelOrder(o2);
                	System.out.println("Order Cancelled."); 
            	    
                    break;
                    
                case 3://Update Order Status
                	
                	System.out.print("Enter order ID to update: "); String ordID3 = sc.nextLine();
            	    Order o3 = Order.findOrderById(ordID3);
            	    
            	    if (o3 == null) {
            	    	System.out.println("Order not found.");
            	    	return;
            	    }
            	    
            	    System.out.print("Enter new status: "); String status = sc.nextLine();
            	    Order.updateOrderStatus(o3, status);
            	    System.out.println("Order status updated.");
                    break;
                    
                case 4://Search Order by ID
                	System.out.print("Enter order ID to search: "); String oid = sc.nextLine();
            	    Order o4 = Order.findOrderById(oid);
            	    
            	    if (o4 == null) {
            	    	System.out.println("Order not found.");
            	    	return;
            	    }
            	    
            	    System.out.println(o4.toString());
                    break;
                    
                case 5://Back
                    System.out.println("Returning to main menu...");
                    break;
                    
                default:
                    System.out.println("Invalid option!");
            }
        } while (choice != 5);
    }
    
    private static void productsMenu() {
        int choice;
        do {
            System.out.println("\n--- PRODUCTS MENU ---");
            System.out.println("1. Add Product");
            System.out.println("2. Remove Product");
            System.out.println("3. Update Product");
            System.out.println("4. Search Product By ID Or Name");
            System.out.println("5. Track Out-of-Stock");
            System.out.println("6. Back");

            choice = readInt("Choose an option: ");

            switch (choice) {
                case 1://Add Product
                    
                    System.out.print("Enter product ID: "); String proID = sc.nextLine();
                    System.out.print("Enter product name: "); String proName = sc.nextLine();
                    System.out.print("Enter product price: "); double proPrice = sc.nextDouble(); sc.nextLine();
                    System.out.print("Enter product stock: "); int proStock = sc.nextInt(); sc.nextLine();

                    Product p1 = new Product(proID, proName, proPrice, proStock);
                    
                    boolean productResult = Product.addProduct(p1);
                    if (productResult)
                    System.out.println("Product Created.");
                    else
                    System.out.println("Product is not created, try again."); 
                
                    break;
                case 2://Remove Product
                	
                    System.out.print("Enter product ID to remove: "); String removeId = sc.nextLine();
                    
                    Product p2 = Product.findProductById(removeId);
                                
                    if (p2 == null) {
                        System.out.println("product not found.");
                        return;
                    }
                    
                    boolean removetResult = Product.removeProductById(p2);
                    if (removetResult)
                        System.out.println("product removed successfuly");
                        else
                        System.out.println("Product is not removed, try again."); 
                    break;
                    
                case 3://Update Product
                	 System.out.print("Enter product ID to update: "); String updateId = sc.nextLine();
                     Product p3 = Product.findProductById(updateId);
                                
                    if (p3 == null) {
                        System.out.println("product not found.");
                        return;
                    }
                     
                     System.out.print("Enter new name: "); String newName = sc.nextLine();
                     System.out.print("Enter new price: "); double newPrice = sc.nextDouble(); sc.nextLine();
                     System.out.print("Enter new stock: "); int newStock = sc.nextInt(); sc.nextLine();
                     
                     boolean updateResult = Product.updateProduct(updateId, newName, newPrice, newStock);
                     if (updateResult)
                         System.out.println("product updated.");
                         else
                         System.out.println("Product is not updated, try again."); 
                	break;
                	
                case 4://Search Product By ID Or Name
                	System.out.print("Enter product ID or name to search: "); String keyword = sc.nextLine();
        	    	Product p4 = Product.searchProduct(keyword);

            	    
            	    if (p4 != null) {
                	    System.out.println(p4.toString());
            	    }
            	    else
            	    	System.out.println("Product not found.");
                    break;
                    
                case 5://Track Out-of-Stock
                    System.out.println("--- Out of Stock Products ---");
                    Product.trackOutOfStock();
                    break;
                    
                case 6://Back
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        } while (choice != 6);
    }

    private static void reviewsMenu() {
        int choice;
        do {
            System.out.println("\n--- REVIEWS MENU ---");
            System.out.println("1. Add Review");
            System.out.println("2. Edit Review");
            System.out.println("3. Get Average Rating of Product");
            System.out.println("4. Back");

            choice = readInt("Choose an option: ");

            switch (choice) {
                case 1://Add Review
                	System.out.print("Enter customer ID: "); String custID1 = sc.nextLine();
        		    Customer c1 = Customer.findCustomerById(custID1);
        		    
        		    if (c1 == null) {
        		        System.out.println("customer not found.");
        		        return;
        		    }
                	
                	System.out.print("Enter product ID: "); String proID1 = sc.nextLine();
        		    Product p1 = Product.findProductById(proID1);
        		    
        		    if (p1 == null) {
        		        System.out.println("product not found.");
        		        return;
        		    }
        		            		    
        		    System.out.print("Enter review ID: "); String revID1 = sc.nextLine();
        		    System.out.print("Enter product review rating: "); int rating = sc.nextInt(); sc.nextLine();
        		    System.out.print("Enter product review comment: "); String comment = sc.nextLine();
        		    Review r1 = new Review(revID1, p1, c1, rating, comment);
        		    
        		    boolean reviewResult = Review.addReview(p1, r1);
        		    if (reviewResult)
        		    System.out.println("Review added.");
        		    else
        		    System.out.println("Review is not added, try again."); 
                    break;
                    
                case 2://Edit Review
                	     
                	    System.out.print("Enter review ID: "); String revID2 = sc.nextLine();
                	    
                	    Review r2 = Review.findReviewById(revID2);
                	                
                	    if (r2 == null) {
                	        System.out.println("review not found.");
                	        return;
                	    }
                	    
                	    System.out.print("Enter new rating (1-5): "); int rating2 = sc.nextInt(); sc.nextLine();
                	    System.out.print("Enter new comment: "); String comment2 = sc.nextLine();
                	    
                	    boolean editResult = Review.editReview(revID2, rating2, comment2);
                        if (editResult)
                            System.out.println("Review edited.");
                            else
                            System.out.println("Review is not edited, try again."); 
                	break;
                    
                case 3://Get Average Rating of Product
                	System.out.print("Enter product ID: "); String proID2 = sc.nextLine();
            	    Product p2 = Product.findProductById(proID2);
            	    
            	    if (p2 == null) {
            	        System.out.println("product not found.");
            	        return;
            	    }
            	    System.out.println("Average rating for " + p2.getName() + ": " + Review.getProductAverageRating(p2));
                    break;
                    
                case 4://Back
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        } while (choice != 4);
    }

    private static void reportsMenu() {
        int choice;
        do {
            System.out.println("\n--- REPORTS MENU ---");
            System.out.println("1. Extract Reviews by Customer");
            System.out.println("2. Top 3 Products by Average Rating");
            System.out.println("3. All Orders Between Two Dates");
            System.out.println("4. Common Products Reviewed by Two Customers > 4");
            System.out.println("5. Back");

            choice = readInt("Choose an option: ");

            switch (choice) {
                case 1://Extract Reviews by Customer
                	System.out.print("Enter customer ID: "); String custID1 = sc.nextLine();
        		    Customer c = Customer.findCustomerById(custID1);
        		    
        		    if (c == null) {
        		        System.out.println("customer not found.");
        		        return;
        		    }
        		    Review.displayReviewsByCustomer(c);
                    break;
                    
                case 2://Top 3 Products by Average Rating
                	Review.showTop3Products(Product.getProducts());
                    break;
                    
                case 3://All Orders Between Two Dates
                        System.out.print("Enter start date (yyyy-MM-dd): ");
                        String start = sc.nextLine().trim();
                        System.out.print("Enter end date (yyyy-MM-dd): ");
                        String end = sc.nextLine().trim();

                        Order.showOrdersBetweenDates(start, end);

                    break;
                    
                case 4://Common Products Reviewed by Two Customers > 4
                	System.out.print("Enter first customer ID: ");
                    String c1 = sc.nextLine().trim();
                    
                    System.out.print("Enter second customer ID: ");
                    String c2 = sc.nextLine().trim();
                    
                    Review.showCommonReviewedProducts(c1, c2);
                    break;
                    
                case 5://Back
                    System.out.println("Returning to main menu...");
                    break;
                    
                default:
                    System.out.println("Invalid option!");
            }
        } while (choice != 5);
    }
}