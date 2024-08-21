package com.nhnacademy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mart {
    static Logger logger = LogManager.getLogger(Mart.class.getName());
    private List<Store> stores = new ArrayList<>(5);

    protected static final Map<String, Integer> STUFF_COUNT = new HashMap<>() {
        {
            put("사과", 5);
            put("바나나", 3);
            put("메론", 4);
            put("키위", 7);
            put("딸기", 10);
        }
    };

    public List<Store> getStores() {
        return stores;
    }

    public void consumerEnter(ExecutorService consumerExecutor) {
        consumerExecutor.submit(new Consumer(getStores()));
        logger.info("고객이 들어왔다.");
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 11000));
            logger.info("고객 대기 중..");
        } catch (InterruptedException e) {
            Thread.interrupted();
            Thread.currentThread().interrupt();
        }
    }

    public void producerEnter(ExecutorService producerExecutor) {
        producerExecutor.submit(new Producer(getStores()));
        logger.info("생산자가 들어왔다.");
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 11000));
            logger.info("생산자 대기 중..");
        } catch (InterruptedException e) {
            Thread.interrupted();
            Thread.currentThread().interrupt();
        }
    }

    public static void exit() {
        logger.info("고객이 나갔다.");
    }
}

class Test {
    static Logger logger = LogManager.getLogger(Test.class.getName());
    public static void main(String[] args) {
        Mart mart = new Mart();
        ThreadFactory daemonThreadFactory = new DaemonThreadFactory();
        ExecutorService producerExecutor = Executors.newFixedThreadPool(2, daemonThreadFactory);
        ExecutorService consumerExecutor = Executors.newFixedThreadPool(3, daemonThreadFactory);
        
        SelfRunnableCounter runnableCounter1 = new SelfRunnableCounter("counter1", 60000);
        runnableCounter1.start();

        for (String stuff : Mart.STUFF_COUNT.keySet()) {
            mart.getStores().add(new Store(stuff, new Semaphore(Mart.STUFF_COUNT.get(stuff))));
        }
        
        while (runnableCounter1.isAlive()) {
            mart.producerEnter(producerExecutor);
            mart.consumerEnter(consumerExecutor);
        }

        producerExecutor.shutdown();
        consumerExecutor.shutdown();

        while (!producerExecutor.isShutdown() || !consumerExecutor.isShutdown()) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        while (true) {
            if (producerExecutor.isShutdown() && consumerExecutor.isShutdown()) {
                logger.info("영업 종료");
                break;
            }
        }
    }
    
    static class DaemonThreadFactory implements ThreadFactory {
        public Thread newThread(Runnable r) {
            Thread thread = Executors.defaultThreadFactory().newThread(r);
            thread.setDaemon(true);
            return thread;
        }
    }
}
