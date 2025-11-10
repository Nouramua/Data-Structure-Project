public class Review {
    private String reviewId;
    private Product product;
    private Customer customer;
    private int rating;
    private String comment;

    public Review(String reviewId, Product product, Customer customer, int rating, String comment) {
        this.reviewId = reviewId;
        this.product = product;
        this.customer = customer;
        this.rating = rating;
        this.comment = comment;
    }

    public String getReviewId() { return reviewId; }
    public Product getProduct() { return product; }
    public Customer getCustomer() { return customer; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }

    public void setRating(int rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }
    
    public String toString() {
    return String.format("Review[ID: %s, Product: %s, Customer: %s, Rating: %d/5, Comment: %s]", 
                        reviewId, product.getName(), customer.getName(), rating, 
                        comment.length() > 30 ? comment.substring(0, 30) + "..." : comment);
}

    public String toCSV() {
        return reviewId + "," + product.getProductId() + "," + customer.getCustomerId() + "," + rating + "," + comment;
    }
}