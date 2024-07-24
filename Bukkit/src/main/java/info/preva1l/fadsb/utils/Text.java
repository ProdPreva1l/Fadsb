package info.preva1l.fadsb.utils;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public final class Text {
    private static final Pattern HEX_PATTERN = Pattern.compile("&#(\\w{5}[0-9a-fA-F])");

    public String miniToString(TextComponent textComponent) {
        return MiniMessage.miniMessage().serialize(textComponent);
    }

    /**
     * Colorize a legacy string.
     * @param text String with color codes or hex codes.
     * @return Colorized String
     */
    @SuppressWarnings("deprecation")
    private String colorize(String text) {
        Matcher matcher = HEX_PATTERN.matcher(text);
        StringBuilder buffer = new StringBuilder();

        while(matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString());
        }

        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }

    /**
     * Converts legacy colour codes to MiniMessage.
     * @param message message with legacy codes
     * @return string with mini modernMessage formatting (not colorized)
     */
    public String legacyToMiniMessage(String message) {
        message = message.replace("&4", "<dark_red>");
        message = message.replace("&c", "<red>");
        message = message.replace("&6", "<gold>");
        message = message.replace("&e", "<yellow>");
        message = message.replace("&2", "<dark_green>");
        message = message.replace("&a", "<green>");
        message = message.replace("&b", "<aqua>");
        message = message.replace("&3", "<dark_aqua>");
        message = message.replace("&1", "<dark_blue>");
        message = message.replace("&9", "<blue>");
        message = message.replace("&d", "<light_purple>");
        message = message.replace("&5", "<dark_purple>");
        message = message.replace("&f", "<white>");
        message = message.replace("&7", "<gray>");
        message = message.replace("&8", "<dark_gray>");
        message = message.replace("&0", "<black>");
        message = message.replace("&l", "<b>");
        message = message.replace("&k", "<obf>");
        message = message.replace("&m", "<st>");
        message = message.replace("&n", "<u>");
        message = message.replace("&o", "<i>");
        message = message.replace("&r", "<reset>");

        Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");
        Matcher match = pattern.matcher(message);
        String code = message;
        while (match.find()) {
            code = message.substring(match.start(),match.end());
            code = code.replace("&", "<");
            code = code + ">";
        }
        return message.replaceAll("&#[a-fA-F0-9]{6}", code);
    }

    /**
     * Converts MiniMessage to legacy colour codes.
     * @param message message with mini message formatting
     * @return string with legacy formatting (not colorized)
     */
    public String miniMessageToLegacy(String message) {
        message = message.replace("<dark_red>", "&4");
        message = message.replace("<red>", "&c");
        message = message.replace("<gold>", "&6");
        message = message.replace("<yellow>", "&e");
        message = message.replace("<dark_green>", "&2");
        message = message.replace("<green>", "&a");
        message = message.replace("<aqua>", "&b");
        message = message.replace("<dark_aqua>", "&3");
        message = message.replace("<dark_blue>", "&1");
        message = message.replace("<blue>", "&9");
        message = message.replace("<light_purple>", "&d");
        message = message.replace("<dark_purple>", "&5");
        message = message.replace("<white>", "&f");
        message = message.replace("<gray>", "&7");
        message = message.replace("<dark_gray>", "&8");
        message = message.replace("<black>", "&0");
        message = message.replace("<b>", "&l");
        message = message.replace("<obf>", "&k");
        message = message.replace("<st>", "&m");
        message = message.replace("<u>", "&n");
        message = message.replace("<i>", "&o");
        message = message.replace("<reset>", "&r");


        Pattern pattern = Pattern.compile("<#[a-fA-F0-9]{6}>");
        Matcher match = pattern.matcher(message);
        String code = message;
        while (match.find()) {
            code = message.substring(match.start(), match.end());
            code = code.replace("<", "&");
            code = code.replace(">", "");
        }
        return message.replaceAll("<#[a-fA-F0-9]{6}>", code);
    }

    /**
     * Takes a string formatted in minimessage OR legacy and turns it into an Adventure Component.
     * @param message the modernMessage
     * @return colorized component
     */
    public Component modernMessage(@NotNull String message) {
        return MiniMessage.miniMessage().deserialize(legacyToMiniMessage(message));
    }

    /**
     * Takes a string formatted in minimessage OR legacy and turns it into a legacy String.
     * @param message the modernMessage
     * @return colorized component
     */
    public String legacyMessage(@NotNull String message) {
        return colorize(miniMessageToLegacy(message));
    }
}