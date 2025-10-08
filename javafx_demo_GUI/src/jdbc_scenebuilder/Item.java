package jdbc_scenebuilder;

public class Item {
    private String productName;
    private int itemID;
    private int orderID;
    private int productID;
    private String size;
    private String sugarLevel;
    private String iceLevel;
    private String toppings; // comma-separated string (from a list)
    private double price;

//     sizes = ['Small', 'Medium', 'Large', 'Bucees_Large']
// sugar_or_ice = ['0', '50', '75', '100']
// toppings_options = ['Boba','Jelly','Pudding','None']

    public Item(String productName, int itemID, int orderID, int productID, double price, String size, String sugar, String ice, String toppings) {
        this.productName = productName;
        this.itemID = itemID;
        this.orderID = orderID;
        this.productID = productID;
        this.price = price;
        this.size = size;
        this.sugarLevel = sugar;
        this.iceLevel = ice;
        this.toppings = toppings;
    }

    public double getTotalPrice() {
        return Math.round(price * 100.0) / 100.0;
    }

    public String getProductName() { return productName; }
    public int getItemID() { return itemID; }
    public int getOrderID() { return orderID; }
    public int getProductID() { return productID; }
    public String getSize() { return size; }
    public String getSugarLevel() { return sugarLevel; }
    public String getIceLevel() { return iceLevel; }
    public String getToppings() { return toppings; }


    @Override
    public String toString() {
        return itemID + " " + productName +  " " + size + " " + sugarLevel + "/" + iceLevel + " " + toppings + " $" + getTotalPrice();
    }
}
