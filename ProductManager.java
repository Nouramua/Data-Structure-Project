import java.io.*;
    
public class ProductManager {
    private static LinkedList<Product> products = new LinkedList<>();

    public static void loadFromCSV(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 4) continue;

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                int stock = Integer.parseInt(parts[3]);

                products.insert(new Product(id, name, price, stock));
            }
            System.out.println("Products loaded successfully from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }

    public static void saveToCSV(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            Node<Product> node = products.getHead();
            while (node != null) {
                Product p = node.data;
                bw.write(p.getProductId() + "," + p.getName() + "," + p.getPrice() + "," + p.getStock());
                bw.newLine();
                node = node.next;
            }
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }

    public static LinkedList<Product> getProducts() {
        return products;
    }

    public static void addProduct(Product p) {
        products.insert(p);
    }
}
