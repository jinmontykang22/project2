package jdbc_scenebuilder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderID;
    private Timestamp orderDateTime;
    private int month;
    //private List<Item> items;
    private double totalPrice;
    private double tip;
    private String notes;

    public Order(int orderID, double totalPrice, double tip, String notes) {
        //this.items = new ArrayList<>();
        this.orderID = orderID;
        this.totalPrice = totalPrice;
        this.orderDateTime = Timestamp.valueOf(LocalDateTime.now());
        this.month = LocalDateTime.now().getMonthValue();
        this.tip = 0.0;
        this.notes = "";
    }

    public int getOrderID() { return orderID; }
    public Timestamp getOrderTime() { return orderDateTime; }
    public int getMonth() { return month; }
    public double getTotalPrice() { return totalPrice; }
    public double getTip() { return tip; }
    public String getSpecialNotes() { return notes; }

    // public void addItem(Item item) {
    //     items.add(item);
    // }

    // public double getTotal() {
    //     return items.stream().mapToDouble(Item::getTotalPrice).sum() + tip;
    // }

    // public List<Item> getItems() {
    //     return items;
    // }

    // public String summary() {
    //     StringBuilder sb = new StringBuilder();
    //     for (Item i : items) {
    //         sb.append(i.toString()).append("\n");
    //     }
    //     sb.append("Total: $").append(String.format("%.2f", getTotal())).append("\n");
    //     return sb.toString();
    // }
}
