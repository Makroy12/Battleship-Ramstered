package fr.pantheonsorbonne.miage.board;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import fr.pantheonsorbonne.miage.ship.Ship;

public class BattleshipGrid {

    private final int SIZE = 10;

    public int getSIZE() {
        return SIZE;
    }

    private Cell[][] grid = new Cell[SIZE][SIZE];
    private List<Ship> ships = new ArrayList<>();

    public BattleshipGrid() {
        // Initialize the grid with empty cells
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
    }

    public Cell getCell(int x, int y) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            return grid[x][y];
        } else {
            // Handle the case where the coordinates are out of bounds
            // You could throw an exception or return a default cell
            throw new IllegalArgumentException("Coordinates out of bounds");
        }
    }

    public boolean placeShip(Ship ship, int startX, int startY, boolean isHorizontal) {
        List<Ship.Coordinate> shipCoordinates = new ArrayList<>();
        // Check if ship can be placed and collect coordinates
        for (int i = 0; i < ship.getSize(); i++) {
            int x = startX + (isHorizontal ? i : 0);
            int y = startY + (isHorizontal ? 0 : i);
            if (!isValidPlacement(x, y)) {
                return false; // Ship cannot be placed here
            }
            shipCoordinates.add(new Ship.Coordinate(x, y));
        }
        // Place the ship and update cell states
        for (Ship.Coordinate coord : shipCoordinates) {
            Cell cell = grid[coord.getX()][coord.getY()];
            cell.setShip(ship);
            cell.setShip(ship);
            ;
        }
        ships.add(ship);
        return true;
    }

    private boolean isValidPlacement(int x, int y) {
        if (x >= SIZE || y >= SIZE || grid[x][y].hasShip()) {
            return false;
        }
        // Check adjacent cells
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int checkX = x + dx;
                int checkY = y + dy;
                if (checkX >= 0 && checkX < SIZE && checkY >= 0 && checkY < SIZE) {
                    if (grid[checkX][checkY].hasShip()) {
                        return false; // Adjacent ship present
                    }
                }
            }
        }
        return true;
    }

    public boolean placeMine(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            return false; // Out of bounds
        }
        Cell cell = grid[x][y];
        if (cell.hasShip() || cell.hasMine()) {
            return false; // Cannot place a mine here
        }
        cell.setMine(true);
        return true;
    }

    public String hit(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            return "Out of Bounds";
        }

        Cell targetCell = grid[x][y];

        // Check for mine
        if (targetCell.hasMine()) {
            targetCell.hit();
            return "Mine Hit";
        }

        // Check for ship
        if (targetCell.hasShip()) {
            Ship hitShip = targetCell.getShip();
            int segmentIndex = getSegmentIndex(hitShip, x, y);
            if (hitShip.hasDefenseSystem() && !hitShip.isDefenseUsed()) {
                hitShip.setDefenseUsed(true);
                return "Defense Activated";
            }

            hitShip.hit(segmentIndex); // Assume hit() method marks the segment as hit
            targetCell.hit();

            if (hitShip.isSunk()) {
                return "Ship Sunk";
            } else {
                return "Ship Hit";
            }
        }

        // Handle a miss
        targetCell.hit();
        return "Miss";
    }

    private int getSegmentIndex(Ship ship, int x, int y) {
        for (int i = 0; i < ship.getSize(); i++) {
            Ship.Coordinate coord = ship.getPositions()[i];
            if (coord.getX() == x && coord.getY() == y) {
                return i; // Found the matching segment index
            }
        }
        return -1; // Segment not found for the given coordinates
    }

    public boolean areAllShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false; // Found a ship that's not yet sunk
            }
        }
        return true; // All ships have been sunk
    }

    public void updateShipSunkStatus(Ship sunkShip) {
        // Iterate through all the positions of the sunk ship
        for (Ship.Coordinate coord : sunkShip.getPositions()) {
            Cell cell = grid[coord.getX()][coord.getY()];
            if (cell.getShip() == sunkShip) {
                // Update the cell state to reflect that the ship part here is sunk
                cell.setState(CellState.SUNK_SHIP);
            }
        }
        // Additional logic for handling a sunk ship, if necessary
    }

    public void displayGrid() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                Cell cell = grid[x][y];
                if (cell.isScannedByRadar() || cell.isHit()) {
                    System.out.print(cell.getState().getDisplayCharacter());
                } else {
                    // Hide the actual content of the cell if it hasn't been scanned or hit
                    System.out.print('.');
                }
            }
            System.out.println(); // New line after each row
        }
    }

    public boolean placeShip(int i, int j) {
        return false;
    }

    public Integer getNumberOfMines() {
        return null;
    }

    public BooleanSupplier isShipPlaced(Ship ship) {
        return null;
    }

}
