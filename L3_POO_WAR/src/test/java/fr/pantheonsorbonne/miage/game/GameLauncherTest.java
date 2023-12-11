import fr.pantheonsorbonne.miage.board.BattleshipGrid;
import fr.pantheonsorbonne.miage.player.AmericainBot;
import fr.pantheonsorbonne.miage.player.Bot;
import fr.pantheonsorbonne.miage.player.NaziBot;
import fr.pantheonsorbonne.miage.ship.ContreTorpilleur;
import fr.pantheonsorbonne.miage.ship.Croiseur;
import fr.pantheonsorbonne.miage.ship.PorteAvion;
import fr.pantheonsorbonne.miage.ship.Ship;
import fr.pantheonsorbonne.miage.ship.SousMarin;
import fr.pantheonsorbonne.miage.ship.Tropilleur;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameLauncherTest {

    @Test
    public void testCreateBot() {
        // Test creating NaziBot
        int choice1 = 1;
        Bot bot1 = GameLauncher.createBot(choice1);
        assertTrue(bot1 instanceof NaziBot);
        assertEquals("Nazi Bot", bot1.getName());

        // Test creating AmericainBot
        int choice2 = 2;
        Bot bot2 = GameLauncher.createBot(choice2);
        assertTrue(bot2 instanceof AmericainBot);
        assertEquals("Americain Bot", bot2.getName());

        // Test creating default NaziBot for invalid choice
        int invalidChoice = 99;
        Bot defaultBot = GameLauncher.createBot(invalidChoice);
        assertTrue(defaultBot instanceof NaziBot);
        assertEquals("Default Nazi Bot", defaultBot.getName());
    }
}
