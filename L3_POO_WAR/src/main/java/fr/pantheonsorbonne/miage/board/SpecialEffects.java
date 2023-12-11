package fr.pantheonsorbonne.miage.board;

public class SpecialEffects {
    private BattleshipGrid battleshipGrid;
    private RadarGrid radarGrid;

    public SpecialEffects(BattleshipGrid battleshipGrid, RadarGrid radarGrid) {
        this.battleshipGrid = battleshipGrid;
        this.radarGrid = radarGrid;
    }

    public void applyEffect(SpecialEffectType effectType, int... params) {
        switch (effectType) {
            case RADAR:
                performRadarScan(params);
                break;
            case RAPID_FIRE:
                if (params.length >= 3) {
                    // Extract startX, startY, and isHorizontal from params
                    int startX = params[0];
                    int startY = params[1];
                    boolean isHorizontal = params[2] != 0; // Assuming 0 for false, non-zero for true
                    performRapidFire(startX, startY, isHorizontal);
                }
                break;
            case NUCLEAR_BOMB:
                if (params.length >= 2) {
                    // Extract centerX and centerY from params
                    int centerX = params[0];
                    int centerY = params[1];
                    performNuclearBomb(centerX, centerY);
                }
                break;
        }
    }

    private void performRadarScan(int... params) {
        // Assumes params contains centerX and centerY for the radar scan
        if (params.length >= 2) {
            radarGrid.radarScan(params[0], params[1]);
        }
    }

    public void performRapidFire(int startX, int startY, boolean isHorizontal) {
        if (isHorizontal) {
            // Ensure the rapid fire doesn't go beyond the right edge of the grid
            int endCol = Math.min(startY + 3, battleshipGrid.getSIZE());

            // Horizontal rapid fire
            for (int col = startY; col < endCol; col++) {
                battleshipGrid.hit(startX, col); // Use the existing hit method in the BattleshipGrid class
            }
        } else {
            // Ensure the rapid fire doesn't go beyond the bottom edge of the grid
            int endRow = Math.min(startX + 3, battleshipGrid.getSIZE());

            // Vertical rapid fire
            for (int row = startX; row < endRow; row++) {
                battleshipGrid.hit(row, startY); // Use the existing hit method in the BattleshipGrid class
            }
        }
    }

    public void performNuclearBomb(int centerX, int centerY) {
        // Calculate the start and end points for the 3x3 area, ensuring they are within
        // the grid bounds
        int startX = Math.max(0, centerX - 1);
        int endX = Math.min(centerX + 1, battleshipGrid.getSIZE() - 1);
        int startY = Math.max(0, centerY - 1);
        int endY = Math.min(centerY + 1, battleshipGrid.getSIZE() - 1);

        // Iterate through the 3x3 area and hit each cell
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                battleshipGrid.hit(x, y); // Use the existing hit method in the BattleshipGrid class
            }
        }
    }

    // Other methods for additional effects
}
