package fr.pantheonsorbonne.miage.board;

import fr.pantheonsorbonne.miage.ship.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BattleshipGridTest {

    private BattleshipGrid grid;

    @BeforeEach
    public void setUp() {
        grid = new BattleshipGrid();
    }

    @Test
    public void testGridInitialization() {
        assertNotNull(grid, "Grid should not be null");
        assertEquals(10, grid.getSIZE(), "Grid size should be 10");
    }

    @Test
    public void testPlaceShip() {
        Ship ship = new Ship(3, false);
        boolean result = grid.placeShip(ship, 0, 0, true);
        assertTrue(result, "Ship should be placed successfully");
    }

    // Additional tests for placing ships, hitting cells, etc.
}
