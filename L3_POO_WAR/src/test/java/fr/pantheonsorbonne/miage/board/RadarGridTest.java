package fr.pantheonsorbonne.miage.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RadarGridTest {

    private RadarGrid radarGrid;
    private BattleshipGrid battleshipGrid;

    @BeforeEach
    public void setUp() {
        battleshipGrid = new BattleshipGrid();
        radarGrid = new RadarGrid(battleshipGrid);
    }

    @Test
    public void testScanSingleCell() {
        radarGrid.scan(0, 0);
        assertTrue(battleshipGrid.getCell(0, 0).isScannedByRadar(), "Cell at (0, 0) should be scanned");
    }

    @Test
    public void testRadarScan3x3Area() {
        radarGrid.radarScan(1, 1);
        for (int x = 0; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {
                assertTrue(battleshipGrid.getCell(x, y).isScannedByRadar(),
                        "Cell at (" + x + ", " + y + ") should be scanned");
            }
        }
    }

    /*
     * @Test
     * public void testScanOutsideBounds() {
     * try {
     * radarGrid.scan(-1, -1);
     * fail("Expected IllegalArgumentException to be thrown, but nothing was thrown."
     * );
     * } catch (IllegalArgumentException e) {
     * String expectedMessage =
     * "Scanning outside the grid should throw an exception";
     * String actualMessage = e.getMessage();
     * assertEquals(expectedMessage, actualMessage);
     * }
     * }
     */
}
