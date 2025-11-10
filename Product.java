public class Product {
    private String productId;
    private String name;
    private double price;
    private int stock;
    private LinkedList<Review> reviews; 

    public Product(String productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.reviews = new LinkedList<>();
    }

    public String getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    public void setStock(int stock) { this.stock = stock; }
    public void setPrice(double price) { this.price = price; }
    public void setName(String name) { this.name = name; }

    public LinkedList<Review> getListOfReviews() { return reviews; }

    public double getAverageRating() {
        if (reviews == null || reviews.empty()) {
            return 0.0;
        }

        int totalRating = 0;
        int reviewCount = 0;

        Node<Review> current = reviews.getHead();
        while (current != null) {
            totalRating += current.data.getRating();
            reviewCount++;
            current = current.next;
        }

        return reviewCount > 0 ? (double) totalRating / reviewCount : 0.0;
    }

    @Override
    public String toString() {
        return String.format(
            "Product[ID: %s, Name: %s, Price: $%.2f, Stock: %d, Avg Rating: %.1f]",
            productId, name, price, stock, getAverageRating()
        );
    }

    public String toCSV() {
        return productId + "," + name + "," + price + "," + stock;
    }
}