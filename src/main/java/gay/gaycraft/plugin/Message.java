package gay.gaycraft.plugin;

import net.md_5.bungee.api.ChatColor;

public final class Message {

    private Message() {
    }

    // for repeated messages, errors

    public static final String ERROR = ChatColor.DARK_RED + "" + ChatColor.BOLD + "Error: " + ChatColor.RED;

    public static final String NO_PERMISSION = ERROR +
            "You do not have permission to use this command.";
    public static final String INVALID_ARGUMENTS = ERROR + "Invalid arguments.";
    public static final String INVALID_TIME_UNIT = ERROR + "Invalid time unit specified.";

    public static final String NO_REASON = "No reason specified";

    public static final String PLAYER_NOT_FOUND = ERROR + "Player not found.";

}
