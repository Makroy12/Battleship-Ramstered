package fr.pantheonsorbonne.miage.board;

public enum CellState {
    SHIP, MINE, HIT, WATER, MISSED, DEFENSE, DEFENSE_ABSORBED, SUNK_SHIP;

    public char getDisplayCharacter() {
        switch (this) {
            case SHIP:
                return 'S'; // Character for ship
            case MINE:
                return 'M'; // Character for mine
            case HIT:
                return 'H'; // Character for hit
            case MISSED:
                return '*'; // Character for missed
            case DEFENSE:
                return 'D'; // Character for defense
            case DEFENSE_ABSORBED:
                return 'A'; // Character for defense absorbed
            case SUNK_SHIP:
                return 'X';
            default:
                return '.'; // Default character for water
        }
    }

}
