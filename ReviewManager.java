import java.io.*;

public class ReviewManager {
    private static LinkedList<Review> reviews = new LinkedList<>();

    public static void loadFromCSV(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 3) continue;

                int customerId = Integer.parseInt(parts[0]);
                int rating = Integer.parseInt(parts[1]);
                String comment = parts[2];

                reviews.insert(new Review(customerId, rating, comment));
            }
            System.out.println("Reviews loaded successfully from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading reviews: " + e.getMessage());
        }
    }

    public static void saveToCSV(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            Node<Review> node = reviews.getHead();
            while (node != null) {
                Review r = node.data;
                bw.write(r.getCustomerId() + "," + r.getRating() + "," + r.getComment());
                bw.newLine();
                node = node.next;
            }
        } catch (IOException e) {
            System.out.println("Error saving reviews: " + e.getMessage());
        }
    }

    public static LinkedList<Review> getReviews() {
        return reviews;
    }

    public static void addReview(Review r) {
        reviews.insert(r);
    }
}
