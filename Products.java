public class Products {

    private LinkedList<Product> products;

    public Products() {
        this.products = new LinkedList<>();
    }

    public Products(LinkedList<Product> inputProducts) {
        this.products = (inputProducts != null) ? inputProducts : new LinkedList<>();
    }

    public LinkedList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product p) {
        if (p == null) {
            System.out.println("Cannot add null product");
            return;
        }
        if (findProductById(p.getProductId()) != null) {
            System.out.println("Product with this ID already exists");
        } else {
            products.add(p);  
            System.out.println("Product successfully added");
        }
    }

    public void removeProduct(int productId) {
        if (products.isEmpty()) {
            System.out.println("Product with this ID not found");
            return;
        }
        LinkedList<Product> rebuilt = new LinkedList<>(); //holds all the products except the one being removed
        boolean removed = false;

        for (int i = 0; i < products.size(); i++) {
            Product cur = products.get(i);
            if (cur != null && cur.getProductId() == productId) {
                removed = true; 
            } else {
                rebuilt.add(cur);
            }
        }

        products = rebuilt;
        System.out.println(removed ? "Product successfully removed"
                                   : "Product with this ID not found");
    }

    public void updateProduct(int productId, Product newData) {
        Product old = findProductById(productId);
        if (old == null) {
            System.out.println("Doesn't exist");
        } else {
            old.UpdateProduct(newData); 
        }
    }

    public Product findProductById(int productId) {
        if (products.isEmpty()) 
            return null;
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p != null && p.getProductId() == productId) 
                return p;
        }
        return null;
    }

    public Product findByName(String name) {
        if (name == null || products.isEmpty()) return null;
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p != null && name.equalsIgnoreCase(p.getName())) return p;
        }
        return null;
    }

    public void displayOutOfStock() {
        if (products.isEmpty()) {
            System.out.println("No products exist");
            return;
        }
        boolean found = false;
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p != null && p.getStock() == 0) {
                p.display();
                found = true;
            }
        }
        if (!found) System.out.println("No out of stock products");
    }

    public void addReviewToProduct(Review r) {
        if (r == null) return;
        Product p = findProductById(r.getProductId());
        if (p == null)
            System.out.println("Product does not exist to assign review " + r.getReviewId() + " to it");
        else
            p.addReview(r);
    }

    public void linkReviewToProduct(Review r) {
        if (r == null || products.isEmpty()) return;
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p != null && p.getProductId() == r.getProductId()) {
                p.addReview(r);
                return;
            }
        }
    }
}
