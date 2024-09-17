package info.preva1l.fadlc.utils;

import com.google.common.base.Preconditions;
import lombok.Getter;

import java.awt.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Simplistic enumeration of all supported color values for chat.
 */
public final class LegacyChatColor {

    /**
     * The special character which prefixes all chat colour codes. Use this if
     * you need to dynamically convert colour codes from your custom format.
     */
    public static final char COLOR_CHAR = '\u00A7';
    public static final String ALL_CODES = "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx";
    /**
     * Pattern to remove all colour codes.
     */
    public static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + COLOR_CHAR + "[0-9A-FK-ORX]");
    /**
     * Represents black.
     */
    public static final LegacyChatColor BLACK = new LegacyChatColor('0', "black", new Color(0x000000));
    /**
     * Represents dark blue.
     */
    public static final LegacyChatColor DARK_BLUE = new LegacyChatColor('1', "dark_blue", new Color(0x0000AA));
    /**
     * Represents dark green.
     */
    public static final LegacyChatColor DARK_GREEN = new LegacyChatColor('2', "dark_green", new Color(0x00AA00));
    /**
     * Represents dark blue (aqua).
     */
    public static final LegacyChatColor DARK_AQUA = new LegacyChatColor('3', "dark_aqua", new Color(0x00AAAA));
    /**
     * Represents dark red.
     */
    public static final LegacyChatColor DARK_RED = new LegacyChatColor('4', "dark_red", new Color(0xAA0000));
    /**
     * Represents dark purple.
     */
    public static final LegacyChatColor DARK_PURPLE = new LegacyChatColor('5', "dark_purple", new Color(0xAA00AA));
    /**
     * Represents gold.
     */
    public static final LegacyChatColor GOLD = new LegacyChatColor('6', "gold", new Color(0xFFAA00));
    /**
     * Represents gray.
     */
    public static final LegacyChatColor GRAY = new LegacyChatColor('7', "gray", new Color(0xAAAAAA));
    /**
     * Represents dark gray.
     */
    public static final LegacyChatColor DARK_GRAY = new LegacyChatColor('8', "dark_gray", new Color(0x555555));
    /**
     * Represents blue.
     */
    public static final LegacyChatColor BLUE = new LegacyChatColor('9', "blue", new Color(0x5555FF));
    /**
     * Represents green.
     */
    public static final LegacyChatColor GREEN = new LegacyChatColor('a', "green", new Color(0x55FF55));
    /**
     * Represents aqua.
     */
    public static final LegacyChatColor AQUA = new LegacyChatColor('b', "aqua", new Color(0x55FFFF));
    /**
     * Represents red.
     */
    public static final LegacyChatColor RED = new LegacyChatColor('c', "red", new Color(0xFF5555));
    /**
     * Represents light purple.
     */
    public static final LegacyChatColor LIGHT_PURPLE = new LegacyChatColor('d', "light_purple", new Color(0xFF55FF));
    /**
     * Represents yellow.
     */
    public static final LegacyChatColor YELLOW = new LegacyChatColor('e', "yellow", new Color(0xFFFF55));
    /**
     * Represents white.
     */
    public static final LegacyChatColor WHITE = new LegacyChatColor('f', "white", new Color(0xFFFFFF));
    /**
     * Represents magical characters that change around randomly.
     */
    public static final LegacyChatColor MAGIC = new LegacyChatColor('k', "obfuscated");
    /**
     * Makes the text bold.
     */
    public static final LegacyChatColor BOLD = new LegacyChatColor('l', "bold");
    /**
     * Makes a line appear through the text.
     */
    public static final LegacyChatColor STRIKETHROUGH = new LegacyChatColor('m', "strikethrough");
    /**
     * Makes the text appear underlined.
     */
    public static final LegacyChatColor UNDERLINE = new LegacyChatColor('n', "underline");
    /**
     * Makes the text italic.
     */
    public static final LegacyChatColor ITALIC = new LegacyChatColor('o', "italic");
    /**
     * Resets all previous chat colors or formats.
     */
    public static final LegacyChatColor RESET = new LegacyChatColor('r', "reset");
    /**
     * Colour instances keyed by their active character.
     */
    private static final Map<Character, LegacyChatColor> BY_CHAR = new HashMap<Character, LegacyChatColor>();
    /**
     * Colour instances keyed by their name.
     */
    private static final Map<String, LegacyChatColor> BY_NAME = new HashMap<String, LegacyChatColor>();
    /**
     * Count used for populating legacy ordinal.
     */
    private static int count = 0;
    /**
     * This colour's colour char prefixed by the {@link #COLOR_CHAR}.
     */
    private final String toString;
    @Getter
    private final String name;
    private final int ordinal;
    /**
     * The RGB color of the LegacyChatColor. null for non-colors (formatting)
     */
    @Getter
    private final Color color;

    private LegacyChatColor(char code, String name) {
        this(code, name, null);
    }

    private LegacyChatColor(char code, String name, Color color) {
        this.name = name;
        this.toString = new String(new char[]{COLOR_CHAR, code});
        this.ordinal = count++;
        this.color = color;

        BY_CHAR.put(code, this);
        BY_NAME.put(name.toUpperCase(Locale.ROOT), this);
    }

    private LegacyChatColor(String name, String toString, int rgb) {
        this.name = name;
        this.toString = toString;
        this.ordinal = -1;
        this.color = new Color(rgb);
    }

    /**
     * Strips the given message of all color codes
     *
     * @param input String to strip of color
     * @return A copy of the input string, without any coloring
     */
    public static String stripColor(final String input) {
        if (input == null) {
            return null;
        }

        return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }

    public static String translateAlternateColorCodes(char altColorChar, String textToTranslate) {
        char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == altColorChar && ALL_CODES.indexOf(b[i + 1]) > -1) {
                b[i] = LegacyChatColor.COLOR_CHAR;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }

    /**
     * Get the colour represented by the specified code.
     *
     * @param code the code to search for
     * @return the mapped colour, or null if non exists
     */
    public static LegacyChatColor getByChar(char code) {
        return BY_CHAR.get(code);
    }

    public static LegacyChatColor of(Color color) {
        return of("#" + String.format("%08x", color.getRGB()).substring(2));
    }

    public static LegacyChatColor of(String string) {
        Preconditions.checkArgument(string != null, "string cannot be null");
        if (string.length() == 7 && string.charAt(0) == '#') {
            int rgb;
            try {
                rgb = Integer.parseInt(string.substring(1), 16);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Illegal hex string " + string);
            }

            StringBuilder magic = new StringBuilder(COLOR_CHAR + "x");
            for (char c : string.substring(1).toCharArray()) {
                magic.append(COLOR_CHAR).append(c);
            }

            return new LegacyChatColor(string, magic.toString(), rgb);
        }

        LegacyChatColor defined = BY_NAME.get(string.toUpperCase(Locale.ROOT));
        if (defined != null) {
            return defined;
        }

        throw new IllegalArgumentException("Could not parse LegacyChatColor " + string);
    }

    /**
     * See {@link Enum#valueOf(java.lang.Class, java.lang.String)}.
     *
     * @param name color name
     * @return LegacyChatColor
     * @deprecated holdover from when this class was an enum
     */
    @Deprecated
    public static LegacyChatColor valueOf(String name) {
        Preconditions.checkNotNull(name, "Name is null");

        LegacyChatColor defined = BY_NAME.get(name);
        Preconditions.checkArgument(defined != null, "No enum constant " + LegacyChatColor.class.getName() + "." + name);

        return defined;
    }

    /**
     * Get an array of all defined colors and formats.
     *
     * @return copied array of all colors and formats
     * @deprecated holdover from when this class was an enum
     */
    @Deprecated
    public static LegacyChatColor[] values() {
        return BY_CHAR.values().toArray(new LegacyChatColor[0]);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.toString);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final LegacyChatColor other = (LegacyChatColor) obj;

        return Objects.equals(this.toString, other.toString);
    }

    @Override
    public String toString() {
        return toString;
    }

    /**
     * See {@link Enum#name()}.
     *
     * @return constant name
     * @deprecated holdover from when this class was an enum
     */
    @Deprecated
    public String name() {
        return getName().toUpperCase(Locale.ROOT);
    }

    /**
     * See {@link Enum#ordinal()}.
     *
     * @return ordinal
     * @deprecated holdover from when this class was an enum
     */
    @Deprecated
    public int ordinal() {
        Preconditions.checkArgument(ordinal >= 0, "Cannot get ordinal of hex color");
        return ordinal;
    }
}