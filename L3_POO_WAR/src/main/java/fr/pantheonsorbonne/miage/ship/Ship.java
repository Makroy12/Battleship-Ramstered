package fr.pantheonsorbonne.miage.ship;

import java.util.Arrays;

public class Ship {
    private int size;

    public void setSize(int size) {
        this.size = size;
    }

    private boolean[] hitPoints;

    public boolean[] getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(boolean[] hitPoints) {
        this.hitPoints = hitPoints;
    }

    private Coordinate[] positions; // Position of each segment of the ship on the board

    public Coordinate[] getPositions() {
        return positions;
    }

    private boolean hasDefenseSystem; // Indicates if the ship has a defense system

    public boolean isHasDefenseSystem() {
        return hasDefenseSystem;
    }

    public void setHasDefenseSystem(boolean hasDefenseSystem) {
        this.hasDefenseSystem = hasDefenseSystem;
    }

    private boolean defenseUsed; // Indicates if the defense system has been used

    public void setDefenseUsed(boolean defenseUsed) {
        this.defenseUsed = defenseUsed;
    }

    public Ship(int size, boolean hasDefenseSystem) {
        this.size = size;
        this.hitPoints = new boolean[size];
        this.positions = new Coordinate[size];
        for (int i = 0; i < size; i++) {
            this.hitPoints[i] = false;
            this.positions[i] = new Coordinate(-1, -1); // Initialize with invalid positions
        }
        this.hasDefenseSystem = hasDefenseSystem;
        this.defenseUsed = false;
    }

    public boolean hasDefenseSystem() {
        return hasDefenseSystem;
    }

    public boolean isDefenseUsed() {
        return defenseUsed;
    }

    public void useDefense() {
        if (hasDefenseSystem && !defenseUsed) {
            defenseUsed = true;
        }
    }

    public boolean isSunk() {
        for (boolean hit : hitPoints) {
            if (!hit) {
                return false;
            }
        }
        return true;
    }

    public void hit(int segment) {
        if (segment >= 0 && segment < size) {
            hitPoints[segment] = true;
        }
    }

    public static class Coordinate {
        private int x;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        private int y;

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // Getters

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    public int getSegmentIndex(int x, int y) {
        for (int i = 0; i < positions.length; i++) {
            if (positions[i].getX() == x && positions[i].getY() == y) {
                return i;
            }
        }
        return -1; // Indicate that the segment was not found
    }

    public int getSize() {
        return this.size;
    }

    public void setPositions(Coordinate[] positions) {
        this.positions = positions;
    }

    public Ship(Ship ship) {
        this.size = ship.size;
        this.hitPoints = Arrays.copyOf(ship.hitPoints, ship.hitPoints.length);
        this.positions = Arrays.copyOf(ship.positions, ship.positions.length);
        this.hasDefenseSystem = ship.hasDefenseSystem;
        this.defenseUsed = ship.defenseUsed;
    }

    public void sink() {
    }

}
