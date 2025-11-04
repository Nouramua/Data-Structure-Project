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
        br.readLine(); // skip header
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",", -1);
            int reviewId = Integer.parseInt(parts[0]);
            int productId = Integer.parseInt(parts[1]);
            int customerId = Integer.parseInt(parts[2]);
            int rating = Integer.parseInt(parts[3]);
            String comment = parts[4];

            Product product = null;
            LinkedList.Node<Product> pNode = products.getHead();
            while (pNode != null) {
                if (pNode.data.getProductId() == productId) {
                    product = pNode.data;
                    break;
                }
                pNode = pNode.next;
            }

            Customer customer = null;
            LinkedList.Node<Customer> cNode = customers.getHead();
            while (cNode != null) {
                if (cNode.data.getCustomerId() == customerId) {
                    customer = cNode.data;
                    break;
                }
                cNode = cNode.next;
            }

            if (product != null && customer != null) {
                Review review = new Review(reviewId, product, customer, rating, comment);
                reviews.insert(review);
                product.getReviews().insert(review);
            }
        }
        br.close();
    }

    public void addReviewInteractive(Scanner sc, ProductManager pm, CustomerManager cm) {
    System.out.print("Enter customer ID: "); int cid = sc.nextInt(); sc.nextLine();
    Customer c = cm.findCustomerById(cid);
    if (c == null) { System.out.println("Customer not found."); return; }

    System.out.print("Enter product ID: "); int pid = sc.nextInt(); sc.nextLine();
    Product p = pm.findProductById(pid);
    if (p == null) { System.out.println("Product not found."); return; }

    System.out.print("Enter rating (1-5): "); int rating = sc.nextInt(); sc.nextLine();
    System.out.print("Enter comment: "); String comment = sc.nextLine();

    Review r = new Review(cid, pid, rating, comment);
    reviews.insert(r);
    System.out.println("Review added.");
}

public void editReviewInteractive(Scanner sc, ProductManager pm, CustomerManager cm) {
    System.out.print("Enter customer ID: "); int cid = sc.nextInt(); sc.nextLine();
    System.out.print("Enter product ID: "); int pid = sc.nextInt(); sc.nextLine();

    LinkedList.Node<Review> current = reviews.getHead();
    while (current != null) {
        Review r = current.data;
        if (r.getCustomerId() == cid && r.getProductId() == pid) {
            System.out.print("Enter new rating (1-5): "); int rating = sc.nextInt(); sc.nextLine();
            System.out.print("Enter new comment: "); String comment = sc.nextLine();
            r.setRating(rating);
            r.setComment(comment);
            System.out.println("Review updated.");
            return;
        }
        current = current.next;
    }
    System.out.println("Review not found.");
}

public void getAverageRatingInteractive(Scanner sc, ProductManager pm) {
    System.out.print("Enter product ID: "); int pid = sc.nextInt(); sc.nextLine();
    Product p = pm.findProductById(pid);
    if (p == null) { System.out.println("Product not found."); return; }
    System.out.println("Average rating: " + p.getAverageRating());
}

public void extractReviewsByCustomerInteractive(Scanner sc, CustomerManager cm) {
    System.out.print("Enter customer ID: "); int cid = sc.nextInt(); sc.nextLine();
    Customer c = cm.findCustomerById(cid);
    if (c == null) { System.out.println("Customer not found."); return; }
    extractReviewsByCustomer(cid); // Method in ReviewManager that iterates over all products
}

public void showCommonReviewedProductsInteractive(Scanner sc, CustomerManager cm) {
    System.out.print("Enter first customer ID: "); int id1 = sc.nextInt(); sc.nextLine();
    System.out.print("Enter second customer ID: "); int id2 = sc.nextInt(); sc.nextLine();
    showCommonReviewedProducts(allProductsArray(), id1, id2); // Assumes a method to get Product[]
}


    public void saveToCSV(String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write("reviewId,productId,customerId,rating,comment");
        bw.newLine();
        LinkedList.Node<Review> current = reviews.getHead();
        while (current != null) {
            bw.write(current.data.toCSV());
            bw.newLine();
            current = current.next;
        }
        bw.close();
    }

    public LinkedList<Review> getReviews() { return reviews; }
}
