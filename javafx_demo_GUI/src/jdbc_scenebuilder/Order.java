package jdbc_scenebuilder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderID;
    //private Timestamp orderDateTime;
    private LocalTime time;
    private int day;
    private int month;
    private int year;
    private double totalPrice;
    private double tip;
    private String notes;

    public Order(int orderID, double totalPrice, double tip, String notes) {
        this.orderID = orderID;
        this.totalPrice = totalPrice;
        //this.orderDateTime = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        this.time = LocalTime.now().withNano(0);
        this.day = LocalDateTime.now().getDayOfMonth();
        this.month = LocalDateTime.now().getMonthValue();
        this.year = LocalDateTime.now().getYear();
        this.tip = 0.0;
        this.notes = "";
    }

    public int getOrderID() { return orderID; }
    public LocalTime getOrderTime() { return time; } //time, day, month, year
    public int getDay() { return day; }
    public int getYear() { return year; }
    //public Timestamp getOrderTime() { return orderDateTime; } //time, day, month, year
    public int getMonth() { return month; }
    public double getTotalPrice() { return totalPrice; }
    public double getTip() { return tip; }
    public String getSpecialNotes() { return notes; }
}
