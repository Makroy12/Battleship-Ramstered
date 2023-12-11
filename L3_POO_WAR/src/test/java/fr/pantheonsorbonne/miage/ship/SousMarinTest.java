package fr.pantheonsorbonne.miage.ship;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.board.BattleshipGrid;
import fr.pantheonsorbonne.miage.board.Cell;
import fr.pantheonsorbonne.miage.ship.Ship.Coordinate;

public class SousMarinTest {

    private SousMarin sousMarin;
    private BattleshipGrid grid;

    @BeforeEach
    public void setUp() {
        sousMarin = new SousMarin();
        grid = new BattleshipGrid(); // Utilisation d'une instance réelle

        // Initialiser la grille avec des cellules
        for (int i = 0; i < grid.getSIZE(); i++) {
            for (int j = 0; j < grid.getSIZE(); j++) {
                Cell cell = new Cell(i, j);
                cell.setShip(null); // Aucun navire par défaut
                // Utiliser une méthode de BattleshipGrid pour définir la cellule si elle existe
                // Par exemple, grid.setCell(i, j, cell); si cette méthode est définie
            }
        }
    }

    @Test
    public void testScanLine() {
        // Simuler la présence d'un navire
        Cell cell = grid.getCell(0, 0);
        cell.setShip(new Ship(1, false)); // Placer un navire fictif

        // Définir la position du sous-marin
        Coordinate[] positions = { new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(0, 2) };
        sousMarin.setPositions(positions);

        boolean[] scanResults = sousMarin.scanLine(grid, true);
        assertTrue(scanResults[0], "Doit détecter un navire à la première cellule");
        assertFalse(scanResults[1], "Doit indiquer pas de navire pour les autres cellules");
    }

    // Autres tests selon les besoins
}
