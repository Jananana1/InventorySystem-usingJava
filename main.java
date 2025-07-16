package InventorySystem;

import java.util.*;
import java.io.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

class main implements Manage {
    private List<Product> santosInventory = new ArrayList<>();
    private static final String abanganDbUrl = "jdbc:mysql://localhost:3306/inventory_db?useSSL=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String aladDbUser = "root";
    private static final String jannaDbPassword = "W7301@jqir#";

    public main() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found: " + e.getMessage());
        }
        loadFromDatabase();
    }

    @Override
    public void createItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter ID: ");
        String angeloItemId = scanner.nextLine();

        Product ellaExistingItem = findProductById(angeloItemId);
        if (ellaExistingItem != null) {
            System.out.println("Error: An item with this ID already exists.");
            return;
        }

        System.out.print("Enter Name: ");
        String ellaItemName = scanner.nextLine();
        System.out.print("Enter Quantity: ");
        int santosItemQuantity = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Price: ");
        double maeItemPrice = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Warranty in months (0 if not applicable): ");
        int aladItemWarranty = scanner.nextInt();
        scanner.nextLine();

        Product ellaProduct = aladItemWarranty > 0
                ? new Tinda(angeloItemId, ellaItemName, santosItemQuantity, maeItemPrice, aladItemWarranty)
                : new Product(angeloItemId, ellaItemName, santosItemQuantity, maeItemPrice);

        santosInventory.add(ellaProduct);
        saveToDatabase(ellaProduct);
        System.out.println("Item created successfully.");
        scanner.nextLine();
    }

    @Override
    public void readItems() {
        if (santosInventory.isEmpty()) {
            System.out.println("No items in inventory.");
        } else {
            for (Product product : santosInventory) {
                String formattedDetails = String.format(
                        "ID: %-7s Name: %-10s Quantity: %-5d Price: %-10.2f Warranty: %d years",
                        product.getId(),
                        product.getName(),
                        product.getQuantity(),
                        product.getPrice(),
                        product instanceof Tinda ? ((Tinda) product).getWarranty() : 0
                );
                System.out.println(formattedDetails);
                System.out.println();
            }
        }
    }

    @Override
    public void updateItem(String angeloItemId) {
        for (Product product : santosInventory) {
            if (product.getId().equals(angeloItemId)) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter new Name: ");
                product.setName(scanner.nextLine());
                System.out.print("Enter new Quantity: ");
                product.setQuantity(scanner.nextInt());
                System.out.print("Enter new Price: ");
                product.setPrice(scanner.nextDouble());
                scanner.nextLine();

                updateDatabase(product);
                System.out.println("Item updated successfully.\n");
                return;
            }
        }
        System.out.println("Item not found.");
    }

    @Override
    public void deleteItem(String angeloItemId) {
        Iterator<Product> iterator = santosInventory.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getId().equals(angeloItemId)) {
                iterator.remove();
                deleteFromDatabase(angeloItemId);
                System.out.println("Item deleted successfully.\n");
                
                return;
            }
        }
        System.out.println("Item not found.");
    }

    private Product findProductById(String abanganId) {
        for (Product product : santosInventory) {
            if (product.getId().equals(abanganId)) {
                return product;
            }
        }
        return null;
    }

    private void saveToDatabase(Product ellaProduct) {
        try (Connection conn = DriverManager.getConnection(abanganDbUrl, aladDbUser, jannaDbPassword);
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO inventory (id, name, quantity, price, warranty) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setString(1, ellaProduct.getId());
            stmt.setString(2, ellaProduct.getName());
            stmt.setInt(3, ellaProduct.getQuantity());
            stmt.setDouble(4, ellaProduct.getPrice());
            stmt.setInt(5, ellaProduct instanceof Tinda ? ((Tinda) ellaProduct).getWarranty() : 0);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error saving to database: " + e.getMessage());
        }
    }

    private void updateDatabase(Product santosProduct) {
        try (Connection conn = DriverManager.getConnection(abanganDbUrl, aladDbUser, jannaDbPassword);
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE inventory SET name = ?, quantity = ?, price = ?, warranty = ? WHERE id = ?")) {
            stmt.setString(1, santosProduct.getName());
            stmt.setInt(2, santosProduct.getQuantity());
            stmt.setDouble(3, santosProduct.getPrice());
            stmt.setInt(4, santosProduct instanceof Tinda ? ((Tinda) santosProduct).getWarranty() : 0);
            stmt.setString(5, santosProduct.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating database: " + e.getMessage());
        }
    }

    private void deleteFromDatabase(String abanganId) {
        try (Connection conn = DriverManager.getConnection(abanganDbUrl, aladDbUser, jannaDbPassword);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM inventory WHERE id = ?")) {
            stmt.setString(1, abanganId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting from database: " + e.getMessage());
        }
    }

    private void loadFromDatabase() {
        try (Connection conn = DriverManager.getConnection(abanganDbUrl, aladDbUser, jannaDbPassword);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM inventory")) {
            while (rs.next()) {
                String angeloItemId = rs.getString("id");
                String ellaItemName = rs.getString("name");
                int santosItemQuantity = rs.getInt("quantity");
                double maeItemPrice = rs.getDouble("price");
                int aladItemWarranty = rs.getInt("warranty");

                Product ellaProduct = aladItemWarranty > 0
                        ? new Tinda(angeloItemId, ellaItemName, santosItemQuantity, maeItemPrice, aladItemWarranty)
                        : new Product(angeloItemId, ellaItemName, santosItemQuantity, maeItemPrice);

                santosInventory.add(ellaProduct);
            }
        } catch (SQLException e) {
            System.out.println("Error loading from database: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        main inventoryManager = new main();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("JEA's Electronics Inventory System");
            System.out.println("1. Create Item");
            System.out.println("2. Read Items");
            System.out.println("3. Update Item");
            System.out.println("4. Delete Item");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    inventoryManager.createItem();
                    break;
                case 2:
                    inventoryManager.readItems();
                    break;
                case 3:
                    System.out.print("Enter ID of item to update: ");
                    String updateId = scanner.nextLine();
                    inventoryManager.updateItem(updateId);
                    break;
                case 4:
                    System.out.print("Enter ID of item to delete: ");
                    String deleteId = scanner.nextLine();
                    inventoryManager.deleteItem(deleteId);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
}
