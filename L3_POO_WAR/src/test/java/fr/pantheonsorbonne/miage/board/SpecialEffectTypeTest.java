package fr.pantheonsorbonne.miage.board;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SpecialEffectTypeTest {

    @Test
    public void testSpecialEffectTypeValues() {
        SpecialEffectType[] expectedValues = { SpecialEffectType.MINE, SpecialEffectType.RADAR,
                SpecialEffectType.RAPID_FIRE, SpecialEffectType.NUCLEAR_BOMB,
                SpecialEffectType.SUBMARINE_SCAN };
        assertArrayEquals(expectedValues, SpecialEffectType.values(),
                "SpecialEffectType enum should contain the expected values.");
    }

    @Test
    public void testValueOf() {
        assertEquals(SpecialEffectType.RADAR, SpecialEffectType.valueOf("RADAR"),
                "valueOf should return the correct SpecialEffectType.");
        // Repeat for other values
    }

    // Additional tests if there are specific methods or behavior associated with
    // this enum
}
