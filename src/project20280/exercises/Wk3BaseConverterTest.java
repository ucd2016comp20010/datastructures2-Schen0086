package project20280.exercises;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Wk3BaseConverterTest {

    @Test
    void testConvertToBinary() {
        assertEquals("10111", Wk3BaseConverter.convertToBinary(23));
        assertEquals(
                "111001000000101011000010011101010110110001100010000000000000",
                Wk3BaseConverter.convertToBinary(1027010000000000000L)
        );
    }

    @Test
    void testConvertToOtherBases() {
        // base 16 (hex)
        assertEquals("17", Wk3BaseConverter.convertToBase(23, 16));   // 0x17
        assertEquals("FF", Wk3BaseConverter.convertToBase(255, 16));

        // base > 9 uses letters
        assertEquals("A", Wk3BaseConverter.convertToBase(10, 16));
        assertEquals("Z", Wk3BaseConverter.convertToBase(35, 36));
        assertEquals("10", Wk3BaseConverter.convertToBase(36, 36));

        // base 8 (octal)
        assertEquals("27", Wk3BaseConverter.convertToBase(23, 8));

        // negatives
        assertEquals("-10111", Wk3BaseConverter.convertToBase(-23, 2));
    }

    @Test
    void testConvertEdgeCases() {
        assertEquals("0", Wk3BaseConverter.convertToBase(0, 2));
        assertThrows(IllegalArgumentException.class, () -> Wk3BaseConverter.convertToBase(10, 1));
        assertThrows(IllegalArgumentException.class, () -> Wk3BaseConverter.convertToBase(10, 37));
    }
}
