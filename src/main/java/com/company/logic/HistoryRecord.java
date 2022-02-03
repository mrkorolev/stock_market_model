package com.company.logic;

public class HistoryRecord extends Transaction{

    private String transactionType;
    private double balanceInHistory = 0;
    private int sharesOfThisType = 0;

    public HistoryRecord(String transactionType, Share share, Trader buyer, Trader seller, int amount){
        super(share, buyer, seller, amount);
        this.transactionType = transactionType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getBalanceInHistory() {
        return balanceInHistory;
    }

    public int getSharesOfThisType() {
        return sharesOfThisType;
    }

    public void setBalanceInHistory(double balanceInHistory) {
        this.balanceInHistory = balanceInHistory;
    }

    public void setSharesOfThisType(int sharesOfThisType) {
        this.sharesOfThisType += sharesOfThisType;
    }


}
