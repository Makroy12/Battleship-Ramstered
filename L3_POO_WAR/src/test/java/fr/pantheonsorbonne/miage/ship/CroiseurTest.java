package fr.pantheonsorbonne.miage.ship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CroiseurTest {

    private Croiseur croiseur;

    @BeforeEach
    public void setUp() {
        croiseur = new Croiseur();
    }

    @Test
    public void testInitialization() {
        assertEquals(4, croiseur.getSize(), "Size should be 4");
        assertFalse(croiseur.hasDefenseSystem(), "Should not have a defense system");
    }

    @Test
    public void testHit() {
        croiseur.hit(0);
        // Add assertions to check if the first segment is hit
        // Optionally, check that the ship is not sunk after a single hit
    }

    @Test
    public void testIsSunk() {
        // Hit all segments and then check if the ship is sunk
        for (int i = 0; i < croiseur.getSize(); i++) {
            croiseur.hit(i);
        }
        assertTrue(croiseur.isSunk(), "Croiseur should be sunk after all segments are hit");
    }

}
