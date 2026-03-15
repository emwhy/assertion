package org.emw.assertfire.util;

/**
 * This helps color coding log text output.
 */
public enum AnsiEscapeText {
    //Color end string, color reset
    Reset("\033[0m"),

    // Regular Colors. Normal color, no bold, background color etc.
    Black("\033[0;30m"),    // BLACK
    Red("\033[0;31m"),      // RED
    Green("\033[0;32m"),    // GREEN
    Yellow("\033[0;33m"),   // YELLOW
    Blue("\033[0;34m"),     // BLUE
    Magenta("\033[0;35m"),  // MAGENTA
    Cyan("\033[0;36m"),     // CYAN
    White("\033[0;37m"),    // WHITE

    // Bold
    BlackBold("\033[1;30m"),   // BLACK
    RedBold("\033[1;31m"),     // RED
    GreenBold("\033[1;32m"),   // GREEN
    YellowBold("\033[1;33m"),  // YELLOW
    BlueBold("\033[1;34m"),    // BLUE
    MagentaBold("\033[1;35m"), // MAGENTA
    CyanBold("\033[1;36m"),    // CYAN
    WhiteBold("\033[1;37m"),   // WHITE

    // Underline
    BlackUnderlined("\033[4;30m"),     // BLACK
    RedUnderlined("\033[4;31m"),       // RED
    GreenUnderlined("\033[4;32m"),     // GREEN
    YellowUnderlined("\033[4;33m"),    // YELLOW
    BlueUnderlined("\033[4;34m"),      // BLUE
    MagentaUnderlined("\033[4;35m"),   // MAGENTA
    CyanUnderlined("\033[4;36m"),      // CYAN
    WhiteUnderlined("\033[4;37m"),     // WHITE

    // Background
    BlackBackground("\033[40m"),   // BLACK
    RedBackground("\033[41m"),     // RED
    GreenBackground("\033[42m"),   // GREEN
    YellowBackground("\033[43m"),  // YELLOW
    BlueBackground("\033[44m"),    // BLUE
    MagentaBackground("\033[45m"), // MAGENTA
    CyanBackground("\033[46m"),    // CYAN
    WhiteBackground("\033[47m"),   // WHITE

    // High Intensity
    BlackBright("\033[0;90m"),     // BLACK
    RedBright("\033[0;91m"),       // RED
    GreenBright("\033[0;92m"),     // GREEN
    YellowBright("\033[0;93m"),    // YELLOW
    BlueBright("\033[0;94m"),      // BLUE
    MagentaBright("\033[0;95m"),   // MAGENTA
    CyanBright("\033[0;96m"),      // CYAN
    WhiteBright("\033[0;97m"),     // WHITE

    // Bold High Intensity
    BlackBoldBright("\033[1;90m"),    // BLACK
    RedBoldBright("\033[1;91m"),      // RED
    GreenBoldBright("\033[1;92m"),    // GREEN
    YellowBoldBright("\033[1;93m"),   // YELLOW
    BlueBoldBright("\033[1;94m"),     // BLUE
    MagentaBoldBright("\033[1;95m"),  // MAGENTA
    CyanBoldBright("\033[1;96m"),     // CYAN
    WhiteBoldBright("\033[1;97m"),    // WHITE

    // High Intensity backgrounds
    BlackBackgroundBright("\033[0;100m"),     // BLACK
    RedBackgroundBright("\033[0;101m"),       // RED
    GreenBackgroundBright("\033[0;102m"),     // GREEN
    YellowBackgroundBright("\033[0;103m"),    // YELLOW
    BlueBackgroundBright("\033[0;104m"),      // BLUE
    MagentaBackgroundBright("\033[0;105m"),   // MAGENTA
    CyanBackgroundBright("\033[0;106m"),      // CYAN
    WhiteBackgroundBright("\033[0;107m");     // WHITE

    private final String code;

    AnsiEscapeText(String code) {
        this.code = code;
    }

    /**
     * Mark the text with the color code, then reset it at the end.
     * @param text
     * @return
     */
    public String text(String text) {
        if (this == Reset) {
            throw new RuntimeException("Cannot generate text from Reset");
        }
        return this.code + text + Reset.code;
    }

    /**
     * Clear any color coding from the text.
     * @param text
     * @return
     */
    public static String clear(String text) {
        for (AnsiEscapeText escapeText : AnsiEscapeText.values()) {
            text = text.replace(escapeText.toString(), "");
        }
        return text;
    }

    @Override
    public String toString() {
        return code;
    }
}