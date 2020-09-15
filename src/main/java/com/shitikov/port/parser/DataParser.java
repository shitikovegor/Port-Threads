package com.shitikov.port.parser;

import com.shitikov.port.creator.ShipCreator;
import com.shitikov.port.entity.Ship;
import com.shitikov.port.exception.ProjectException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class DataParser {
    private static DataParser instance = new DataParser();
    private static Logger logger = LogManager.getLogger();

    private DataParser() {
    }

    public static DataParser getInstance() {
        return instance;
    }

    public List<Ship> parseShips(List<String> dataShips) {
        ShipCreator creator = new ShipCreator();
        List<Ship> ships = new ArrayList<>();

        for (String textString : dataShips) {
            String[] data = textString.split(",");
            String name = data[0].strip();
            int containerNumber = Integer.parseInt(data[1].strip());
            int capacity = Integer.parseInt(data[2].strip());

            try {
                Ship ship = creator.createShip(name, containerNumber, capacity);
                ships.add(ship);
            } catch (ProjectException e) {
                logger.log(Level.ERROR, "Incorrect data. ", e);
            }
        }
        return ships;
    }
}
