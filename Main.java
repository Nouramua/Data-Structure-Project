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
    }
}//End Class
