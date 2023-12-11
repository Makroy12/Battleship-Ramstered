package fr.pantheonsorbonne.miage.player;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import fr.pantheonsorbonne.miage.board.BattleshipGrid;
import fr.pantheonsorbonne.miage.board.Cell;
import fr.pantheonsorbonne.miage.board.RadarGrid;
import fr.pantheonsorbonne.miage.board.SpecialEffectType;
import fr.pantheonsorbonne.miage.ship.ContreTorpilleur;
import fr.pantheonsorbonne.miage.ship.Croiseur;
import fr.pantheonsorbonne.miage.ship.PorteAvion;
import fr.pantheonsorbonne.miage.ship.Ship;
import fr.pantheonsorbonne.miage.ship.SousMarin;
import fr.pantheonsorbonne.miage.ship.Tropilleur;

public class NaziBot extends Bot {
    private RadarGrid radarGrid;
    private Random random;
    private List<Ship> ships;
    protected Set<SpecialEffectType> availableEffects;

    public NaziBot(String name, fr.pantheonsorbonne.miage.game.BattleshipGrid grid, List<Ship> ships) {
        super(grid);
        this.ships = new ArrayList<>(ships);
        this.random = new Random();
        this.usedEffects = EnumSet.noneOf(SpecialEffectType.class);
        initializeAvailableEffects();
    }

    private void initializeAvailableEffects() {
        availableEffects = new HashSet<>();
        for (SpecialEffectType effect : SpecialEffectType.values()) {
            availableEffects.add(effect);
        }
    }

    @Override
    public void placeShips() {
        System.out.println("NaziBot is placing ships...");
        placeShipRandomly(new PorteAvion(), "PorteAvion");
        placeShipRandomly(new Croiseur(), "Croiseur");
        placeShipRandomly(new ContreTorpilleur(), "ContreTorpilleur");
        placeShipRandomly(new SousMarin(), "SousMarin");
        placeShipRandomly(new Tropilleur(), "Tropilleur");
        System.out.println("NaziBot has finished placing ships.");
    }

    private void placeShipRandomly(Ship ship, String shipName) {
        boolean placed = false;
        while (!placed) {
            int x = random.nextInt(grid.getSIZE());
            int y = random.nextInt(grid.getSIZE());
            boolean isHorizontal = random.nextBoolean();
            placed = grid.placeShip(ship, x, y, isHorizontal);
            if (placed) {
                System.out.println(shipName + " placed at (" + x + ", " + y + ") "
                        + (isHorizontal ? "horizontally" : "vertically"));
            }
        }
    }

    @Override
    public void placeMines() {
        int minesPlaced = 0;
        while (minesPlaced < 3) {
            int x = random.nextInt(grid.getSIZE());
            int y = random.nextInt(grid.getSIZE());
            if (grid.placeMine(x, y)) {
                minesPlaced++;
            }
        }
    }

    public void setupDefenseShips() {
        if (ships.size() < 2) {
            throw new IllegalStateException("Not enough ships to assign defense systems");
        }

        int firstShipIndex = random.nextInt(ships.size());
        int secondShipIndex;
        do {
            secondShipIndex = random.nextInt(ships.size());
        } while (secondShipIndex == firstShipIndex);

        ships.get(firstShipIndex).setHasDefenseSystem(true);
        ships.get(secondShipIndex).setHasDefenseSystem(true);
    }

    @Override
    public boolean[] getSubmarineScanResults() {
        boolean[] scanResults = new boolean[10];
        for (int i = 0; i < 10; i++) {
            scanResults[i] = random.nextBoolean();
        }
        return scanResults;
    }

    @Override
    public PlayerAction decideAction() {
        System.out.println("NaziBot is deciding an action");
        if (random.nextBoolean()) {
            int targetX = random.nextInt(getGrid().getSIZE());
            int targetY = random.nextInt(getGrid().getSIZE());
            if (isValidAttack(targetX, targetY)) {
                return PlayerAction.createAttackAction(targetX, targetY, getGrid());
            }
        } else {
            SpecialEffectType effectType = selectRandomSpecialEffect();
            if (effectType != null && isEffectAvailable(effectType)) {
                return PlayerAction.createSpecialEffectAction(effectType, getGrid());
            }
        }
        return decideAction();
    }

    private void attack(int targetX, int targetY) {
        if (isSpecialEffectAvailable() && isValidAttack(targetX, targetY)) {
            Cell cell = grid.getCell(targetX, targetY);

            if (!cell.isHit()) {
                cell.hit();

                if (cell.hasShip()) {
                    System.out.println("Hit! You've damaged an enemy ship.");
                    if (cell.getShip().isSunk()) {
                        System.out.println("You've sunk an enemy ship!");
                    }
                } else {
                    System.out.println("Miss! Your shot missed the target.");
                }
            } else {
                System.out.println("This cell has already been hit.");
            }
        } else {
            if (!isSpecialEffectAvailable()) {
                System.out.println("Special effect has already been used.");
            } else {
                System.out.println("Invalid attack coordinates.");
            }
        }
    }

    // Define a method for using special effects
    // Define a method for using special effects
    private void useSpecialEffect(int x, int y, SpecialEffectType effectType) {
        if (!isEffectAvailable(effectType)) {
            System.out.println("Special effect " + effectType + " has already been used.");
            return;
        }

        switch (effectType) {
            case RADAR:
                performRadarScan(x, y); // Assuming x and y are the center coordinates for radar scan
                break;
            case RAPID_FIRE:
                performRapidFire(x, y, random.nextBoolean()); // Randomly decide horizontal or vertical
                break;
            case NUCLEAR_BOMB:
                performNuclearBomb(x, y); // Assuming x and y are the center coordinates for the nuclear bomb
                break;
        }

        // Mark the special effect as used
        markEffectAsUsed(effectType);
    }

    private void performRadarScan(int centerX, int centerY) {
        // Implementation of radar scan
        radarGrid.radarScan(centerX, centerY);
    }

    private void performRapidFire(int startX, int startY, boolean isHorizontal) {
        // Implementation of rapid fire
        // Use the logic provided
        if (isHorizontal) {
            int endCol = Math.min(startY + 3, grid.getSIZE());
            for (int col = startY; col < endCol; col++) {
                grid.hit(startX, col);
            }
        } else {
            int endRow = Math.min(startX + 3, grid.getSIZE());
            for (int row = startX; row < endRow; row++) {
                grid.hit(row, startY);
            }
        }
    }

    private void performNuclearBomb(int centerX, int centerY) {
        // Implementation of nuclear bomb
        // Use the logic provided
        int startX = Math.max(0, centerX - 1);
        int endX = Math.min(centerX + 1, grid.getSIZE() - 1);
        int startY = Math.max(0, centerY - 1);
        int endY = Math.min(centerY + 1, grid.getSIZE() - 1);

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                grid.hit(x, y);
            }
        }
    }

    // Mark a special effect as used
    @Override
    public void markEffectAsUsed(SpecialEffectType effect) {
        super.markEffectAsUsed(effect);
        availableEffects.remove(effect);
    }

    // Check if a special effect is available (not yet used)
    public boolean isEffectAvailable(SpecialEffectType effect) {
        return availableEffects.contains(effect);
    }

    // Check if an attack action is valid
    private boolean isValidAttack(int x, int y) {
        return isWithinGrid(x, y) && !grid.getCell(x, y).isHit(); // Check if the cell is within grid and not previously
                                                                  // hit
    }

    private boolean isWithinGrid(int x, int y) {
        return x >= 0 && x < grid.getSIZE() && y >= 0 && y < grid.getSIZE();
    }

    private SpecialEffectType selectRandomSpecialEffect() {
        List<SpecialEffectType> availableEffects = new ArrayList<>();
        for (SpecialEffectType effect : SpecialEffectType.values()) {
            if (isEffectAvailable(effect)) {
                availableEffects.add(effect);
            }
        }

        if (availableEffects.isEmpty()) {
            return null; // No available special effects
        }

        int index = random.nextInt(availableEffects.size());
        return availableEffects.get(index);
    }

    private boolean isSpecialEffectAvailable() {
        for (SpecialEffectType effect : SpecialEffectType.values()) {
            if (isEffectAvailable(effect)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasLost() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false; // At least one ship is not sunk
            }
        }
        return true; // All ships are sunk
    }

    @Override
    public void placeShips(BattleshipGrid grid) {
    }

    @Override
    public void setupDefenseShips(BattleshipGrid grid) {
    }

    @Override
    public void placeMines(BattleshipGrid grid) {
    }
}
