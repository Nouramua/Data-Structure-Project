import java.io.*;
import java.util.Scanner;

public class ReviewManager {
    private LinkedList<Review> reviews;

    public ReviewManager() {
        reviews = new LinkedList<>();
    }

    public void loadFromCSV(String fileName, LinkedList<Customer> customers, LinkedList<Product> products) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        br.readLine(); 
        int count = 0;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 5) {
                String reviewId = parts[0].trim();
                String productId = parts[1].trim();
                String customerId = parts[2].trim();
                int rating = Integer.parseInt(parts[3].trim());
                String comment = parts[4].trim();

                if (rating < 1 || rating > 5) {
                    continue;
                }

                Product product = null;
                Node<Product> pNode = products.getHead();
                while (pNode != null) {
                    if (pNode.data.getProductId().equals(productId)) {
                        product = pNode.data;
                        break;
                    }
                    pNode = pNode.next;
                }

                Customer customer = null;
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
                    addReviewFromCSV(review); 
                    count++;
                }
            }
        }
        br.close();
        System.out.println("Loaded " + count + " reviews");
    }

   
    private void addReviewFromCSV(Review r) {
        reviews.insert(r);

        if (r.getProduct() != null && r.getProduct().getListOfReviews() != null) {
            r.getProduct().getListOfReviews().insert(r);
        }
    }


    public boolean addReview(Review r) {
        Node<Review> tmp = reviews.getHead();
        while (tmp != null) {
            if (r.getReviewId().equals(tmp.data.getReviewId())) {
                return false;
            }
            tmp = tmp.next;
        }

        reviews.insert(r);

        if (r.getProduct() != null && r.getProduct().getListOfReviews() != null) {
            r.getProduct().getListOfReviews().insert(r);
        }

        return true;
    }

    public void createReview(Scanner sc, CustomerManager cm, ProductManager pm) {
        System.out.print("Enter customer ID: ");
        String custID = sc.nextLine().trim();
        Customer c = cm.findCustomerById(custID);
        if (c == null) {
            System.out.println("Uh Oh, Customer not found.");
            return;
        }

        System.out.print("Enter product ID: ");
        String proID = sc.nextLine().trim();
        Product p = pm.findProductById(proID);
        if (p == null) {
            System.out.println("Uh Oh, Product not found.");
            return;
        }

        System.out.print("Enter review ID: ");
        String revID = sc.nextLine().trim();
        if (revID.isEmpty()) {
            System.out.println("Uh Oh, Review ID is required.");
            return;
        }

        System.out.print("Enter review rating (1-5): ");
        String rateInput = sc.nextLine().trim();
        int revRate;
        try {
            revRate = Integer.parseInt(rateInput);
        } catch (NumberFormatException e) {
            System.out.println("Uh Oh, Invalid rating. Must be a number between 1 and 5.");
            return;
        }

        if (revRate < 1 || revRate > 5) {
            System.out.println("Uh Oh, Rating must be between 1 and 5.");
            return;
        }

        System.out.print("Enter review comment: ");
        String revCom = sc.nextLine().trim();

        Review r = new Review(revID, p, c, revRate, revCom);
        boolean result = addReview(r);

        if (result) {
            System.out.println("Review created!");
        } else {
            System.out.println("Uh Oh, Review ID already exists. Review not created.");
        }
    }

    public void editReview(Scanner sc, ProductManager pm, CustomerManager cm, ReviewManager rm) {
        System.out.print("Enter customer ID: ");
        String custID = sc.nextLine().trim();
        Customer c = cm.findCustomerById(custID);
        if (c == null) {
            System.out.println("Uh Oh, Customer not found.");
            return;
        }

        System.out.print("Enter product ID: ");
        String proID = sc.nextLine().trim();
        Product p = pm.findProductById(proID);
        if (p == null) {
            System.out.println("Uh Oh, Product not found.");
            return;
        }

        System.out.print("Enter review ID: ");
        String revID = sc.nextLine().trim();
        Review r = rm.findReviewById(revID);
        if (r == null) {
            System.out.println("Uh Oh, Review not found.");
            return;
        }

        if (!r.getCustomer().getCustomerId().equals(custID) ||
            !r.getProduct().getProductId().equals(proID)) {
            System.out.println("Uh Oh, This review does not match the given customer/product.");
            return;
        }

        System.out.print("Enter new rating (1-5): ");
        String rateInput = sc.nextLine().trim();
        int rating;
        try {
            rating = Integer.parseInt(rateInput);
        } catch (NumberFormatException e) {
            System.out.println("Uh Oh, Invalid rating.");
            return;
        }
        if (rating < 1 || rating > 5) {
            System.out.println("Uh Oh, Rating must be between 1 and 5.");
            return;
        }

        System.out.print("Enter new comment: ");
        String comment = sc.nextLine().trim();

        reviews.findfirst();
        while (reviews.getCurrent() != null) {
            if (reviews.getCurrent().data.getReviewId().equals(revID)) {
                reviews.getCurrent().data.setRating(rating);
                reviews.getCurrent().data.setComment(comment);
                System.out.println("Review updated!");
                return;
            }
            reviews.findnext();
        }
    }

    public Review findReviewById(String id) {
        Node<Review> current = reviews.getHead();
        while (current != null) {
            if (current.data.getReviewId().equals(id)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public void AverageRating(Scanner sc, ProductManager pm) {
        System.out.print("Enter product ID: ");
        String proID = sc.nextLine().trim();

        Product p = pm.findProductById(proID);
        if (p == null) {
            System.out.println("Product not found.");
            return;
        }
        System.out.println("Average rating: " + String.format("%.2f", p.getAverageRating()));
    }

    public LinkedList<Review> extractReviewsByCustomer(String customerId) {
        LinkedList<Review> customerReviews = new LinkedList<>();

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

    public void displayReviewsByCustomer(Scanner sc, CustomerManager cm) {
        System.out.print("Enter customer ID: ");
        String custID = sc.nextLine().trim();

        Customer c = cm.findCustomerById(custID);
        if (c == null) {
            System.out.println("Uh Oh, Customer not found.");
            return;
        }

        LinkedList<Review> customerReviews = extractReviewsByCustomer(custID);
        if (customerReviews.empty()) {
            System.out.println("No reviews found for customer ID: " + custID);
            return;
        }

        System.out.println("=== REVIEWS BY CUSTOMER: " + custID + " ===");
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

    public void showCommonReviewedProducts(String customerId1, String customerId2) {
        LinkedList<Product> uniqueProducts = new LinkedList<>();
        LinkedList<Product> commonProducts = new LinkedList<>();

        Node<Review> currentReview = reviews.getHead();
        while (currentReview != null) {
            Product product = currentReview.data.getProduct();
            boolean exists = false;
            Node<Product> cur = uniqueProducts.getHead();
            while (cur != null) {
                if (cur.data.getProductId().equals(product.getProductId())) {
                    exists = true;
                    break;
                }
                cur = cur.next;
            }
            if (!exists) {
                uniqueProducts.insert(product);
            }
            currentReview = currentReview.next;
        }

        Node<Product> currentProduct = uniqueProducts.getHead();
        while (currentProduct != null) {
            Product product = currentProduct.data;
            String productId = product.getProductId();

            double avg1 = calculateCustomerProductRating(customerId1, productId);
            double avg2 = calculateCustomerProductRating(customerId2, productId);

            if (avg1 > 4.0 && avg2 > 4.0) {
                commonProducts.insert(product);
            }
            currentProduct = currentProduct.next;
        }

        if (commonProducts.empty()) {
            System.out.println("No common products found with average rating > 4.0 for both customers.");
            return;
        }

        System.out.println("=== COMMON PRODUCTS WITH RATING > 4.0 ===");
        System.out.println("Customers: " + customerId1 + " and " + customerId2);
        System.out.println("Total: " + commonProducts.getSize() + " product(s)");
        System.out.println("----------------------------------------");

        Node<Product> resultProduct = commonProducts.getHead();
        int counter = 1;
        while (resultProduct != null) {
            Product product = resultProduct.data;
            double avg1 = calculateCustomerProductRating(customerId1, product.getProductId());
            double avg2 = calculateCustomerProductRating(customerId2, product.getProductId());

            System.out.println(counter + ". " + product.getName());
            System.out.println("   Product ID: " + product.getProductId());
            System.out.printf("   %s Rating: %.1f/5.0\n", customerId1, avg1);
            System.out.printf("   %s Rating: %.1f/5.0\n", customerId2, avg2);
            System.out.println("----------------------------------------");

            resultProduct = resultProduct.next;
            counter++;
        }
    }

    private double calculateCustomerProductRating(String customerId, String productId) {
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

    public void showTop3Products() {
        LinkedList<Product> uniqueProducts = new LinkedList<>();

        Node<Review> current = reviews.getHead();
        while (current != null) {
            Product p = current.data.getProduct();
            boolean exists = false;
            Node<Product> temp = uniqueProducts.getHead();
            while (temp != null) {
                if (temp.data.getProductId().equals(p.getProductId())) {
                    exists = true;
                    break;
                }
                temp = temp.next;
            }
            if (!exists) uniqueProducts.insert(p);
            current = current.next;
        }

        if (uniqueProducts.empty()) {
            System.out.println("No products with reviews found.");
            return;
        }

        Product[] top3 = new Product[3];
        double[] topRatings = new double[3];

        Node<Product> productNode = uniqueProducts.getHead();
        while (productNode != null) {
            double rating = calculateProductAverageRating(productNode.data);

            for (int i = 0; i < 3; i++) {
                if (top3[i] == null || rating > topRatings[i]) {
                    for (int j = 2; j > i; j--) {
                        top3[j] = top3[j - 1];
                        topRatings[j] = topRatings[j - 1];
                    }
                    top3[i] = productNode.data;
                    topRatings[i] = rating;
                    break;
                }
            }
            productNode = productNode.next;
        }

        System.out.println("TOP 3 PRODUCTS BY RATING");
        System.out.println("============================");
        for (int i = 0; i < 3 && top3[i] != null; i++) {
            System.out.println((i + 1) + ". " + top3[i].getName());
            System.out.println("   Rating: " + String.format("%.1f", topRatings[i]) + "/5.0");
            System.out.println("   ID: " + top3[i].getProductId());
            System.out.println("   Price: $" + top3[i].getPrice());
            System.out.println("----------------------------");
        }
    }

    private double calculateProductAverageRating(Product product) {
        int total = 0, count = 0;
        Node<Review> current = reviews.getHead();
        while (current != null) {
            if (current.data.getProduct().getProductId().equals(product.getProductId())) {
                total += current.data.getRating();
                count++;
            }
            current = current.next;
        }
        return count > 0 ? (double) total / count : 0.0;
    }

    public void saveToCSV(String fileName) throws IOException {
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

    public LinkedList<Review> getReviews() {
        return reviews;
    }
}