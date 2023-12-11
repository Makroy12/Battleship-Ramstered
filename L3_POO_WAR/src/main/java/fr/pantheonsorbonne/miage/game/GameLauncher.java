package fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import fr.pantheonsorbonne.miage.player.Bot;
import fr.pantheonsorbonne.miage.player.NaziBot;
import fr.pantheonsorbonne.miage.ship.ContreTorpilleur;
import fr.pantheonsorbonne.miage.ship.Croiseur;
import fr.pantheonsorbonne.miage.ship.PorteAvion;
import fr.pantheonsorbonne.miage.ship.Ship;
import fr.pantheonsorbonne.miage.ship.SousMarin;
import fr.pantheonsorbonne.miage.ship.Tropilleur;

public class GameLauncher {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Battleship!");
        System.out.println("Choose the bots:");
        System.out.println("1 - Nazi Bot");
        System.out.println("2 - Expert Bot");

        int choice1 = getBotChoice(scanner, "Select bot for Player 1: ");
        Bot player1 = createBot(choice1);

        int choice2 = getBotChoice(scanner, "Select bot for Player 2: ");
        Bot player2 = createBot(choice2);

        GameController gameController = new GameController(player1, player2);
        gameController.setupGame();
        gameController.startGame();

        scanner.close();
    }

    private static int getBotChoice(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    private static Bot createBot(int choice) {
        BattleshipGrid grid = new BattleshipGrid();
        List<Ship> allShips = createShipList();

        switch (choice) {
            case 1:
                return new NaziBot("Nazi Bot", grid, allShips);
            case 2:
                return new NaziBot("Nazi Bot", grid, allShips);
            default:
                System.out.println("Invalid choice. Defaulting to Nazi Bot.");
                return new NaziBot("Default Nazi Bot", grid, allShips);
        }
    }

    private static List<Ship> createShipList() {
        List<Ship> allShips = new ArrayList<>();
        allShips.add(new PorteAvion());
        allShips.add(new Croiseur());
        allShips.add(new ContreTorpilleur());
        allShips.add(new SousMarin());
        allShips.add(new Tropilleur());
        return allShips;
    }
}