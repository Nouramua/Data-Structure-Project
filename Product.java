public class Product {

    private int productId;
    private String name;
    private double price;
    private int stock;

    private LinkedList<Review> reviews;

    public Product(int productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.reviews = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public int getReviewCount() {
        return reviews.size();
    }

    public void addReview(Review r) {
        reviews.add(r);
    }

    public void editReview(int customerId, int newRating, String newComment) {
        LinkedList.current = reviews.getHead();
        while (current != null) {
            Review rev = current.data;
            if (rev.getCustomerId() == customerId) {
                rev.setRating(newRating);
                rev.setComment(newComment);
                System.out.println("Review updated for product: " + name);
                return;
            }
            current = current.next;
        }
        System.out.println("No review found for customer " + customerId + " in product " + name);
    }

    public double getAverageRating() {
        if (reviews.isEmpty()) return 0;
        double sum = 0;
        LinkedList.Node<Review> current = reviews.getHead();
        while (current != null) {
            sum += current.data.getRating();
            current = current.next;
        }
        return sum / reviews.size();
    }
}//End Class
