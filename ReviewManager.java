public class ReviewManager {

    public static void showReviewsByCustomer(Product[] products, int customerId) {
        System.out.println("\nReviews by Customer ID: " + customerId);
        boolean found = false;

        for (int i = 0; i < products.length; i++) {
            Product p = products[i];
            for (int j = 0; j < p.getReviewCount(); j++) {
                Review r = p.getReviews()[j];
                if (r != null && r.getCustomerId() == customerId) {
                    System.out.println(
                        "- Product: " + p.getName() +
                        " | Rating: " + r.getRating() +
                        " | Comment: " + r.getComment()
                        );
                    found = true;
                }
            }
        }

        if (!found)
            System.out.println("This customer has no reviews.");
    }

    public static void showTop3Products(Product[] products) {
        // (Bubble Sort)
        for (int i = 0; i < products.length - 1; i++) {
            for (int j = i + 1; j < products.length; j++) {
                if (products[j].getAverageRating() > products[i].getAverageRating()) {
                    Product temp = products[i];
                    products[i] = products[j];
                    products[j] = temp;
                }
            }
        }

        System.out.println("\nTop 3 Products by Average Rating:");
        int top = Math.min(3, products.length);
        for (int i = 0; i < top; i++) {
            System.out.println((i + 1) + ". " + products[i].getName() +
                    " → Avg Rating: " + String.format("%.2f", products[i].getAverageRating()));
        }
    }

    public static void showCommonReviewedProducts(Product[] products, int id1, int id2) {
        System.out.println("\nProducts reviewed by both customers (" + id1 + " & " + id2 + ") with rating > 4:");
        boolean found = false;

        for (int i = 0; i < products.length; i++) {
            Product p = products[i];
            boolean reviewedByA = false;
            boolean reviewedByB = false;

            for (int j = 0; j < p.getReviewCount(); j++) {
                Review r = p.getReviews()[j];
                if (r == null) continue;

                if (r.getCustomerId() == id1 && r.getRating() > 4)
                    reviewedByA = true;
                if (r.getCustomerId() == id2 && r.getRating() > 4)
                    reviewedByB = true;
            }

            if (reviewedByA && reviewedByB) {
                System.out.println("- " + p.getName());
                found = true;
            }
        }

        if (!found)
            System.out.println("No common products found with rating > 4.");
    }
}//End Class
