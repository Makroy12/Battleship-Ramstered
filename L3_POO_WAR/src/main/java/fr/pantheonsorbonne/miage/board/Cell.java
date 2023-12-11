package fr.pantheonsorbonne.miage.board;

import fr.pantheonsorbonne.miage.ship.Ship;

public class Cell {
    private CellState state;
    private Ship ship;
    private boolean scannedByRadar;
    private final int x; // X-coordinate of the cell
    private final int y; // Y-coordinate of the cell

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.state = CellState.WATER;
        this.ship = null;
        this.scannedByRadar = false;
    }

    // Copy constructor for cloning purposes
    public Cell(Cell other) {
        this.x = other.x;
        this.y = other.y;
        this.state = other.state;
        this.ship = other.ship != null ? new Ship(other.ship) : null;
        this.scannedByRadar = other.scannedByRadar;
    }

    // Getters for x and y
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CellState getState() {
        return this.state;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
        this.state = ship != null ? CellState.SHIP : CellState.WATER;
    }

    public void setMine(boolean hasMine) {
        this.state = hasMine ? CellState.MINE : CellState.WATER;
    }

    public void setScannedByRadar(boolean scanned) {
        this.scannedByRadar = scanned;
    }

    public boolean isScannedByRadar() {
        return scannedByRadar;
    }

    public boolean hasMine() {
        return this.state == CellState.MINE;
    }

    public boolean hasShip() {
        return this.ship != null;
    }

    public void hit() {
        if (this.state == CellState.SHIP || this.state == CellState.MINE) {
            this.state = CellState.HIT; // Ship or mine hit
        } else if (this.state == CellState.WATER) {
            this.state = CellState.MISSED; // Missed hit in water
        }
    }

    public String getDisplayCharacter(boolean isScannedByRadar) {
        if (this.state == CellState.MINE && !isScannedByRadar && !this.isHit()) {
            return "."; // Hide mine if not scanned or hit
        }
        // Convert the char to String
        return String.valueOf(this.state.getDisplayCharacter());
    }

    public boolean isHit() {
        return this.state == CellState.HIT;
    }

    public Ship getShip() {
        return this.ship;
    }

    // Reset method to reset the cell to its initial state
    public void reset() {
        this.state = CellState.WATER;
        this.ship = null;
    }

    public void setState(CellState state) {
        this.state = state;
    }
}
