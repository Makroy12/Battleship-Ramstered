package fr.pantheonsorbonne.miage.ship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class ContreTorpilleurTest {

    private ContreTorpilleur contreTorpilleur;

    @BeforeEach
    public void setUp() {
        contreTorpilleur = new ContreTorpilleur();
    }

    @Test
    public void testInitialization() {
        assertEquals(3, contreTorpilleur.getSize(), "Size should be 3");
        assertFalse(contreTorpilleur.hasDefenseSystem(), "Should not have a defense system");
    }

    @Test
    public void testHit() {
        contreTorpilleur.hit(0);
        // Add assertions to check if the first segment is hit
    }

    @Test
    public void testIsSunk() {
        // Hit all segments and then check if the ship is sunk
    }

    // Additional tests as needed
}
