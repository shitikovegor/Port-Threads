package com.shitikov.port.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static final int PIER_NUMBER = 3;
    private static Port instance = new Port();
    private static Logger logger = LogManager.getLogger();
    private Deque<Pier> freePiers;
    private Queue<Pier> occupiedPiers;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();


    private Port() {
        this.occupiedPiers = new LinkedList<>();
        this.freePiers = new ArrayDeque<>();
        for (int i = 0; i < PIER_NUMBER; i++) {
            freePiers.add(new Pier(i + 1));
        }
    }

    public static Port getInstance() {
        return instance;
    }

    public int getPierNumber() {
        return PIER_NUMBER;
    }

    public Deque<Pier> getFreePiers() {
        return freePiers;
    }

    public Queue<Pier> getOccupiedPiers() {
        return occupiedPiers;
    }

    public Optional<Pier> arrivePier() {
        Optional<Pier> pierOpt = Optional.empty();
        try {
            lock.lock();
            while (freePiers.isEmpty()) {
                logger.log(Level.INFO, "No free piers. ");
                condition.await();
            }
            Pier pier = freePiers.poll();
            occupiedPiers.offer(pier);
            pierOpt = Optional.of(pier);
        } catch (InterruptedException e) {
            logger.log(Level.WARN, "Thread error. ", e);
        } finally {
            lock.unlock();
        }
        return pierOpt;
    }

    public void departPier(Pier pier) {
        try {
            lock.lock();
            freePiers.offer(pier);
            occupiedPiers.remove();
        } finally {
            condition.signal();
            lock.unlock();
        }
    }
}
