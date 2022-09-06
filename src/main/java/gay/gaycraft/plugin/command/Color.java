package gay.gaycraft.plugin.command;

import java.util.regex.Pattern;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import gay.gaycraft.plugin.Message;
import gay.gaycraft.plugin.wrapper.PlayerWrapper;
import net.md_5.bungee.api.ChatColor;

public class Color implements CommandExecutor {

    private final JavaPlugin plugin;

    public Color(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;

        if (args.length != 1) {
            sender.sendMessage(Message.INVALID_ARGUMENTS);
            return true;
        }

        if (!Pattern.compile("^#([a-fA-F0-9]{6})$").matcher(args[0]).matches()) {
            sender.sendMessage(Message.ERROR + "Invalid hex code format.");
            return true;
        }

        PlayerWrapper playerWrapper = new PlayerWrapper(plugin, (Player) sender);

        playerWrapper.setColor(args[0]);
        sender.sendMessage(ChatColor.GREEN + "Your color has been set.");

        ((Player) sender).setPlayerListName(ChatColor.of(args[0]) + playerWrapper.getDisplayName());

        return true;
    }

}
