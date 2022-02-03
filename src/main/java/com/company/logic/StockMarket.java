package com.company.logic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public final class StockMarket {

    private List<Share> shares = new ArrayList<>();
    private List<Trader> traders = new ArrayList<>();
    private LinkedHashMap<String, Transaction> tradeHistory = new LinkedHashMap<>();

    public StockMarket(List<Share> shares, List<Trader> traders){
        this.shares = shares;
        this.traders = traders;
    }

    public void sellCheck(Transaction t) throws InsufficientSharesException{         // checks the number of shares in a portfolio of a SELLER
        // somehow get the key for the transaction
        ShareRecord record = t.getSeller().getPortfolio().get(t.getShare().getUniqueID());
        String sellerName = t.getSeller().getRecordName();
        String id = t.getShare().getUniqueID();

        if(record.getTotalAmount() < t.getAmount()){
            throw new InsufficientSharesException("Seller --- " + sellerName + " | Code --- " + id + ": not enough shares!");
        }
    }

    public void buyCheck(Transaction t) throws InsufficientBalanceException{    // checks the balance of the account of the BUYER
        // somehow get the key for the transaction
        double buyerBalance = t.getBuyer().getAccountBalance();
        double requiredAmount = t.getAmount()*t.getShare().getPrice();
        String buyerName = t.getBuyer().getRecordName();
        String id = t.getShare().getUniqueID();
        if(buyerBalance < requiredAmount){
            throw new InsufficientBalanceException("Buyer --- " + buyerName + " | Code --- " + id + ": not enough balance!");
        }
    }

    private void modifyPortfolios(Transaction t){
        TreeMap<String, ShareRecord> portfolioSeller = t.getSeller().getPortfolio();
        TreeMap<String, ShareRecord> portfolioBuyer = t.getBuyer().getPortfolio();
        Share share = t.getShare();

        // Seller situation
        if(portfolioSeller.get(share.getUniqueID()).getTotalAmount() == t.getAmount()){
            portfolioSeller.remove(share.getUniqueID());
        }else if(portfolioSeller.get(share.getUniqueID()).getTotalAmount() > t.getAmount()){
            portfolioSeller.get(share.getUniqueID()).setTotalAmount(-t.getAmount());
            portfolioSeller.get(share.getUniqueID()).setTotalCapital(t.getAmount()*share.getPrice());
        }
        t.getSeller().setAccountBalance(share.getPrice()*t.getAmount());
        // Buyer situation
        if(!portfolioBuyer.containsKey(share.getUniqueID())){
            portfolioBuyer.put(share.getUniqueID(), new ShareRecord(share, t.getAmount()));
        }else if(portfolioBuyer.containsKey(share.getUniqueID())){
            portfolioBuyer.get(share.getUniqueID()).setTotalAmount(t.getAmount());
            portfolioBuyer.get(share.getUniqueID()).setTotalCapital(t.getAmount()*share.getPrice());
        }
        t.getBuyer().setAccountBalance(-share.getPrice()*t.getAmount());
    }

    private void modifyHistories(LinkedHashMap<String, HistoryRecord> buyerHistory,
                                 LinkedHashMap<String, HistoryRecord> sellerHistory,
                                 Transaction t){
        // Update buyer history:
        String transactionID = transactionCodeGenerate(t.getBuyer().getRecordName(), t.getSeller().getRecordName());
        HistoryRecord hRecord = new HistoryRecord("PURCHASED", t.getShare(), t.getBuyer(), t.getSeller(), t.getAmount());
        hRecord.setDateTime(t.getDateTime());
        hRecord.setBalanceInHistory(t.getBuyer().getAccountBalance());
        hRecord.setSharesOfThisType(t.getBuyer().getPortfolio().get(t.getShare().getUniqueID()).getTotalAmount());
        buyerHistory.put(transactionID, hRecord);

        // Update seller history:
        hRecord = new HistoryRecord("SOLD", t.getShare(), t.getBuyer(), t.getSeller(), t.getAmount());
        hRecord.setDateTime(t.getDateTime());
        hRecord.setBalanceInHistory(t.getSeller().getAccountBalance());
        hRecord.setSharesOfThisType(t.getSeller().getPortfolio().get(t.getShare().getUniqueID()).getTotalAmount());
        sellerHistory.put(transactionID, hRecord);

        // StockMarket update:
        tradeHistory.put(transactionID, t);
    }

    public String transactionCodeGenerate(String sellerName, String buyerName){
        return (sellerName.substring(0,2) + buyerName.substring(0,2)).toUpperCase() + (int)((Math.random()*8999) + 1000);
    }

    public void completeTransaction(Transaction t){
//     Check 1: if passed, go to check 2 --- otherwise first  exception
//     Check 2: if passed, go outside    --- otherwise second exception
       try{
           sellCheck(t);
           buyCheck(t);
            t.setStatus(true);
            t.setDateTime(LocalDateTime.now());
       }catch(InsufficientSharesException | InsufficientBalanceException iResExc){
            iResExc.printStackTrace();
            t.setStatus(false);
       }

       if(t.getStatus()){
            modifyPortfolios(t);
            modifyHistories(t.getBuyer().getHistory(), t.getSeller().getHistory(), t);
       }else{
           // Create a window with the message, that the transaction is unsuccessful
           System.out.println("Check traders' info!!! Transaction declined...");
       }
    }

    public void setShares(List<Share> shares) {
        this.shares = shares;
    }

    public void setTraders(List<Trader> traders) {
        this.traders = traders;
    }

    public LinkedHashMap<String, Transaction> getTradeHistory() {
        return tradeHistory;
    }

    public void printMarketHistory(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss");
        System.out.println("Transactions for today\n");
        System.out.println("| Transaction date/time | Transaction ID |   Buying side   |   Selling side   |  Share ID  | # of shares |  Amount  |");
        System.out.println("---------------------------------------------------------------------------------------------------------------------");
        for(String key : getTradeHistory().keySet()){
            Transaction t = getTradeHistory().get(key);
            String date = t.getDateTime().format(format);
            System.out.println("  " + date + "       " + key + "       " + t.getBuyer().getRecordName() + "      " + t.getSeller().getRecordName() + "        "
                                + t.getShare().getUniqueID() + "          " + t.getAmount() + "         " + t.getTotal());
        }
    }
}
