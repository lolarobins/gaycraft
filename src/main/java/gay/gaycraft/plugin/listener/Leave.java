package gay.gaycraft.plugin.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import gay.gaycraft.plugin.wrapper.PlayerWrapper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class Leave implements Listener {
    
    private final JavaPlugin plugin;

    public Leave(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void joinEvent(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        PlayerWrapper playerWrapper = new PlayerWrapper(plugin, event.getPlayer());

        BaseComponent[] component = new ComponentBuilder("> ")
                .color(ChatColor.DARK_GRAY)
                .append(playerWrapper.getDisplayName())
                .color(ChatColor.of(playerWrapper.getColor()))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new Text(playerWrapper.getHoverText())))
                .append(" joined the server")
                .event((HoverEvent) null)
                .create();

        for (Player p : Bukkit.getOnlinePlayers())
            p.spigot().sendMessage(component);
    }

}
