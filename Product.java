public class Product {

    private int productId;
    private String name;
    private double price;
    private int stock;

    private Review[] reviews;
    private int reviewCount;

    public Product(int productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.reviews = new Review[100];
        this.reviewCount = 0;
    }

    public String getName() {
        return name;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public Review[] getReviews() {
        return reviews;
    }
    public double getPrice() { 
        return price;
     }

    public int getStock() {
         return stock; 
        }

    public void setStock(int stock) {
         this.stock = stock;
         }

    public void addReview(Review r) {
        if (reviewCount < reviews.length) {
            reviews[reviewCount] = r;
            reviewCount++;
        } else {
            System.out.println("Cannot add more reviews for product " + name);
        }
    }

    public void editReview(int customerId, int newRating, String newComment) {
        for (int i = 0; i < reviewCount; i++) {
            if (reviews[i].getCustomerId() == customerId) {
                reviews[i].setRating(newRating);
                reviews[i].setComment(newComment);
                System.out.println("Review updated for product: " + name);
                return;
            }
        }
        System.out.println("No review found for customer " + customerId + " in product " + name);
    }

    public double getAverageRating() {
        if (reviewCount == 0) return 0;
        double sum = 0;
        for (int i = 0; i < reviewCount; i++) {
            sum += reviews[i].getRating();
        }
        return sum / reviewCount;
    }

}//End Class
