package gay.gaycraft.plugin.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import gay.gaycraft.plugin.Message;
import gay.gaycraft.plugin.permission.Permission;
import gay.gaycraft.plugin.wrapper.PlayerWrapper;
import net.md_5.bungee.api.ChatColor;

public class Mute implements CommandExecutor {

    private final JavaPlugin plugin;

    public Mute(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && !Permission.has(new PlayerWrapper(plugin, (Player) sender), Permission.MUTE)) {
            sender.sendMessage(Message.NO_PERMISSION);
            return true;
        }
        
        if (args.length == 0) {
            sender.sendMessage(Message.INVALID_ARGUMENTS);
            return true;
        }

        OfflinePlayer target = Bukkit.getPlayer(args[0]);

        if (!target.hasPlayedBefore()) {
            sender.sendMessage(Message.PLAYER_NOT_FOUND);
            return true;
        }

        PlayerWrapper pWrapper = new PlayerWrapper(plugin, target);

        if (args.length == 1)
            pWrapper.mute(0, Message.NO_REASON);
        else {
            String message = args[1];
            
            for (int i = 2; i < args.length; i++)
                message += " " + args[i];
    
            pWrapper.mute(0, ChatColor.translateAlternateColorCodes('&', message));
        }

        sender.sendMessage(ChatColor.GREEN + "Muted " + target.getName() + ".");

        return true;
    }
    
}
