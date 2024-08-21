package com.nhnacademy;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Semaphore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Store {
    Logger logger = LogManager.getLogger(Store.class.getName());

    private Deque<String> stuffs = new ArrayDeque<>();
    private Semaphore semaphore;
    private String stuffName;

    public Store(String stuffName, Semaphore semaphore) {
        this.semaphore = semaphore;
        this.stuffName = stuffName;
        this.stuffs = new ArrayDeque<>(Mart.STUFF_COUNT.get(stuffName));
    }

    public void buy(String stuffName) {
        if (!stuffs.isEmpty()) {
            if (semaphore.tryAcquire()) {
                stuffs.pop();
                logger.info("{} 을 구매했다. 재고: {}", stuffName, stuffs.size());
                sell(stuffName);
                semaphore.release();
            } 
        } else {
            logger.warn("{} 재고가 없습니다.", stuffName);
        }
    }

    public void sell(String stuff) {
        logger.info("{} 을 팔았다.", stuff);
    }

    public Deque<String> getStuffs() {
        return stuffs;
    }

    public void setStuff(String stuff) {
        if (stuffs.size() < Mart.STUFF_COUNT.get(stuff)) {
            stuffs.add(stuff);
            logger.info("{} 을 채웠다.", stuff);
        } else {
            logger.warn("납품 포기");
        }
    }

    public String getStuffName() {
        
        return stuffName;
    }
}