package fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.board.BattleshipGrid;
import fr.pantheonsorbonne.miage.player.NaziBot;
import fr.pantheonsorbonne.miage.ship.ContreTorpilleur;
import fr.pantheonsorbonne.miage.ship.Croiseur;
import fr.pantheonsorbonne.miage.ship.PorteAvion;
import fr.pantheonsorbonne.miage.ship.Ship;
import fr.pantheonsorbonne.miage.ship.SousMarin;
import fr.pantheonsorbonne.miage.ship.Tropilleur;

public class GameControllerTest {

    public static void main(String[] args) {
        // Créez deux instances de NaziBot pour la partie
        NaziBot player1 = new NaziBot("Player 1", new BattleshipGrid(), createShips());
        NaziBot player2 = new NaziBot("Player 2", new BattleshipGrid(), createShips());

        // Créez une instance de GameController avec les deux joueurs
        GameController gameController = new GameController(player1, player2);

        // Configurez la partie
        gameController.setupGame();

        // Démarrez la partie
        gameController.startGame();
    }

    // Créez une liste de navires pour les NaziBots
    private static List<Ship> createShips() {
        List<Ship> ships = new ArrayList<>();
        ships.add(new PorteAvion());
        ships.add(new Croiseur());
        ships.add(new ContreTorpilleur());
        ships.add(new SousMarin());
        ships.add(new Tropilleur());
        return ships;
    }
}
