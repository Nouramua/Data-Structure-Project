public class Customer {

    private int customerId;
    private String name;
    private String email;
    private LinkedList<Order> orders;
    private LinkedList<Review> reviews; 

    public Customer(int customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.orders = new LinkedList<>();
        this.reviews = new LinkedList<>();
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LinkedList<Order> getOrders() {
        return orders;
    }

    public LinkedList<Review> getReviews() {
        return reviews;
    }

    public void addOrder(Order o) {
        if (o != null) orders.add(o);
    }

    
    public void displayReviews() {
        System.out.println("Reviews for " + name + ":");
        if (reviews.isEmpty()) {
            System.out.println("No reviews yet");
        } else {
            for (int i = 0; i < reviews.size(); i++) {
                Review rev = reviews.get(i);
                if (rev != null) rev.display();
            }
        }
    }

    public void displayOrders() {
        System.out.println("Orders by " + name + ":");
        if (orders.isEmpty()) {
            System.out.println("No orders yet");
        } else {
            for (int i = 0; i < orders.size(); i++) {
                Order o = orders.get(i);
                if (o != null) o.display();
            }
        }
    }
}
