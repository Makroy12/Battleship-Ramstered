package fr.pantheonsorbonne.miage.ship;

import fr.pantheonsorbonne.miage.board.BattleshipGrid;
import fr.pantheonsorbonne.miage.board.Cell;

/**
 * Classe représentant un sous-marin dans le jeu de Bataille Navale.
 */
public class SousMarin extends Ship {
    /**
     * Constructeur pour un sous-marin.
     */
    public SousMarin() {
        super(3, false); // Taille de 3, initialement sans système de défense
    }

    /**
     * Obtient la position actuelle du sous-marin.
     * 
     * @return La première position du sous-marin ou null si non définie.
     */
    public Coordinate getCurrentPosition() {
        if (this.getPositions() != null && this.getPositions().length > 0) {
            return this.getPositions()[0];
        }
        return null;
    }

    /**
     * Scanne une ligne ou une colonne sur la grille pour détecter la présence de
     * navires.
     * 
     * @param grid      La grille de jeu.
     * @param isRowScan Vrai pour scanner une ligne, faux pour une colonne.
     * @return Un tableau indiquant la présence de navires dans chaque cellule
     *         scannée.
     */
    public boolean[] scanLine(BattleshipGrid grid, boolean isRowScan) {
        int size = grid.getSIZE();
        boolean[] scanResults = new boolean[size];

        Coordinate currentPosition = getCurrentPosition();
        if (currentPosition == null) {
            return scanResults; // Retourner les résultats vides si la position du sous-marin n'est pas définie
        }

        int index = isRowScan ? currentPosition.getX() : currentPosition.getY();
        for (int i = 0; i < size; i++) {
            int x = isRowScan ? index : i;
            int y = isRowScan ? i : index;
            Cell cell = grid.getCell(x, y);
            scanResults[i] = cell.hasShip();
        }

        return scanResults;
    }

}
