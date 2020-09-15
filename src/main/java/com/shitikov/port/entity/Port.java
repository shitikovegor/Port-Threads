package com.shitikov.port.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static Port instance = new Port();
    private static Logger logger = LogManager.getLogger();
    Deque<Ship> ships;
    private int pierNumber;
    private Warehouse warehouse;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();


    private Port() {
        this.pierNumber = 3;
        this.ships = new ArrayDeque<>();
        this.warehouse = Warehouse.getInstance();
    }

    public static Port getInstance() {
        return instance;
    }

    public int getPierNumber() {
        return pierNumber;
    }

    public Port setPierNumber(int pierNumber) {
        this.pierNumber = pierNumber;
        return this;
    }

    public Deque<Ship> getShips() {
        return ships;
    }

    public Port setShips(Deque<Ship> ships) {
        this.ships = ships;
        return this;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void add(Ship ship) {
        try {
            lock.lock();
            while (ships.size() == pierNumber) {
                logger.log(Level.INFO, "No free piers. ");
                condition.await();
            }
            ships.add(ship);
            logger.log(Level.INFO, "Ship {} is in Port. ", ship);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Thread error. ", e);
        } finally {
            condition.signalAll();
            lock.unlock();
        }
    }

    public void remove(Ship ship) {
        try {
            lock.lock();
            ships.remove(ship);
            logger.log(Level.INFO, "Ship {} sailed out of Port. ", ship);
        } finally {
            condition.signalAll();
            lock.unlock();
        }
    }
}
