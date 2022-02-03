package com.company.logic;

import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public class Trader{

    private String recordName;
    // need to add contacts/links
    private double accountBalance;
    private TreeMap<String, ShareRecord> portfolio = new TreeMap<>();                           // id ---> соотв. актив
    private LinkedHashMap<String, HistoryRecord> history = new LinkedHashMap<>();               // id ---> соотв. транзакция


    public Trader(String recordName, double accountBalance){
        this.recordName = recordName;
        this.accountBalance = accountBalance;
    }

    public void printTraderPortfolio(){
        System.out.println("Trader name: " + getRecordName() + " --- current balance: " + getAccountBalance());
        System.out.println("Portfolo\n");
        TreeMap<String, ShareRecord> p = getPortfolio();
        System.out.println("| ShareID | Company | # of shares | Price | Net worth |");
        System.out.println("-------------------------------------------------------");
        for(String key : p.keySet()){
            ShareRecord record = p.get(key);
            System.out.println("   " + key + "      " + record.getShare().getCompanyName() + "          " + record.getTotalAmount() + "      " + record.getShare().getPrice() + "      " +record.getTotalCapital());
            System.out.println("-------------------------------------------------------");
        }
    }

    public void printTraderHistory(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss");
        System.out.println("History for:  " + getRecordName() + "\n");
        System.out.println("| Transaction date/time |  Transaction ID  | Type of transaction | Share ID |  Company | Price | # of shares | # of shares left | Amount |  Balance  |");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
        for(String key : getHistory().keySet()) {
            HistoryRecord rec = getHistory().get(key);
            String space = (rec.getTransactionType().equals("SOLD"))? "     " : "";
            String sign = (rec.getTransactionType().equalsIgnoreCase("PURCHASED"))? "-" : "+";
            String signShares = (rec.getTransactionType().equalsIgnoreCase("PURCHASED"))? "+" : "-";
            String date = rec.getDateTime().format(format);
            System.out.println("   " + date + "      " + key + "              " + rec.getTransactionType() + "         " + space + rec.getShare().getUniqueID() + "      "
                    + rec.getShare().getCompanyName() + "     " + rec.getShare().getPrice() + "        " + signShares + rec.getAmount() + "             "
                    + rec.getSharesOfThisType() + "           " + sign + rec.getTotal() + "    " + rec.getBalanceInHistory());

            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    public String getRecordName() {
        return recordName;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public TreeMap<String, ShareRecord> getPortfolio() {
        return portfolio;
    }

    public LinkedHashMap<String, HistoryRecord> getHistory() {
        return history;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance += accountBalance;
    }


}
