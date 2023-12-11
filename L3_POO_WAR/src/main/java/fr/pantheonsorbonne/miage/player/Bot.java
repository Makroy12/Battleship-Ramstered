package fr.pantheonsorbonne.miage.player;

import java.util.EnumSet;
import java.util.Set;

import fr.pantheonsorbonne.miage.board.BattleshipGrid;
import fr.pantheonsorbonne.miage.board.Cell;
import fr.pantheonsorbonne.miage.board.SpecialEffectType;
import fr.pantheonsorbonne.miage.ship.SousMarin;

public abstract class Bot {
    private fr.pantheonsorbonne.miage.game.BattleshipGrid name;
    protected BattleshipGrid grid;
    private SousMarin submarine;
    private boolean submarineUsed;
    protected Set<SpecialEffectType> usedEffects;
    private boolean hitMine = false; // Tracks if the bot has hit a mine
    protected boolean skipNextTurn = false; // Indicates whether the bot should skip its next turn (e.g., due to hitting
                                            // a mine)

    // No-argument constructor
    public Bot() {
        this.usedEffects = EnumSet.noneOf(SpecialEffectType.class);
        this.submarine = new SousMarin();
        this.submarineUsed = false;
        this.grid = new BattleshipGrid();
    }

    // Constructor with a String argument
    public Bot(fr.pantheonsorbonne.miage.game.BattleshipGrid grid2) {
        this(); // Call the no-argument constructor to initialize common fields
        this.name = grid2;
    }

    // Constructor with a BattleshipGrid argument
    public Bot(BattleshipGrid grid) {
        this(); // Call the no-argument constructor to initialize common fields
        this.grid = grid; // Set the provided grid
    }

    // Getter and setter for the grid
    public BattleshipGrid getGrid() {
        return this.grid;
    }

    public void setGrid(BattleshipGrid grid) {
        this.grid = grid;
    }

    public void hitMine() {
        this.hitMine = true;
    }

    public boolean hasHitMine() {
        return this.hitMine;
    }

    public void clearMineHit() {
        this.hitMine = false;
    }

    public SousMarin getSubmarine() {
        return this.submarine;
    }

    // Method to mark the submarine's ability as used
    public void markSubmarineAsUsed() {
        this.submarineUsed = true;
    }

    // Check if the submarine's ability is available (not yet used)
    public boolean isSubmarineAvailable() {
        return !this.submarineUsed;
    }

    // Method for placing ships on the grid
    public abstract void placeShips();

    // Method for placing mines on the grid
    public abstract void placeMines();

    // Method for setting up defense systems on selected ships
    public abstract void setupDefenseShips();

    // Decide the next action (hit, use special effect, etc.)
    public abstract PlayerAction decideAction();

    // Implement logic to check if the bot has lost all its ships
    public boolean hasLost() {
        // Iterate through each cell in the grid
        for (int x = 0; x < grid.getSIZE(); x++) {
            for (int y = 0; y < grid.getSIZE(); y++) {
                Cell cell = grid.getCell(x, y);
                // Check if the cell contains a ship and if it's not sunk
                if (cell.hasShip() && !cell.getShip().isSunk()) {
                    // If any ship is not sunk, the player has not lost
                    return false;
                }
            }
        }
        // If all ships are sunk, return true
        return true;
    }

    // Method to mark that the bot should skip its next turn (e.g., after hitting a
    // mine)
    public void setSkipNextTurn(boolean skip) {
        this.skipNextTurn = skip;
    }

    // Check if the bot should skip its next turn
    public boolean shouldSkipTurn() {
        return skipNextTurn;
    }

    // Clear the flag to skip the next turn
    public void clearSkipTurn() {
        this.skipNextTurn = false;
    }

    // Method to handle the execution of a special effect
    public void executeSpecialEffect(SpecialEffectType effectType) {
        // Implementation depends on the effect type
    }

    // Method for the submarine's special effect
    public abstract boolean[] getSubmarineScanResults();

    // Mark a special effect as used
    public void markEffectAsUsed(SpecialEffectType effect) {
        usedEffects.add(effect);
    }

    // Check if a special effect is available (not yet used)
    public boolean isEffectAvailable(SpecialEffectType effect) {
        return !usedEffects.contains(effect);
    }

    public abstract void placeShips(BattleshipGrid grid);

    public abstract void setupDefenseShips(BattleshipGrid grid);

    public abstract void placeMines(BattleshipGrid grid);

}
