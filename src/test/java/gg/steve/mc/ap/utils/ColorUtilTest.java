package gg.steve.mc.ap.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorUtilTest {

    @Test
    void constructor_instantiates() {
        assertNotNull(new ColorUtil());
    }

    @Test
    void colorize_translatesAmpersandColorCodes() {
        String result = ColorUtil.colorize("&aHello &bWorld");
        assertEquals("§aHello §bWorld", result);
    }

    @Test
    void colorize_noColorCodes_returnsUnchanged() {
        String result = ColorUtil.colorize("Hello World");
        assertEquals("Hello World", result);
    }

    @Test
    void colorize_emptyString_returnsEmpty() {
        String result = ColorUtil.colorize("");
        assertEquals("", result);
    }

    @Test
    void colorize_multipleFormattingCodes() {
        String result = ColorUtil.colorize("&l&cBold Red");
        assertEquals("§l§cBold Red", result);
    }

    @Test
    void colorize_trailingAmpersand_preservedAsIs() {
        String result = ColorUtil.colorize("test&");
        assertEquals("test&", result);
    }

    @Test
    void colorize_invalidColorCode_preservedAsIs() {
        String result = ColorUtil.colorize("&zInvalid");
        assertEquals("&zInvalid", result);
    }
}
