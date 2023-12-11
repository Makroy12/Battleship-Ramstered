package fr.pantheonsorbonne.miage.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import fr.pantheonsorbonne.miage.board.BattleshipGrid;
import fr.pantheonsorbonne.miage.board.Cell;
import fr.pantheonsorbonne.miage.board.SpecialEffectType;

public class PlayerActionTest {
    private BattleshipGrid grid;

    @BeforeEach
    public void setUp() {
        // Initialisez votre grille ici, par exemple :
        grid = new BattleshipGrid();
    }

    @Test
    public void testAttackActionIsValid() {
        // Créez une action d'attaque valide
        PlayerAction attackAction = PlayerAction.createAttackAction(2, 3, grid);

        // Vérifiez si l'action est valide
        assertTrue(attackAction.isValid());
    }

    @Test
    public void testSpecialEffectActionIsValid() {
        // Créez une action d'effet spécial valide
        PlayerAction specialEffectAction = PlayerAction.createSpecialEffectAction(SpecialEffectType.MINE, grid);

        // Vérifiez si l'action est valide
        assertTrue(specialEffectAction.isValid());
    }

    @Test
    public void testInvalidAttackAction() {
        // Créez une action d'attaque invalide en ciblant une cellule hors de la grille
        PlayerAction invalidAttackAction = PlayerAction.createAttackAction(12, 8, grid);

        // Vérifiez si l'action est invalide
        assertFalse(invalidAttackAction.isValid());
    }

    @Test
    public void testInvalidSpecialEffectAction() {
        // Créez une action d'effet spécial invalide sans spécifier l'effet
        PlayerAction invalidSpecialEffectAction = PlayerAction.createSpecialEffectAction(null, grid);

        // Vérifiez si l'action est invalide
        assertFalse(invalidSpecialEffectAction.isValid());
    }

    @Test
    public void testGetTargetCell() {
        // Créez une action d'attaque
        PlayerAction attackAction = PlayerAction.createAttackAction(5, 6, grid);

        // Obtenez la cellule cible de l'action
        Cell targetCell = attackAction.getTargetCell();

        // Vérifiez si la cellule obtenue correspond aux coordonnées spécifiées dans
        // l'action
        assertEquals(5, targetCell.getX());
        assertEquals(6, targetCell.getY());
    }
}
