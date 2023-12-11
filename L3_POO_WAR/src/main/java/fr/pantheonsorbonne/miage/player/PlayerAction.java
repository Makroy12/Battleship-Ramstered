package fr.pantheonsorbonne.miage.player;

import fr.pantheonsorbonne.miage.board.BattleshipGrid;
import fr.pantheonsorbonne.miage.board.SpecialEffectType;

public class PlayerAction {
    private ActionType actionType;
    private int targetX;
    private int targetY;
    private SpecialEffectType specialEffect;
    private BattleshipGrid grid; // Grid associated with this action

    // Updated constructor to include BattleshipGrid
    public PlayerAction(ActionType actionType, int targetX, int targetY, SpecialEffectType specialEffect,
            BattleshipGrid grid) {
        this.actionType = actionType;
        this.targetX = targetX;
        this.targetY = targetY;
        this.specialEffect = specialEffect;
        this.grid = grid; // Initialize the grid here
    }

    // Enum to represent the type of action
    public enum ActionType {
        ATTACK,
        USE_SPECIAL_EFFECT
    }

    public ActionType getActionType() {
        return actionType;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public SpecialEffectType getSpecialEffect() {
        return specialEffect;
    }

    public boolean isValid() {
        if (actionType == ActionType.ATTACK) {
            return isWithinGrid(targetX, targetY);
        }
        if (actionType == ActionType.USE_SPECIAL_EFFECT) {
            return specialEffect != null;
        }
        return false;
    }

    private boolean isWithinGrid(int x, int y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    public static PlayerAction createAttackAction(int x, int y, BattleshipGrid grid) {
        return new PlayerAction(ActionType.ATTACK, x, y, null, grid);
    }

    public static PlayerAction createSpecialEffectAction(SpecialEffectType effect, BattleshipGrid grid) {
        return new PlayerAction(ActionType.USE_SPECIAL_EFFECT, -1, -1, effect, grid);
    }

    public boolean isAttack() {
        return this.actionType == ActionType.ATTACK;
    }

    public fr.pantheonsorbonne.miage.board.Cell getTargetCell() {
        return grid.getCell(targetX, targetY);
    }
}