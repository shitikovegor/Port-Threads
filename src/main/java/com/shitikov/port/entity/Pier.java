package com.shitikov.port.entity;

public class Pier {
    private int pierId;

    public Pier(int pierId) {
        this.pierId = pierId;
    }

    public int getPierId() {
        return pierId;
    }

    public Pier setPierId(int pierId) {
        this.pierId = pierId;
        return this;
    }

    public void unloadToWarehouse(Ship ship) {
        Warehouse warehouse = Warehouse.getInstance();
        warehouse.unload(ship);
    }

    public void loadFromWarehouse(Ship ship) {
        Warehouse warehouse = Warehouse.getInstance();
        warehouse.load(ship);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pier pier = (Pier) o;

        return pierId == pier.pierId;
    }

    @Override
    public int hashCode() {
        return pierId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pier{");
        sb.append("pierId=").append(pierId);
        sb.append('}');
        return sb.toString();
    }
}
