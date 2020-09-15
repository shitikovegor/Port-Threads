package com.shitikov.port.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Ship implements Callable<Ship> {
    private static Logger logger = LogManager.getLogger();
    private String name;
    private int containerNumber;
    private int containerCapacity;

    public Ship(String name, int containerNumber, int containerCapacity) {
        this.name = name;
        this.containerNumber = containerNumber;
        this.containerCapacity = containerCapacity;
    }

    public String getName() {
        return name;
    }

    public Ship setName(String name) {
        this.name = name;
        return this;
    }

    public int getContainerNumber() {
        return containerNumber;
    }

    public Ship setContainerNumber(int containerNumber) {
        this.containerNumber = containerNumber;
        return this;
    }

    public int getContainerCapacity() {
        return containerCapacity;
    }

    public Ship setContainerCapacity(int containerCapacity) {
        this.containerCapacity = containerCapacity;
        return this;
    }

    public boolean isFull() {
        return containerNumber >= containerCapacity;
    }

    public boolean addContainer() {
        boolean result = false;

        if (!isFull()) {
            containerNumber++;
            result = true;
        } else {
            logger.log(Level.INFO, "Ship {} is full. ", this);
        }
        return result;
    }

    public boolean removeContainer() {
        boolean result = false;

        if (containerNumber > 0) {
            containerNumber--;
            result = true;
        } else {
            logger.log(Level.INFO, "Ship {} doesn't have containers. ", this);
        }
        return result;
    }

    @Override
    public Ship call() throws Exception {
        Warehouse warehouse = Warehouse.getInstance();
        Port port = Port.getInstance();

        port.add(this);
        TimeUnit.MILLISECONDS.sleep(100);
        warehouse.unload(this);
        TimeUnit.MILLISECONDS.sleep(100);
        warehouse.load(this);
        TimeUnit.MILLISECONDS.sleep(100);
        port.remove(this);

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ship ship = (Ship) o;

        if (containerNumber != ship.containerNumber) return false;
        if (containerCapacity != ship.containerCapacity) return false;
        return name != null ? name.equals(ship.name) : ship.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + containerNumber;
        result = 31 * result + containerCapacity;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ship{");
        sb.append("name='").append(name).append('\'');
        sb.append(", containerNumber=").append(containerNumber);
        sb.append(", containerCapacity=").append(containerCapacity);
        sb.append('}');
        return sb.toString();
    }
}
