package com.shitikov.port.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Warehouse {
    private static final Logger logger = LogManager.getLogger();
    private static Warehouse instance = new Warehouse();
    private int containersInWarehouse;
    private int warehouseCapacity;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private Warehouse() {
        this.containersInWarehouse = 5;
        this.warehouseCapacity = 10;
    }

    public static Warehouse getInstance() {
        return instance;
    }

    public int getContainersInWarehouse() {
        return containersInWarehouse;
    }

    public Warehouse setContainersInWarehouse(int containersInWarehouse) {
        this.containersInWarehouse = containersInWarehouse;
        return this;
    }

    public int getWarehouseCapacity() {
        return warehouseCapacity;
    }

    public Warehouse setWarehouseCapacity(int warehouseCapacity) {
        this.warehouseCapacity = warehouseCapacity;
        return this;
    }

    public boolean isFull() {
        return containersInWarehouse == warehouseCapacity;
    }

    public boolean isEmpty() {
        return containersInWarehouse <= 0;
    }

    public void unload(Ship ship) {
        try {
            lock.lock();
            while (ship.removeContainer() && !isFull()) {
                containersInWarehouse++;
                logger.log(Level.INFO, "Container removed from ship {}, number of containers - {}",
                        ship, containersInWarehouse);
            }
        } finally {
            condition.signalAll();
            lock.unlock();
        }
    }

    public void load(Ship ship) {
        try {
            lock.lock();
            while (ship.addContainer() && !isEmpty()) {
                containersInWarehouse--;
                logger.log(Level.INFO, "Container added to ship {}, number of containers - {}, max capacity - {}",
                        ship, containersInWarehouse, warehouseCapacity);
            }
        } finally {
            condition.signalAll();
            lock.unlock();
        }
    }
}
