package com.shitikov.port.creator;

import com.shitikov.port.entity.Ship;
import com.shitikov.port.exception.ProjectException;

public class ShipCreator {

    public Ship createShip(String name, int containerNumber, int capacity) throws ProjectException {
        if (containerNumber > capacity) {
            throw new ProjectException("Number of containers must be less capacity.");
        }

        return new Ship(name, containerNumber, capacity);
    }
}
