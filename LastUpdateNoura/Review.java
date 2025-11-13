package DSProject;
import java.io.*;

public class Review {
    private String reviewId;
    private Product product;
    private Customer customer;
    private int rating;
    private String comment;
    
    private static LinkedList<Review> reviews = new LinkedList<Review>();

    public Review(String reviewId, Product product, Customer customer, int rating, String comment) {
        this.reviewId = reviewId;
        this.product = product;
        this.customer = customer;
        this.rating = rating;
        this.comment = comment;
    }
    
    public static void loadFromCSV(String fileName, LinkedList<Customer> customers, LinkedList<Product> products) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        br.readLine(); // skip header
        
        int count = 0;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 5) {
                String reviewId = parts[0];
                String productId = parts[1];
                String customerId = parts[2];
                int rating = Integer.parseInt(parts[3]);
                String comment = parts[4];

                // Find product and customer
                Product product = null;
                Customer customer = null;
                
                Node<Product> pNode = products.getHead();
                while (pNode != null) {
                    if (pNode.data.getProductId().equals(productId)) {
                        product = pNode.data;
                        break;
                    }
                    pNode = pNode.next;
                }
                
                Node<Customer> cNode = customers.getHead();
                while (cNode != null) {
                    if (cNode.data.getCustomerId().equals(customerId)) {
                        customer = cNode.data;
                        break;
                    }
                    cNode = cNode.next;
                }

                if (product != null && customer != null) {
                    Review review = new Review(reviewId, product, customer, rating, comment);
                    reviews.insert(review);
                    product.getReviews().insert(review);
                    count++;
                }
            }
        }
        br.close();
        System.out.println("✅ Loaded " + count + " reviews");
    }
    
    public static void saveToCSV(String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write("reviewId,productId,customerId,rating,comment");
        bw.newLine();
        Node<Review> current = reviews.getHead();
        while (current != null) {
            bw.write(current.data.toCSV());
            bw.newLine();
            current = current.next;
        }
        bw.close();
    }
    
	public static boolean addReview(Product p, Review r) {
	    boolean result = Product.placeProductreview(p, r);
	    return result;
	}
	
	public static Review findReviewById(String id) {
	    Node<Review> current = reviews.getHead();
	    while (current != null) {
	        if (current.data.getReviewId().equals(id))
	        return current.data;
	        
	        current = current.next;
	    }
	    return null;
	}
	
	public static boolean editReview(String revID, int rating, String comment) {
		boolean edited = false;
	    reviews.findfirst();
	    while (!reviews.last()) {
	        if (Review.getReviews().retrieve().getReviewId().equals(revID))
	            reviews.getCurrent().data.setRating(rating);
	            reviews.getCurrent().data.setComment(comment);
	            edited = true;
	        
	        reviews.findnext();
	    }
	    if (Review.getReviews().retrieve().getReviewId().equals(revID))
            reviews.getCurrent().data.setRating(rating);
            reviews.getCurrent().data.setComment(comment);
            edited = true;
            
        return edited;
	}
	
	public static double getProductAverageRating(Product p) {
	    return p.getAverageRating();
	}

	public static LinkedList<Review> extractReviewsByCustomer(String customerId) {
	    LinkedList<Review> customerReviews = new LinkedList<>();
	        
	        if (reviews == null || reviews.empty()) {
	            return customerReviews;
	        }

	        Node<Review> currentNode = reviews.getHead();
	        
	        while (currentNode != null) {
	            Review currentReview = currentNode.data;
	            
	            if (currentReview.getCustomer().getCustomerId().equals(customerId)) {
	                customerReviews.insert(currentReview);
	            }
	            
	            currentNode = currentNode.next;
	        }
	        
	        return customerReviews;
	    }

	    public static void displayReviewsByCustomer(Customer c) {
	            
	        LinkedList<Review> customerReviews = extractReviewsByCustomer(c.getCustomerId());
	        
	        if (customerReviews.empty()) {
	            System.out.println("No reviews found for customer ID: " + c.getCustomerId());
	            return;
	        }

	        System.out.println("=== REVIEWS BY CUSTOMER: " + c.getCustomerId() + " ===");
	        System.out.println("Total Reviews: " + customerReviews.getSize());
	        
	        Node<Review> currentNode = customerReviews.getHead();
	        int reviewNumber = 1;
	        
	        while (currentNode != null) {
	            Review currentReview = currentNode.data;
	            System.out.println("\nReview #" + reviewNumber + ":");
	            System.out.println("  Product: " + currentReview.getProduct().getName());
	            System.out.println("  Rating: " + currentReview.getRating() + "/5");
	            System.out.println("  Comment: " + currentReview.getComment());
	            System.out.println("  Review ID: " + currentReview.getReviewId());
	            System.out.println("------------------------");
	            
	            currentNode = currentNode.next;
	            reviewNumber++;
	        }
	    }
	    
	    public static void showTop3Products(LinkedList<Product> productList) {
	        if (productList.getHead() == null) {
	            System.out.println("No products found.");
	            return;
	        }

	        HeapPQ topProducts = new HeapPQ(3);

	        Node<Product> current = productList.getHead();
	        while (current != null) {
	            Product p = current.data;
	            topProducts.insert(p);
	            current = current.next;
	        }

	        Product[] results = topProducts.getAll();
	        int size = topProducts.getSize();

	        for (int i = 0; i < size - 1; i++) {
	            for (int j = i + 1; j < size; j++) {
	                if (results[j].getAverageRating() > results[i].getAverageRating()) {
	                    Product temp = results[i];
	                    results[i] = results[j];
	                    results[j] = temp;
	                }
	            }
	        }

	        System.out.println("TOP 3 PRODUCTS BY RATING");
	        System.out.println("=========================");
	        for (int i = 0; i < size; i++) {
	            Product p = results[i];
	            System.out.println((i + 1) + ". " + p.getName() + " | Rating: " + p.getAverageRating());
	        }
	    }

	    
	    public static void showCommonReviewedProducts(String customerId1, String customerId2) {
	        LinkedList<Product> commonProducts = new LinkedList<>();

	        System.out.println("=== SEARCHING FOR COMMON PRODUCTS ===");
	        
	        Node<Product> allProducts = Product.getProducts().getHead();
	        while (allProducts != null) {
	            String productId = allProducts.data.getProductId();
	            
	            double avg1 = calculateCustomerProductRating(customerId1, productId);
	            double avg2 = calculateCustomerProductRating(customerId2, productId);
	            
	            if (avg1 >= 4.0 && avg2 >= 4.0) {
	                System.out.println("FOUND: " + productId + " - " + customerId1 + "=" + avg1 + ", " + customerId2 + "=" + avg2);
	                
	                boolean exists = false;
	                Node<Product> checkNode = commonProducts.getHead();
	                while (checkNode != null) {
	                    if (checkNode.data.getProductId().equals(productId)) {
	                        exists = true;
	                        break;
	                    }
	                    checkNode = checkNode.next;
	                }
	                
	                if (!exists) {
	                    commonProducts.insert(allProducts.data);
	                }
	            }
	            
	            allProducts = allProducts.next;
	        }
	        
	        if (commonProducts.empty()) {
	            System.out.println("No common products found with rating >= 4.0 for both customers.");
	            return;
	        }

	        System.out.println("=== COMMON PRODUCTS WITH RATING >= 4.0 ===");
	        System.out.println("Customers: " + customerId1 + " and " + customerId2);
	        System.out.println("Total: " + commonProducts.getSize() + " product(s)");
	        System.out.println("----------------------------------------");

	        Node<Product> node = commonProducts.getHead();
	        int counter = 1;
	        while (node != null) {
	            Product p = node.data;
	            double avg1 = calculateCustomerProductRating(customerId1, p.getProductId());
	            double avg2 = calculateCustomerProductRating(customerId2, p.getProductId());

	            System.out.println(counter + ". " + p.getName());
	            System.out.println("   Product ID: " + p.getProductId());
	            System.out.printf("   %s Rating: %.1f/5.0\n", customerId1, avg1);
	            System.out.printf("   %s Rating: %.1f/5.0\n", customerId2, avg2);
	            System.out.println("----------------------------------------");

	            node = node.next;
	            counter++;
	        }
	    }
	    
	    private static  double calculateCustomerProductRating(String customerId, String productId) {
	        int totalRating = 0;
	        int reviewCount = 0;
	        
	        Node<Review> currentReview = reviews.getHead();
	        while (currentReview != null) {
	            Review review = currentReview.data;
	            if (review.getCustomer().getCustomerId().equals(customerId) && 
	                review.getProduct().getProductId().equals(productId)) {
	                totalRating += review.getRating();
	                reviewCount++;
	            }
	            currentReview = currentReview.next;
	        }
	        
	        return reviewCount > 0 ? (double) totalRating / reviewCount : 0.0;
	    }
	    
    //Setters & Getters
	public void setRating(int rating) { this.rating = rating; }
	public void setComment(String comment) { this.comment = comment; }    
	    
    public String getReviewId() { return reviewId; }
    public Product getProduct() { return product; }
    public Customer getCustomer() { return customer; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public static LinkedList<Review> getReviews() { return reviews; }
    
    public String toString() {
        return String.format("Review[ID: %s, Product: %s, Customer: %s, Rating: %d/5, Comment: %s]", 
                            reviewId, product.getName(), customer.getName(), rating, 
                            comment.length() > 30 ? comment.substring(0, 30) + "..." : comment);
    }

    public String toCSV() {
        return reviewId + "," + product.getProductId() + "," + customer.getCustomerId() + "," + rating + "," + comment;
    }
}
