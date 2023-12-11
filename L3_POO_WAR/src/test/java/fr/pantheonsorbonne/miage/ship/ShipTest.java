package fr.pantheonsorbonne.miage.ship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.ship.Ship.Coordinate;

public class ShipTest {

    private Ship ship;

    @BeforeEach
    public void setUp() {
        ship = new Ship(3, true); // Exemple avec une taille de 3 et un système de défense
    }

    @Test
    public void testInitialization() {
        assertEquals(3, ship.getSize(), "Size should be 3");
        assertTrue(ship.hasDefenseSystem(), "Should have a defense system");
        assertFalse(ship.isSunk(), "Should not be sunk initially");
        assertNotNull(ship.getHitPoints(), "Hit points array should be initialized");
        assertNotNull(ship.getPositions(), "Positions array should be initialized");
    }

    @Test
    public void testHit() {
        ship.hit(0);
        assertTrue(ship.getHitPoints()[0], "First segment should be marked as hit");
        assertFalse(ship.isSunk(), "Ship should not be sunk after one hit");
    }

    @Test
    public void testSunk() {
        for (int i = 0; i < ship.getSize(); i++) {
            ship.hit(i);
        }
        assertTrue(ship.isSunk(), "Ship should be sunk after all segments are hit");
    }

    @Test
    public void testDefenseSystem() {
        assertFalse(ship.isDefenseUsed(), "Defense system should not be used initially");
        ship.useDefense();
        assertTrue(ship.isDefenseUsed(), "Defense system should be used after activation");
    }

    /*
     * @Test
     * public void testInvalidHit() {
     * assertThrows(IllegalArgumentException.class, () -> ship.hit(-1),
     * "Hitting an invalid segment should throw an exception");
     * }
     */

    @Test
    public void testSetPositions() {
        Coordinate[] positions = { new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0) };
        ship.setPositions(positions);
        assertEquals(positions, ship.getPositions(), "Positions should be set correctly");
    }

}
