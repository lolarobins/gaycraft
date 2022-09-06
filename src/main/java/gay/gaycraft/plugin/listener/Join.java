package gay.gaycraft.plugin.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;

import gay.gaycraft.plugin.wrapper.PlayerWrapper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class Join implements Listener {

    private final JavaPlugin plugin;

    public Join(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void joinEvent(PlayerJoinEvent event) {
        event.setJoinMessage(null);

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

        event.getPlayer().setPlayerListName(ChatColor.of(playerWrapper.getColor()) + playerWrapper.getDisplayName());
    }

    @EventHandler
    public void preJoinEvent(PlayerLoginEvent event) {
        PlayerWrapper pWrapper = new PlayerWrapper(plugin, event.getPlayer());

        if (pWrapper.isBanned())
            event.disallow(Result.KICK_BANNED, pWrapper.getBanReason());
    }
    
}
