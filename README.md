# stock_market_model

This project is a small model of a stock market, with two traiders exchanging their assets/shares and capital.

The logic contains the following aspects:

Classes of the project model the following aspects of trading stock: Share, Transaction,  Trader, ShareRecord, HistoryRecord, InsufficientBalanceException, 
InsufficientSharesException and StockMarket(package location: src/main/java/com/company/logic).



1) Share is the base of the project. 
It models the share in the market, and contains:  

- PRICE of the share on the market (later to be taken from a DB);
                                                                                          
- COMPANY, which it corresponds to (to be taken from the DB);
                                                                                         
- UNIQUE IDENTIFICATOR (which is to be removed and taken from a DB later).
                                                                                  
                                                                                  
2) Transactioon is be formed every time one trader is to buy from/sell to another. 
It has the following elements:      

- SHARE that is to be traded (with links to all of it's properties);
                                      
- BUYER and SELLER, both sides of the transaction;
                                     
- AMOUNT of shares of this type to be traded;
                                     
- TOTAL CAPITAL, that is to be deducted from the buyer and transferred to the seller;
                                     
- STATUS of the transaction (boolean: "true" - valid, "false" - invalid);
                                     
- DATE AND TIME of the transaction (to be changed later, 
  as it is defined locally on every machine and the market).
  
  
3) Trader obviously models the side interested in buying/selling.
Attributes:           

- the NAME of the record, which was registered with the trader;

- the BALANCE associated with this record (currency to be added later);
                                                  
- the PORTFOLIO of the trader, which is defined with the TreeMap data structure (the records put into
  the map will filter the keys with respect to alphabetical order). 
  Key: share's unique ID |  Record type: ShareRecord;
                                                    
- the HISTORY of the trader's account, which is defined with the LinkedHashMap data structure
  (getting records in the map will return them in the same order, as records were added to it).
   Key: unique transaction ID | Record type: HistoryRecord.
            
            
4) ShareRecord is the class that models the record inside the portfolio of every trader in the market.
It is similar to a share, but has some additions:

- SHARE link, which points to the share that is traded;
                                                  
- TOTAL AMOUNT of share of this type, that a specific trader pocesses.
  In case of a seller it goes down and goes up in case of a buyer; 
                                                  
- TOTAL CAPITAL that the shares of this type give to the trader (net worth).
In case of a seller it goes down and up in case of a buyer.


5) HistoryRecord is the class that models the record inside the history of every trader in the marker.
It extends the transactions class and has:
                    
- TRANSACTION TYPE, which will be set accordingly for buyer/seller;
                                                  
- BALANCE IN HISTORY that stores the balance of the trader's account right after transaction is complete;
                                                  
- SHARES OF THIS TYPE stores the amount of shares of the trader's account right after transaction is complete.
  Both these parameters are used for a visual representation of the balance after each transaction in history.
                                              
                                             
6) InsufficientBalanceException and InsufficientSharesException and corresponding to situations of the buyer not having enough balance for the transaction and
seller not having enough shares for the transaction respectively; these are thrown in situations explained below.
  

7) The heart of the project is the StockMarket class (that's where everything is supposed to be happenning in terms of transactions).
It contains the list of shares to be traded, a list of traders and the history of all transactions that take place on the market.
   
List of transactions:                             

- primary key is the unnique transaction ID, which contains the first two letters of the buyer and seller record 
  name, as well as the random number (1000 - 9999), so that there is a guarantee to generate a unique code 
  for each transaction in most cases;
                            
- under each transaction ID there is a transaction, which is generated manually in the Main.java class (for the 
  moment), so that each key maps to it's corresponding transaction - which is later to be executed through 
  stock market;
                            
                           
Each time a transaction is to take place, there is: 

- a check for the amount of shares requested for the seller (if it doesn't pass, there is a corresponding
  exception thrown, in which case a transaction will be set to invalid, and will not execute);
                                                        
- a check for the balance on the side of the buyer (same as above - doesn't pass, then the transaction
  is invalid -> corresponding exception thrown -> transaction invalid and will not execute);
                                                          
- if both checks pass, this will indicate the correctness of the transaction, and therefore will modify
  portfolios and histories of the involved traders, as well as the overall market history, after which it
  can be printed in an understandable way.


Results for the transactions, as well as the histories and portfolios to be printed with according methods, which can be found in the classes they refer to.              
