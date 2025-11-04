import java.io.*;
import java.util.Scanner;

public class ProductManager {
    private LinkedList<Product> products;

    public ProductManager() { products = new LinkedList<>(); }

    public void loadFromCSV(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        br.readLine(); // skip header
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",", -1);
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            double price = Double.parseDouble(parts[2]);
            int stock = Integer.parseInt(parts[3]);
            products.insert(new Product(id, name, price, stock));
        }
        br.close();
    }

    public void removeProductById(int id) {
    LinkedList.Node<Product> current = products.getHead();
    while (current != null) {
        if (current.data.getProductId() == id) {
            products.findfirst();
            while (products.getCurrent() != current) products.findnext();
            products.remove();
            System.out.println("Product removed.");
            return;
        }
        current = current.next;
    }
    System.out.println("Product ID not found.");
}

public void updateProduct(int id, String name, double price, int stock) {
    LinkedList.Node<Product> current = products.getHead();
    while (current != null) {
        if (current.data.getProductId() == id) {
            current.data.setName(name);
            current.data.setPrice(price);
            current.data.setStock(stock);
            System.out.println("Product updated.");
            return;
        }
        current = current.next;
    }
    System.out.println("Product ID not found.");
}

public void searchProduct(String keyword) {
    LinkedList.Node<Product> current = products.getHead();
    boolean found = false;
    while (current != null) {
        Product p = current.data;
        if (String.valueOf(p.getProductId()).equals(keyword) || p.getName().equalsIgnoreCase(keyword)) {
            System.out.println(p.getProductId() + " | " + p.getName() + " | $" + p.getPrice() + " | Stock: " + p.getStock());
            found = true;
        }
        current = current.next;
    }
    if (!found) System.out.println("Product not found.");
}

public void trackOutOfStock() {
    LinkedList.Node<Product> current = products.getHead();
    boolean found = false;
    System.out.println("--- Out of Stock Products ---");
    while (current != null) {
        if (current.data.getStock() == 0) {
            System.out.println(current.data.getProductId() + " | " + current.data.getName());
            found = true;
        }
        current = current.next;
    }
    if (!found) System.out.println("No out-of-stock products.");
}


    public void saveToCSV(String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write("productId,name,price,stock");
        bw.newLine();
        LinkedList.Node<Product> current = products.getHead();
        while (current != null) {
            bw.write(current.data.toCSV());
            bw.newLine();
            current = current.next;
        }
        bw.close();
    }

    public LinkedList<Product> getProducts() { return products; }
}
