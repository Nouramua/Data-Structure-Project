public class Review {
    
    private int customerId;
    private int rating;     // 1 to 5
    private String comment;

    public Review(int customerId, int rating, String comment) {
        this.customerId = customerId;
        this.rating = rating;
        this.comment = comment;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}//End Class
