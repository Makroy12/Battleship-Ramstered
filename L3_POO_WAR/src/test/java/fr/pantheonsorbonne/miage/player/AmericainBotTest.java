package fr.pantheonsorbonne.miage.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.board.BattleshipGrid;
import fr.pantheonsorbonne.miage.board.SpecialEffectType;
import fr.pantheonsorbonne.miage.ship.ContreTorpilleur;
import fr.pantheonsorbonne.miage.ship.Croiseur;
import fr.pantheonsorbonne.miage.ship.PorteAvion;
import fr.pantheonsorbonne.miage.ship.Ship;
import fr.pantheonsorbonne.miage.ship.SousMarin;
import fr.pantheonsorbonne.miage.ship.Tropilleur;

public class AmericainBotTest {

    private NaziBot naziBot;
    private BattleshipGrid grid;
    private List<Ship> ships;

    @BeforeEach
    public void setUp() {
        grid = new BattleshipGrid();
        ships = new ArrayList<>();
        ships.add(new PorteAvion());
        ships.add(new Croiseur());
        ships.add(new ContreTorpilleur());
        ships.add(new SousMarin());
        ships.add(new Tropilleur());
        naziBot = new NaziBot("NaziBot", grid, ships);
    }

    @Test
    public void testSetupDefenseShips() {
        // Ensure that exactly 2 ships have defense systems
        naziBot.setupDefenseShips();
        int shipsWithDefense = 0;
        for (Ship ship : ships) {
            if (ship.hasDefenseSystem()) {
                shipsWithDefense++;
            }
        }
        assertEquals(2, shipsWithDefense);
    }

    @Test
    public void testDecideAction() {
        // Ensure that the decideAction method doesn't throw exceptions
        PlayerAction action = naziBot.decideAction();
        assertNotNull(action);
    }

    @Test
    public void testIsEffectAvailable() {
        // Ensure that all special effects are initially available
        for (SpecialEffectType effectType : SpecialEffectType.values()) {
            assertTrue(naziBot.isEffectAvailable(effectType));
        }

        // Mark one effect as used and check if it's no longer available
        SpecialEffectType effectType = SpecialEffectType.RADAR;
        naziBot.markEffectAsUsed(effectType);
        assertFalse(naziBot.isEffectAvailable(effectType));
    }
}
