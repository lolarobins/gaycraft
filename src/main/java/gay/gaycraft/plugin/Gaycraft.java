package gay.gaycraft.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import gay.gaycraft.plugin.command.Color;
import gay.gaycraft.plugin.command.Nick;
import gay.gaycraft.plugin.command.Pronouns;
import gay.gaycraft.plugin.listener.Chat;
import gay.gaycraft.plugin.listener.Join;
import gay.gaycraft.plugin.listener.Leave;
import gay.gaycraft.plugin.listener.Motd;

public class Gaycraft extends JavaPlugin {

    public void onEnable() {
        // listeners
        getServer().getPluginManager().registerEvents(new Motd(), this);
        getServer().getPluginManager().registerEvents(new Chat(this), this);
        getServer().getPluginManager().registerEvents(new Join(this), this);
        getServer().getPluginManager().registerEvents(new Leave(this), this);

        //commands
        getCommand("color").setExecutor(new Color(this));
        getCommand("pronouns").setExecutor(new Pronouns(this));
        getCommand("pronouns").setTabCompleter(new Pronouns.TC());
        getCommand("nick").setExecutor(new Nick(this));
    }

    public void onDisable() {

    }
    
}
