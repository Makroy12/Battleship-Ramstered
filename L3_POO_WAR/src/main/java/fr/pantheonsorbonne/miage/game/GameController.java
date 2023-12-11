package fr.pantheonsorbonne.miage.game;

import java.util.EnumSet;
import java.util.Random;

import fr.pantheonsorbonne.miage.board.BattleshipGrid;
import fr.pantheonsorbonne.miage.board.Cell;
import fr.pantheonsorbonne.miage.board.RadarGrid;
import fr.pantheonsorbonne.miage.board.SpecialEffectType;
import fr.pantheonsorbonne.miage.board.SpecialEffects;
import fr.pantheonsorbonne.miage.player.Bot;
import fr.pantheonsorbonne.miage.player.PlayerAction;
import fr.pantheonsorbonne.miage.ship.Ship;
import fr.pantheonsorbonne.miage.ship.SousMarin;

public class GameController {
    private GameState gameState;
    private Bot player1;
    private Bot player2;
    private boolean player1Turn;
    private EnumSet<SpecialEffectType> usedEffects = EnumSet.noneOf(SpecialEffectType.class);
    private SpecialEffects specialEffects;

    public GameController(Bot player1, Bot player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameState = GameState.NOT_STARTED;
        Random random = new Random();
        player1Turn = random.nextBoolean();
    }

    public void setupGame() {
        placeShipsAndMines(player1, player1.getGrid());
        placeShipsAndMines(player2, player2.getGrid());
        gameState = GameState.READY;
    }

    private void placeShipsAndMines(Bot bot, BattleshipGrid grid) {
        bot.placeShips(grid);
        bot.setupDefenseShips(grid);
        bot.placeMines(grid);
    }

    public void startGame() {
        if (gameState == GameState.READY) {
            gameState = GameState.IN_PROGRESS;
            while (gameState == GameState.IN_PROGRESS) {
                executeTurn();
                checkWinCondition();
                switchPlayer();
            }
            announceWinner();
        }
    }

    private void executeTurn() {
        Bot currentPlayer = player1Turn ? player1 : player2;

        if (currentPlayer.getGrid() == null) {
            // Handle the case where the grid is not properly initialized
            System.out.println("Player's grid is not initialized.");
            return;
        }

        if (currentPlayer.shouldSkipTurn()) {
            currentPlayer.clearSkipTurn();
            switchPlayer();
            return;
        }

        try {
            if (currentPlayer.hasHitMine()) {
                currentPlayer.clearMineHit();
                currentPlayer.setSkipNextTurn(true); // Ensure next turn is skipped
                switchPlayer();
                return;
            }

            PlayerAction action = currentPlayer.decideAction();
            if (action.isValid()) {
                if (action.isAttack()) {
                    executeAttack(action.getTargetCell(), currentPlayer);
                } else if (action.getActionType() == PlayerAction.ActionType.USE_SPECIAL_EFFECT) {
                    if (currentPlayer.isEffectAvailable(action.getSpecialEffect())) {
                        executeSpecialEffect(action.getSpecialEffect(), currentPlayer);
                    }
                }
            } else {
                // Handle invalid action
                System.out.println("Invalid action by " + (player1Turn ? "Player 1" : "Player 2"));
            }
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace(); // Print the exception for debugging
        }
    }

    private void switchPlayer() {
        player1Turn = !player1Turn;
    }

    private void executeAttack(Cell targetCell, Bot bot) {
        BattleshipGrid targetGrid = (bot == player1) ? player2.getGrid() : player1.getGrid();

        if (targetCell.hasMine()) {
            // Handle mine hit
            targetCell.hit();
            bot.setSkipNextTurn(true); // Assuming Bot class has a method to skip the next turn
            return;
        }

        if (targetCell.hasShip()) {
            Ship ship = targetCell.getShip();
            int segmentIndex = ship.getSegmentIndex(targetCell.getX(), targetCell.getY());
            ship.hit(segmentIndex);
            targetCell.hit();

            if (ship.isSunk()) {
                targetGrid.updateShipSunkStatus(ship); // Update grid or game state if a ship is sunk
            }
        } else {
            targetCell.hit(); // Mark the cell as a miss
        }
    }

    private void executeSpecialEffect(SpecialEffectType effect, Bot bot) {
        BattleshipGrid targetGrid = (bot == player1) ? player2.getGrid() : player1.getGrid(); // Determine the target
                                                                                              // grid

        try {
            switch (effect) {
                case RADAR:
                    // Implement radar scan logic
                    break;
                case RAPID_FIRE:
                    // Implement rapid fire logic
                    break;
                case NUCLEAR_BOMB:
                    // Implement nuclear bomb logic
                    break;
            }
            bot.markEffectAsUsed(effect);
        } catch (Exception e) {
            // Handle exceptions during special effect execution
        }
    }

    private void executeSubmarineReconnaissance(Bot bot, boolean isRowScan) {
        // Retrieve the submarine instance from the bot
        SousMarin submarine = bot.getSubmarine();

        // Check if the submarine is sunk before proceeding
        if (submarine.isSunk()) {
            System.out.println("Submarine is sunk and cannot perform reconnaissance.");
            return; // Exit the method as the submarine is no longer operational
        }

        // Determine the target grid based on the current player
        BattleshipGrid targetGrid = player1Turn ? player2.getGrid() : player1.getGrid();

        // Initialize the RadarGrid with the target grid
        RadarGrid radarGrid = new RadarGrid(targetGrid);

        // Call the scanLine method of the submarine and get the scan results
        boolean[] scanResults = submarine.scanLine(targetGrid, isRowScan);

        // Update the RadarGrid based on scan results
        updateRadarGridWithScanResults(radarGrid, scanResults, isRowScan, submarine.getCurrentPosition());

        // Display the grid with the scanned areas
        radarGrid.displayScannedGrid();

        // After executing the reconnaissance, mark the ability as used for the bot
        bot.markSubmarineAsUsed();
    }

    // Helper method to update the RadarGrid with the scan results
    private void updateRadarGridWithScanResults(RadarGrid radarGrid, boolean[] scanResults, boolean isRowScan,
            SousMarin.Coordinate currentPosition) {
        int index = isRowScan ? currentPosition.getX() : currentPosition.getY();
        for (int i = 0; i < scanResults.length; i++) {
            int x = isRowScan ? index : i;
            int y = isRowScan ? i : index;
            if (scanResults[i]) {
                radarGrid.scan(x, y); // Mark the cell as scanned in the RadarGrid
            }
        }
    }

    private boolean checkWinCondition() {
        if (player1.getGrid().areAllShipsSunk() || player2.getGrid().areAllShipsSunk()) {
            gameState = GameState.ENDED;
            return true;
        }
        return false;
    }

    private void announceWinner() {
        if (gameState != GameState.ENDED) {
            return;
        }

        if (player1.getGrid().areAllShipsSunk() && player2.getGrid().areAllShipsSunk()) {
            System.out.println("It's a draw!");
        } else if (player1.getGrid().areAllShipsSunk()) {
            System.out.println("Player 2 wins!");
        } else if (player2.getGrid().areAllShipsSunk()) {
            System.out.println("Player 1 wins!");
        } else {
            System.out.println("Unexpected result.");
        }
    }

}