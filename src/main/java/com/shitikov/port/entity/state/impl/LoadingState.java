package com.shitikov.port.entity.state.impl;

import com.shitikov.port.entity.Pier;
import com.shitikov.port.entity.Port;
import com.shitikov.port.entity.Ship;
import com.shitikov.port.entity.state.ShipState;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class LoadingState implements ShipState {
    private static Logger logger = LogManager.getLogger();

    @Override
    public void arrivePier(Ship ship) {
        logger.log(Level.WARN, "this action is impossible, state of ship {} is - Loading.", ship.getName());
    }

    @Override
    public void unloadContainers(Ship ship) {
        logger.log(Level.WARN, "this action is impossible, state of ship {} is - Loading.", ship.getName());
    }

    @Override
    public void loadContainers(Ship ship) {
        Optional<Pier> pierOptional = ship.getPier();
        if (pierOptional.isPresent()) {
            Pier pier = pierOptional.get();
            pier.loadFromWarehouse(ship);
        }

        ship.setShipState(new DepartingState());
    }

    @Override
    public void departPier(Ship ship) {
        Optional<Pier> pierOptional = ship.getPier();
        if (pierOptional.isPresent()) {
            Port port = Port.getInstance();
            Pier pier = pierOptional.get();
            port.departPier(pier);
            ship.setPier(Optional.empty());
            logger.log(Level.INFO, "Ship {} sailed out of Port. Pier {}", ship.getName(), pier.getPierId());
        }

        ship.setShipState(new ArrivingState());
    }
}
