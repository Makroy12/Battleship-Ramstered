package fr.pantheonsorbonne.miage.board;

import fr.pantheonsorbonne.miage.ship.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    private Cell cell;

    @BeforeEach
    public void setUp() {
        cell = new Cell(0, 0);
    }

    @Test
    public void testInitialState() {
        assertEquals(CellState.WATER, cell.getState(), "Initial state should be WATER");
    }

    @Test
    public void testSetShip() {
        Ship ship = new Ship(3, false);
        cell.setShip(ship);
        assertTrue(cell.hasShip(), "Cell should have a ship");
    }

    // Additional tests for setting mines, scanning, hitting cells, etc.
}
