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
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public LinkedList<Review> getReviews() { return reviews; }

    public String toCSV() {
        return productId + "," + name + "," + price + "," + stock;
    }
}
