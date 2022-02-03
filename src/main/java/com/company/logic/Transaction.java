package com.company.logic;

import java.time.LocalDateTime;

public class Transaction {                              // DONE <--->

    private Share share;
    private Trader buyer;
    private Trader seller;
    private double total;
    private int amount;
    private boolean status;
    private LocalDateTime dateTime;

    public Transaction(Share share, Trader buyer, Trader seller, int amount){
        this.share = share;
        this.buyer = buyer;
        this.seller = seller;
        this.amount = amount;
        total = this.share.getPrice()*amount;
    }

    public Share getShare() {
        return share;
    }

    public Trader getBuyer() {
        return buyer;
    }

    public Trader getSeller() {
        return seller;
    }

    public double getTotal() {
        return total;
    }

    public int getAmount() {
        return amount;
    }

    public boolean getStatus() {
        return status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
