package fr.pantheonsorbonne.miage.ship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PorteAvionTest {

    private PorteAvion porteAvion;

    @BeforeEach
    public void setUp() {
        porteAvion = new PorteAvion();
    }

    @Test
    public void testInitialization() {
        assertEquals(5, porteAvion.getSize(), "Size should be 5");
        assertFalse(porteAvion.hasDefenseSystem(), "Should not have a defense system");
    }

    @Test
    public void testHit() {
        porteAvion.hit(0);
        // Add assertions to check if the first segment is hit
        // Optionally, check that the ship is not sunk after a single hit
    }

    @Test
    public void testIsSunk() {
        // Hit all segments and then check if the ship is sunk
        for (int i = 0; i < porteAvion.getSize(); i++) {
            porteAvion.hit(i);
        }
        assertTrue(porteAvion.isSunk(), "PorteAvion should be sunk after all segments are hit");
    }

    // Additional tests as needed
}
