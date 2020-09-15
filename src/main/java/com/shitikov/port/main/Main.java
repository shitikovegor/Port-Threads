package com.shitikov.port.main;

import com.shitikov.port.entity.Ship;
import com.shitikov.port.exception.ProjectException;
import com.shitikov.port.parser.DataParser;
import com.shitikov.port.reader.DataReader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {

        ExecutorService es = Executors.newFixedThreadPool(10);
        List<Ship> ships = new ArrayList<>();

        try {
            List<String> shipsData = DataReader.getInstance().readFile("input/ships.txt");
            ships = DataParser.getInstance().parseShips(shipsData);
        } catch (ProjectException e) {
            logger.log(Level.ERROR, "Error in time of reading file. ", e);
        }
        for (Ship ship : ships) {
            es.submit(ship);
        }
        es.shutdown();
    }
}
