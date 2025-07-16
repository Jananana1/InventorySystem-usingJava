package InventorySystem;

public class Tinda extends Product {
    private int ellaWarranty;

    public Tinda(String angeloId, String ellaName, int santosQuantity, double maePrice, int ellaWarranty) {
        super(angeloId, ellaName, santosQuantity, maePrice);
        this.ellaWarranty = ellaWarranty;
    }

    public int getWarranty() {
        return ellaWarranty;
    }

    public void setWarranty(int ellaWarranty) {
        this.ellaWarranty = ellaWarranty;
    }
}
