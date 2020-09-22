package com.shitikov.port.entity;

import com.shitikov.port.entity.state.ShipState;
import com.shitikov.port.entity.state.impl.ArrivingState;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.concurrent.Callable;

public class Ship implements Callable<Ship> {
    private static Logger logger = LogManager.getLogger();
    private String name;
    private int containersNumber;
    private int containerCapacity;
    private Optional<Pier> pier;
    private ShipState shipState;


    public Ship(String name, int containersNumber, int containerCapacity) {
        this.name = name;
        this.containersNumber = containersNumber;
        this.containerCapacity = containerCapacity;
        this.pier = Optional.empty();
        this.shipState = new ArrivingState();
    }

    public String getName() {
        return name;
    }

    public Ship setName(String name) {
        this.name = name;
        return this;
    }

    public int getContainersNumber() {
        return containersNumber;
    }

    public Ship setContainersNumber(int containersNumber) {
        this.containersNumber = containersNumber;
        return this;
    }

    public int getContainerCapacity() {
        return containerCapacity;
    }

    public Ship setContainerCapacity(int containerCapacity) {
        this.containerCapacity = containerCapacity;
        return this;
    }

    public Optional<Pier> getPier() {
        return pier;
    }

    public Ship setPier(Optional<Pier> pier) {
        this.pier = pier;
        return this;
    }

    public ShipState getShipState() {
        return shipState;
    }

    public Ship setShipState(ShipState shipState) {
        this.shipState = shipState;
        return this;
    }

    public boolean addContainer() {
        boolean result = false;

        if (containersNumber < containerCapacity) {
            containersNumber++;
            result = true;
        } else {
            logger.log(Level.INFO, "Ship {} is full. ", this);
        }
        return result;
    }

    public boolean removeContainer() {
        boolean result = false;

        if (containersNumber > 0) {
            containersNumber--;
            result = true;
        } else {
            logger.log(Level.INFO, "Ship {} doesn't have containers. ", this);
        }
        return result;
    }

    @Override
    public Ship call() throws Exception {
        shipState.arrivePier(this);
        shipState.unloadContainers(this);
        shipState.loadContainers(this);
        shipState.departPier(this);

        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Ship other = (Ship) obj;

        if (containersNumber != other.containersNumber)
            return false;
        if (containerCapacity != other.containerCapacity)
            return false;
        if (name != null ? !name.equals(other.name) : other.name != null)
            return false;
        if (pier.isPresent() ? !pier.equals(other.pier) : other.pier.isPresent())
            return false;
        return shipState != null ? shipState.equals(other.shipState) : other.shipState == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + containersNumber;
        result = 31 * result + containerCapacity;
        result = 31 * result + (pier.isPresent() ? pier.hashCode() : 0);
        result = 31 * result + (shipState != null ? shipState.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ship{");
        sb.append("name='").append(name).append('\'');
        sb.append(", containersNumber=").append(containersNumber);
        sb.append(", containerCapacity=").append(containerCapacity);
        sb.append(", shipState=").append(shipState.getClass().getSimpleName());
        sb.append('}');
        return sb.toString();
    }
}
