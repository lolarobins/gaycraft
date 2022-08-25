package gay.gaycraft.plugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import gay.gaycraft.plugin.Message;
import gay.gaycraft.plugin.wrapper.PlayerWrapper;
import net.md_5.bungee.api.ChatColor;

public class Nick implements CommandExecutor {

    private final JavaPlugin plugin;

    public Nick(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;

        if (args.length < 1) {
            sender.sendMessage(Message.INVALID_ARGUMENTS);
            return true;
        }

        PlayerWrapper playerWrapper = new PlayerWrapper(plugin, (Player) sender);

        if (args[0].equalsIgnoreCase("off")) {
            playerWrapper.setDisplayName(null);
            sender.sendMessage(ChatColor.GREEN + "Your nickname has been removed.");
            return true;
        }

        String nick = args[0];
        for (int i = 1; i < args.length; i++)
            nick += " " + args[i];
        
        if (nick.length() > 32) {
            sender.sendMessage(Message.ERROR + "Nickname must be less than 32 characters");
            return true;
        }

        playerWrapper.setDisplayName(nick);
        sender.sendMessage(ChatColor.GREEN + "Your nickname has been set to '" + nick + "'.");

        return true;
    }
    
}
