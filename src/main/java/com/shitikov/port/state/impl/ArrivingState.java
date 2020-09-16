package com.shitikov.port.state.impl;

import com.shitikov.port.entity.Pier;
import com.shitikov.port.entity.Port;
import com.shitikov.port.entity.Ship;
import com.shitikov.port.state.ShipState;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class ArrivingState implements ShipState {
    private static Logger logger = LogManager.getLogger();

    @Override
    public void arrivePier(Ship ship) {
        Port port = Port.getInstance();
        Optional<Pier> pier = port.arrivePier();
        ship.setPier(pier);
        logger.log(Level.INFO, "Ship {} is in Port. Pier {}", ship.getName(), pier.get().getPierId());

        ship.setShipState(new UnloadingState());
    }

    @Override
    public void unloadContainers(Ship ship) {
        logger.log(Level.WARN, "this action is impossible, ship {} isn't in Port.", ship.getName());
    }

    @Override
    public void loadContainers(Ship ship) {
        logger.log(Level.WARN, "this action is impossible, ship {} isn't in Port.", ship.getName());
    }

    @Override
    public void departPier(Ship ship) {
        logger.log(Level.WARN, "this action is impossible, ship {} isn't in Port.", ship.getName());
    }
}
