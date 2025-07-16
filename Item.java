package InventorySystem;

public abstract class Item {
    private String abanganId;
    private String ellaName;
    private int santosQuantity;

    public Item(String abanganId, String ellaName, int santosQuantity) {
        this.abanganId = abanganId;
        this.ellaName = ellaName;
        this.santosQuantity = santosQuantity;
    }

    public String getId() {
        return abanganId;
    }

    public String getName() {
        return ellaName;
    }

    public void setName(String ellaName) {
        this.ellaName = ellaName;
    }

    public int getQuantity() {
        return santosQuantity;
    }

    public void setQuantity(int santosQuantity) {
        this.santosQuantity = santosQuantity;
    }
}
