import java.util.Date;  
public class Main {
    public static void main(String[] args) {

        Product p1 = new Product(1, "iPhone 15", 4000, 10);
        Product p2 = new Product(2, "Galaxy S24", 3500, 8);
        Product p3 = new Product(3, "Pixel 9", 3200, 6);

        Product[] products = {p1, p2, p3};

        p1.addReview(new Review(101, 5, "Excellent phone!"));
        p1.addReview(new Review(102, 4, "Good but pricey."));
        p2.addReview(new Review(101, 5, "Amazing camera!"));
        p3.addReview(new Review(103, 5, "Love it!"));
        p3.addReview(new Review(102, 5, "Perfect design."));

        ReviewManager.showReviewsByCustomer(products, 101);
        ReviewManager.showTop3Products(products);
        ReviewManager.showCommonReviewedProducts(products, 101, 102);


         System.out.println("\n--- ORDER MANAGEMENT ---");
        
        OrderManager orderManager = new OrderManager();
        
        LinkedList<Product> order1Products = new LinkedList<>();
        order1Products.add(p1);
        order1Products.add(p2);
        Order order1 = orderManager.createOrder("CUST101", order1Products);
        
        LinkedList<Product> order2Products = new LinkedList<>();
        order2Products.add(p3);
        Order order2 = orderManager.createOrder("CUST102", order2Products);

        System.out.println("\n--- SEARCH ORDER BY ID ---");
        Order foundOrder = orderManager.searchOrderById("ORD0001");
        if (foundOrder != null) {
            System.out.println("Found: " + foundOrder);
        }

        System.out.println("\n--- ORDERS BETWEEN DATES ---");
        Date today = new Date();
        Date tomorrow = new Date(today.getTime() + (24 * 60 * 60 * 1000));
        LinkedList<Order> dateOrders = orderManager.getAllOrdersBetweenDates(today, tomorrow);

        System.out.println("\n--- ALL ORDERS ---");
        orderManager.displayAllOrders();

        System.out.println("\n--- ORDER CANCELLATION ---");
        orderManager.cancelOrder("ORD0002");

        System.out.println("\n--- STATUS UPDATE ---");
        orderManager.updateStatus("ORD0001", "shipped");

        System.out.println("\n--- FINAL ORDERS ---");
        orderManager.displayAllOrders();
    
    }
}//End Class
