package com.company.logic;

public class ShareRecord {                          // DONE <--->

    private Share share;
    private int totalAmount;                        // кол-во акций
    private double totalCapital;                           // их общая стоимость (целиком за весь набор)

    public ShareRecord(Share share, int totalAmount){
        this.share = share;
        this.totalAmount += totalAmount;
        totalCapital += share.getPrice()*totalAmount;
    }

    public Share getShare() {
        return share;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public double getTotalCapital() {
        return totalCapital;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount += totalAmount;
    }

    public void setTotalCapital(double totalCapital) {
        this.totalCapital += totalCapital;
    }
}
