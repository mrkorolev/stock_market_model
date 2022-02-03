package com.company;

import com.company.logic.*;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class Main {

    public static void main(String[] args) {

        // Пример проведения транзакции, через создание акций, трейдеров и StockMarket.
        // Для улучшения:
        // 1) создать уникальное кол-во акций, через config или БД
        // 2) убрать уникальный код акции из объекта акции
        // 3) исп. графические элементы для создания интерфейса ОДНОГО ТРЕЙДЕРА, которым является пользователь


        Share s1 = new Share(50.00, "Apple");
        Share s2 = new Share(10.50, "Tesla");
        Share s3 = new Share(50.00, "Mercedes");
        Share s4 = new Share(50.00, "Samsung");

        Trader t1 = new Trader("Dmitry Ivanov", 20000);
        t1.getPortfolio().put(s2.getUniqueID(), new ShareRecord(s2, 50));

        Trader t2 = new Trader("Oleg Smirnov", 15000);
        t2.getPortfolio().put(s1.getUniqueID(), new ShareRecord(s1, 10));
        t2.getPortfolio().put(s2.getUniqueID(), new ShareRecord(s2, 50));
        t2.getPortfolio().put(s4.getUniqueID(), new ShareRecord(s4, 25));


        StockMarket sm = new StockMarket(List.of(s1, s2, s3, s4), List.of(t1, t2));
        sm.setTraders(List.of(t1, t2));
        sm.setShares(List.of(s1, s2, s3, s4));


        sm.completeTransaction(new Transaction(s1, t1, t2, 7));
        // Имитация отработки транзакции (по желанию раскомментировать)
//        try{
//            TimeUnit.SECONDS.sleep(5);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
        sm.completeTransaction(new Transaction(s2, t1, t2,25));
//        t1.printTraderHistory();
//        t2.printTraderHistory();
        sm.printMarketHistory();
    }
}
