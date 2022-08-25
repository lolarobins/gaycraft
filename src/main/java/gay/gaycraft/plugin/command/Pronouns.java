package gay.gaycraft.plugin.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import gay.gaycraft.plugin.Message;
import gay.gaycraft.plugin.wrapper.PlayerWrapper;
import net.md_5.bungee.api.ChatColor;

public class Pronouns implements CommandExecutor{
    
    private final JavaPlugin plugin;

    public Pronouns(JavaPlugin plugin) {
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
        
        new PlayerWrapper(plugin, (Player) sender).setPronouns(args[0].toLowerCase());
        sender.sendMessage(ChatColor.GREEN + "Your pronouns have been set to '" + args[0].toLowerCase() + "'.");

        return true;
    }

    public static class TC implements TabCompleter {

        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
            return List.of("she/her", "he/him", "they/them", "it/its");
        }
        
    }
    

}
