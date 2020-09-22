package com.shitikov.port.entity.state;

import com.shitikov.port.entity.Ship;

public interface ShipState {
    void arrivePier(Ship ship);

    void unloadContainers(Ship ship);

    void loadContainers(Ship ship);

    void departPier(Ship ship);
}
