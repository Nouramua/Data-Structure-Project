package DSProject;
import java.io.*;

public class Product {
    private String productId;
    private String name;
    private double price;
    private int stock;
    private LinkedList<Review> reviews;
    
    private static LinkedList<Product> products = new LinkedList<Product>();

    public Product(String productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        reviews = new LinkedList<Review>();
    }
    
    public static void loadFromCSV(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        br.readLine(); // skip header
        
        int count = 0;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                String id = parts[0];
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                int stock = Integer.parseInt(parts[3]);
                products.insert(new Product(id, name, price, stock));
                count++;
            }
        }
        br.close();
        System.out.println("✅ Loaded " + count + " products");
    }
    
    public static void saveToCSV(String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write("productId,name,price,stock");
        bw.newLine();
        Node<Product> current = products.getHead();
        while (current != null) {
            bw.write(current.data.toCSV());
            bw.newLine();
            current = current.next;
        }
        bw.close();
    }
    
    public static boolean addProduct(Product p) {
    	boolean isUnique = true;
        
        if (!products.empty()) {
        	products.findfirst();
            
            while (!products.last()) {
                if (p.getProductId().equals(products.retrieve().getProductId())) {
                    isUnique = false;
                    break;
                }
                products.findnext();
            }
            
            if (p.getProductId().equals(products.retrieve().getProductId())) {
                isUnique = false;
            }
        }
        if (isUnique) {
        	products.insert(p);
        }
        return isUnique;
    }
    
    public static Product findProductById(String id) {
        Node<Product> current = products.getHead();
        while (current != null) {
            if (current.data.getProductId().equals(id))
            return current.data;
            
            current = current.next;
        }
        return null;
    }
    
    public static boolean placeProductreview(Product p, Review r) {
     boolean isUnique = true;
        
        if (!Product.getProducts().empty()) {
        	Product.getProducts().findfirst();
            
            while (!Product.getProducts().last()) {
                if (p.getProductId().equals(Product.getProducts().retrieve().getProductId())) {
                    isUnique = false;
                    break;
                }
                Product.getProducts().findnext();
            }
            
            if (p.getProductId().equals(Product.getProducts().retrieve().getProductId())) {
                isUnique = false;
            }
        }
        if (isUnique) {
        	Review.getReviews().insert(r);
        	p.reviews.insert(r);
        }
        return isUnique;	
    }
    
    public static boolean removeProductById(Product p) { 
    	boolean isRemoved = false;
        products.findfirst();
        while (!products.last()) {
            if (p.getProductId().equals(Product.getProducts().retrieve().getProductId())) {
                products.remove();
                isRemoved = true;
            }
                products.findnext();
            }
        
        if (p.getProductId().equals(Product.getProducts().retrieve().getProductId())) {
            products.remove();
            isRemoved = true;
        }
        
        return isRemoved;
        }


    public static boolean updateProduct(String updateId, String newName, double newPrice, int newStock) {
    	boolean isUpdated = false;
        products.findfirst();
        while (!products.last()) {
            if (Product.getProducts().retrieve().getProductId().equals(updateId)) {
            	Product.getProducts().retrieve().setName(newName);
            	Product.getProducts().retrieve().setPrice(newPrice);
            	Product.getProducts().retrieve().setStock(newStock);
            	isUpdated = true;
            }
            products.findnext();
        }
        if (Product.getProducts().retrieve().getProductId().equals(updateId)) {
        	Product.getProducts().retrieve().setName(newName);
        	Product.getProducts().retrieve().setPrice(newPrice);
        	Product.getProducts().retrieve().setStock(newStock);
        	isUpdated = true;
        }
        return isUpdated;
    }
    
    public static Product searchProduct(String keyword) {
    	products.findfirst();
    	while(!products.last()) {
    		if (Product.getProducts().retrieve().getProductId().equals(keyword) || Product.getProducts().retrieve().getName().equalsIgnoreCase(keyword))
            return Product.getProducts().retrieve();
    		
    		products.findnext();
    	}
    	
    	if (Product.getProducts().retrieve().getProductId().equals(keyword) || Product.getProducts().retrieve().getName().equalsIgnoreCase(keyword))
            return Product.getProducts().retrieve();
    	
    	return null;
    }
    
    public static void trackOutOfStock() {
        boolean found = false;
    	products.findfirst();
    	while(!products.last()) {
    		if (Product.getProducts().retrieve().getStock() == 0) {
                System.out.println(Product.getProducts().retrieve().getProductId() + " | " + Product.getProducts().retrieve().getName());
                found = true;
    		}
    		products.findnext();
    	}
    	if (Product.getProducts().retrieve().getStock() == 0) {
            System.out.println(Product.getProducts().retrieve().getProductId() + " | " + Product.getProducts().retrieve().getName());
            found = true;
		}
    	if (!found) System.out.println("No out-of-stock products.");
    }
    
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

        return (double) totalRating / reviewCount;
    }
    
    public static String extractReviewsByCustomer(Customer c) {
        String extractedReviews = "";
        Review.getReviews().findfirst();
    while (!Review.getReviews().last()) {
        if (Review.getReviews().retrieve().getCustomer().equals(c)) {
            extractedReviews += Review.getReviews().retrieve().toString() + "\n";
        }
        Review.getReviews().findnext();
    }
    if (Review.getReviews().retrieve().getCustomer().equals(c)) {
        extractedReviews += Review.getReviews().retrieve().toString() + "\n";
    }
    return extractedReviews;
    }

    //Setters & Getters
    public void setStock(int stock) { this.stock = stock; }
    public void setPrice(double price) { this.price = price; }
    public void setName(String name) { this.name = name; }
    
    public String getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public LinkedList<Review> getReviews() { return reviews; }
    public static LinkedList<Product> getProducts() { return products; }
    
    public String toString() {
        return String.format("Product[ID: %s, Name: %s, Price: $%.2f, Stock: %d, Avg Rating: %.1f]", 
                            productId, name, price, stock, getAverageRating());
    }

    public String toCSV() {
        return productId + "," + name + "," + price + "," + stock;
    }
}