package com.company.logic;

public class Share {                     // DONE <--->

    private double price;
    private String companyName;
    private String uniqueID;

    public Share(double price, String companyName) {

        this.price = price;
        this.companyName = companyName;
        uniqueID = companyName.substring(0, 2).toUpperCase() + ((int) (Math.random() * 899) + 100);
    }

    public double getPrice() {
        return price;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getUniqueID() {
        return uniqueID;
    }
}
