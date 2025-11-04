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

    public int getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public double getStock() { return stock; }

    public LinkedList<Review> getReviews() { return reviews; }

    public void addReview(Review r) { reviews.insert(r); }

    public double getAverageRating() {
        if (reviews.getSize() == 0) return 0;
        double total = 0;
        Node<Review> current = reviews.getHead();
        while (current != null) {
            total += current.data.getRating();
            current = current.next;
        }
        return total / reviews.getSize();
    }
}