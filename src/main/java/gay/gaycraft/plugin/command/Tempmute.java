package gay.gaycraft.plugin.command;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import gay.gaycraft.plugin.Message;
import gay.gaycraft.plugin.permission.Permission;
import gay.gaycraft.plugin.wrapper.PlayerWrapper;
import net.md_5.bungee.api.ChatColor;

public class Tempmute implements CommandExecutor {

    private final JavaPlugin plugin;

    public Tempmute(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && !Permission.has(new PlayerWrapper(plugin, (Player) sender), Permission.MUTE)) {
            sender.sendMessage(Message.NO_PERMISSION);
            return true;
        }
        
        if (args.length < 3) {
            sender.sendMessage(Message.INVALID_ARGUMENTS);
            return true;
        }
        
        int amount = 0;

        try {
            amount = Integer.parseInt(args[1]);

            if (amount == 0) {
                sender.sendMessage(Message.INVALID_TIME_UNIT);
                return true;
            }
        } catch (NumberFormatException e) {
            sender.sendMessage(Message.INVALID_TIME_UNIT);
            return true;
        }

        long duration = 0;

        switch (args[2].toLowerCase()) {
            case "seconds":
                duration = TimeUnit.SECONDS.toMillis(amount);
                break;
            case "minutes":
                duration = TimeUnit.MINUTES.toMillis(amount);
                break;
            case "hours":
                duration = TimeUnit.HOURS.toMillis(amount);
                break;
            case "days":
                duration = TimeUnit.DAYS.toMillis(amount);
                break;
            default:
                sender.sendMessage(Message.INVALID_TIME_UNIT);
                return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(Message.PLAYER_NOT_FOUND);
            return true;
        }

        PlayerWrapper pWrapper = new PlayerWrapper(plugin, target);

        if (args.length == 3)
            pWrapper.mute(duration, Message.NO_REASON);
        else {
            String message = args[3];
            
            for (int i = 4; i < args.length; i++)
                message += " " + args[i];
    
            pWrapper.mute(duration,ChatColor.translateAlternateColorCodes('&', message));
        }
        
        sender.sendMessage(ChatColor.GREEN + "Muted " + target.getName() + ".");

        return true;
    }

    public static class TC implements TabCompleter {

        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
            if(args.length == 2) {
                return List.of("<amt>");
            } else if (args.length == 3) {
                return List.of("seconds", "minutes", "hours", "days");
            }
    
            return null;
        }
        
    }
    
}
