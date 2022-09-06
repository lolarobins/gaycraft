package gay.gaycraft.plugin.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import gay.gaycraft.plugin.Message;
import gay.gaycraft.plugin.permission.Group;
import gay.gaycraft.plugin.permission.Permission;
import gay.gaycraft.plugin.wrapper.PlayerWrapper;
import net.md_5.bungee.api.ChatColor;

public class Rank implements CommandExecutor {

    private final JavaPlugin plugin;

    public Rank(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && !Permission.has(new PlayerWrapper(plugin, (Player) sender), Permission.RANK)) {
            sender.sendMessage(Message.NO_PERMISSION);
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage(Message.INVALID_ARGUMENTS);
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        if (!target.hasPlayedBefore()) {
            sender.sendMessage(Message.PLAYER_NOT_FOUND);
        }

        Group group = null;
        try {
            group = Group.valueOf(args[1].toUpperCase());
        } catch (Exception e) {
            sender.sendMessage(Message.ERROR + "Rank does not exist.");
            return true;
        }

        PlayerWrapper targetWrapper = PlayerWrapper.getTarget(plugin, target);

        if (sender instanceof Player) {
            PlayerWrapper playerWrapper = new PlayerWrapper(plugin, (Player) sender);

            if (targetWrapper.getGroup().inherits(playerWrapper.getGroup())) {
                sender.sendMessage(Message.ERROR + "You do not have permission to modify the specified player's rank.");
                return true;
            }

            if (playerWrapper.getGroup().inherits(group)) {
                sender.sendMessage(Message.ERROR + "You can't promote to this rank.");
                return true;
            }
        }

        targetWrapper.setGroup(group);

        sender.sendMessage(ChatColor.GREEN + "Set " + target.getName() + " to " + group.getDisplayName() + ".");

        return true;
    }

}
