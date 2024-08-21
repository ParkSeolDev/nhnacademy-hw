package com.nhnacademy;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Consumer implements Runnable {
    Logger logger = LogManager.getLogger(Consumer.class.getName());

    List<Store> stores;

    public Consumer(List<Store> stores) {
        this.stores = stores;
    }

    @Override
    public void run() {
        Random rand = new Random();
        int randomCount = rand.nextInt(stores.size());
        Collections.shuffle(stores);
        try {
            logger.info("구매 대기 중..");
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
            
            for (int i = 0; i < randomCount; i++) {
                Store store = stores.get(i);
                String stuffName = store.getStuffName();
                store.buy(stuffName);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Mart.exit();
    }
    
}
