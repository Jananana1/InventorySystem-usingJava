package InventorySystem;

class Utils {
    public static double calculateTotalValue(Product santosProduct) {
        if (santosProduct == null) {
            System.out.println("Product is null. Cannot calculate total value.");
            return 0.0;
        }
        return santosProduct.getQuantity() * santosProduct.getPrice();
    }

    public static double calculateTotalValue(Product santosProduct, double angeloDiscount) {
        if (santosProduct == null) {
            System.out.println("Product is null. Cannot calculate total value.");
            return 0.0;
        }
        return (santosProduct.getQuantity() * santosProduct.getPrice()) * (1 - angeloDiscount);
    }
}
