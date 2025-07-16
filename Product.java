package InventorySystem;

public class Product extends Item {
    private double maePrice;

    public Product(String angeloId, String ellaName, int santosQuantity, double maePrice) {
        super(angeloId, ellaName, santosQuantity);
        this.maePrice = maePrice;
    }

    public double getPrice() {
        return maePrice;
    }

    public void setPrice(double maePrice) {
        this.maePrice = maePrice;
    }
}
