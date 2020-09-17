package com.shitikov.port.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class Warehouse {
    private static final Logger logger = LogManager.getLogger();
    private static final int WAREHOUSE_CAPACITY = 6;
    private static Warehouse instance = new Warehouse();
    private AtomicInteger containersInWarehouse = new AtomicInteger();

    private Warehouse() {
    }

    public static Warehouse getInstance() {
        return instance;
    }

    public AtomicInteger getContainersInWarehouse() {
        return containersInWarehouse;
    }

    public Warehouse setContainersInWarehouse(AtomicInteger containersInWarehouse) {
        this.containersInWarehouse = containersInWarehouse;
        return this;
    }

    public int getWarehouseCapacity() {
        return WAREHOUSE_CAPACITY;
    }

    public boolean isFull() {
        return containersInWarehouse.get() == WAREHOUSE_CAPACITY;
    }

    public boolean isEmpty() {
        return containersInWarehouse.get() <= 0;
    }

    public void unload(Ship ship) {
        while (ship.removeContainer() && !isFull()) {
            containersInWarehouse.incrementAndGet();
            logger.log(Level.INFO, "Container removed from ship {}, number of containers - {}",
                    ship, containersInWarehouse);
        }
        if (isFull()) {
            logger.log(Level.INFO, "Warehouse is full.");
        }
    }

    public void load(Ship ship) {
        while (ship.addContainer() && !isEmpty()) {
            containersInWarehouse.decrementAndGet();
            logger.log(Level.INFO, "Container added to ship {}, number of containers - {}, max capacity - {}",
                    ship, containersInWarehouse, WAREHOUSE_CAPACITY);
        }
        if (isEmpty()) {
            logger.log(Level.INFO, "Warehouse is empty.");
        }
    }
}
