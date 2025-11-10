import java.io.*;
import java.util.Scanner;

public class ProductManager {
    private LinkedList<Product> products;

    public ProductManager() {
        products = new LinkedList<>();
    }

    public void loadFromCSV(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        br.readLine();

        int count = 0;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                String id = parts[0].trim();
                String name = parts[1].trim();
                double price = Double.parseDouble(parts[2].trim());
                int stock = Integer.parseInt(parts[3].trim());
                products.insert(new Product(id, name, price, stock));
                count++;
            }
        }
        br.close();
        System.out.println("Loaded " + count + " products");
    }

    public boolean addProduct(Product p) {
        Node<Product> tmp = products.getHead();
        while (tmp != null) {
            if (p.getProductId().equals(tmp.data.getProductId())) {
                return false;
            }
            tmp = tmp.next;
        }
        products.insert(p);
        return true;
    }

    public void createProduct(Scanner sc) {
        System.out.print("Enter product ID: ");
        String proID = sc.nextLine().trim();

        System.out.print("Enter product name: ");
        String proName = sc.nextLine().trim();

        System.out.print("Enter product price: ");
        String priceInput = sc.nextLine().trim();

        System.out.print("Enter product stock: ");
        String stockInput = sc.nextLine().trim();

        if (proID.isEmpty() || proName.isEmpty()) {
            System.out.println("Uh Oh! Product ID and name are required, Product not created.");
            return;
        }

        double proPrice;
        int proStock;

        try {
            proPrice = Double.parseDouble(priceInput);
            proStock = Integer.parseInt(stockInput);
        } catch (NumberFormatException e) {
            System.out.println("Uh Oh, invalid number for price or stock. Product not created.");
            return;
        }

        if (proPrice <= 0 || proStock < 0) {
            System.out.println("Uh Oh, Price must be > 0 and stock must be ≥ 0. Product not created.");
            return;
        }

        Product p = new Product(proID, proName, proPrice, proStock);
        boolean result = addProduct(p);

        if (result) {
            System.out.println("Product created!");
        } else {
            System.out.println("Uh Oh, Product ID already exists. Product not created.");
        }
    }

    public void removeProductById(Scanner sc) {
        System.out.print("Enter product ID to remove: ");
        String removeId = sc.nextLine().trim();

        Product p = findProductById(removeId);
        if (p == null) {
            System.out.println("Product not found.");
            return;
        }

        products.findfirst();
        while (products.getCurrent() != null) {
            if (products.getCurrent().data.getProductId().equals(removeId)) {
                products.remove();
                System.out.println("Product removed successfully.");
                return;
            }
            products.findnext();
        }
    }

    public void updateProduct(Scanner sc) {

        System.out.print("Enter product ID to update: ");
        String updateId = sc.nextLine().trim();

        Product p = findProductById(updateId);
        if (p == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Enter new name: ");
        String newName = sc.nextLine().trim();

        System.out.print("Enter new price: ");
        String priceInput = sc.nextLine().trim();

        System.out.print("Enter new stock: ");
        String stockInput = sc.nextLine().trim();

        double newPrice;
        int newStock;
        try {
            newPrice = Double.parseDouble(priceInput);
            newStock = Integer.parseInt(stockInput);
        } catch (NumberFormatException e) {
            System.out.println("Uh Oh, Invalid number for price or stock. Product not updated.");
            return;
        }

        if (newPrice <= 0 || newStock < 0) {
            System.out.println("Uh Oh, Price must be > 0 and stock ≥ 0. Product not updated.");
            return;
        }

        products.findfirst();
        while (products.getCurrent() != null) {
            if (products.getCurrent().data.getProductId().equals(updateId)) {
                products.getCurrent().data.setName(newName);
                products.getCurrent().data.setPrice(newPrice);
                products.getCurrent().data.setStock(newStock);
                System.out.println("Product updated!");
                return;
            }
            products.findnext();
        }
    }

    public void searchProduct(Scanner sc) {
        System.out.print("Enter product ID or name to search: ");
        String keyword = sc.nextLine().trim();

        Node<Product> current = products.getHead();
        boolean found = false;
        while (current != null) {
            Product p = current.data;
            if (p.getProductId().equals(keyword) ||
                p.getName().equalsIgnoreCase(keyword)) {
                System.out.println(p.toString());
                found = true;
            }
            current = current.next;
        }
        if (!found) System.out.println("Product not found.");
    }

    public Product findProductById(String id) {
        Node<Product> current = products.getHead();
        while (current != null) {
            if (current.data.getProductId().equals(id)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public void trackOutOfStock() {
        Node<Product> current = products.getHead();
        boolean found = false;
        System.out.println("--- Out of Stock Products ---");
        while (current != null) {
            if (current.data.getStock() == 0) {
                System.out.println(current.data.getProductId()
                                   + " | " + current.data.getName());
                found = true;
            }
            current = current.next;
        }
        if (!found) System.out.println("No out of stock products.");
    }

    public void saveToCSV(String fileName) throws IOException {
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

    public LinkedList<Product> getProducts() {
        return products;
    }
}