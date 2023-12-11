package fr.pantheonsorbonne.miage.board;

public class RadarGrid {
    private BattleshipGrid grid;

    public RadarGrid(BattleshipGrid grid) {
        this.grid = grid;
    }

    // Scans a single cell at (x, y)
    public void scan(int x, int y) {
        if (isValidCoordinate(x, y)) {
            grid.getCell(x, y).setScannedByRadar(true);
        }
    }

    // Scans a 3x3 area centered around (centerX, centerY)
    public void radarScan(int centerX, int centerY) {
        int startX = Math.max(centerX - 1, 0);
        int endX = Math.min(centerX + 1, grid.getSIZE() - 1);
        int startY = Math.max(centerY - 1, 0);
        int endY = Math.min(centerY + 1, grid.getSIZE() - 1);

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                scan(x, y); // Use the single cell scan method
            }
        }
    }

    // Checks if the coordinates are within the grid bounds
    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < grid.getSIZE() && y >= 0 && y < grid.getSIZE();
    }

    // Displays the grid, highlighting the scanned areas
    public void displayScannedGrid() {
        for (int i = 0; i < grid.getSIZE(); i++) {
            for (int j = 0; j < grid.getSIZE(); j++) {
                Cell cell = grid.getCell(i, j);
                System.out.print(cell.getDisplayCharacter(cell.isScannedByRadar()));
            }
            System.out.println();
        }
    }

    // ... other methods as needed ...
}
