package gay.gaycraft.plugin.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import net.md_5.bungee.api.ChatColor;

public class Motd implements Listener {

    @EventHandler
    public void motdEvent(ServerListPingEvent event) {
        event.setMotd(
                ChatColor.of("#FFFFFF") + "" + ChatColor.BOLD + "g" +
                        ChatColor.of("#FFAFC8") + "" + ChatColor.BOLD + "a" +
                        ChatColor.of("#74D7EE") + "" + ChatColor.BOLD + "y " +
                        ChatColor.of("#613915") + "" + ChatColor.BOLD + "c" +
                        ChatColor.of("#000000") + "" + ChatColor.BOLD + "r" +
                        ChatColor.of("#E40303") + "" + ChatColor.BOLD + "a" +
                        ChatColor.of("#FF8C00") + "" + ChatColor.BOLD + "f" +
                        ChatColor.of("#FFED00") + "" + ChatColor.BOLD + "t" +
                        ChatColor.of("#008026") + "" + ChatColor.BOLD + "i" +
                        ChatColor.of("#24408E") + "" + ChatColor.BOLD + "n" +
                        ChatColor.of("#732982") + "" + ChatColor.BOLD + "g");
    }

}
