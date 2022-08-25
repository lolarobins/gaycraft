package gay.gaycraft.plugin.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import gay.gaycraft.plugin.wrapper.PlayerWrapper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class Chat implements Listener {

    private final JavaPlugin plugin;

    public Chat(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent event) {
        // cancel event to use chat components
        event.getRecipients().clear();

        PlayerWrapper playerWrapper = new PlayerWrapper(plugin, event.getPlayer());

        BaseComponent[] component = new ComponentBuilder(playerWrapper.getDisplayName())
                // player name colour
                .color(ChatColor.of(playerWrapper.getColor()))
                // player details on hover (current world, etc)
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new Text(playerWrapper.getHoverText())))
                .append(" > ")
                .event((HoverEvent) null)
                .color(ChatColor.DARK_GRAY)
                .append(event.getMessage())
                .color(ChatColor.WHITE)
                .create();

        for (Player p : Bukkit.getOnlinePlayers())
            p.spigot().sendMessage(component);
    }
}
