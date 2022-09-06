package gay.gaycraft.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import gay.gaycraft.plugin.command.Ban;
import gay.gaycraft.plugin.command.Color;
import gay.gaycraft.plugin.command.Kick;
import gay.gaycraft.plugin.command.Mute;
import gay.gaycraft.plugin.command.Nick;
import gay.gaycraft.plugin.command.Pronouns;
import gay.gaycraft.plugin.command.Rank;
import gay.gaycraft.plugin.command.Tempban;
import gay.gaycraft.plugin.command.Tempmute;
import gay.gaycraft.plugin.command.Unban;
import gay.gaycraft.plugin.command.Unmute;
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

        // commands

        getCommand("color").setExecutor(new Color(this));

        getCommand("pronouns").setExecutor(new Pronouns(this));
        getCommand("pronouns").setTabCompleter(new Pronouns.TC());

        getCommand("nick").setExecutor(new Nick(this));

        getCommand("rank").setExecutor(new Rank(this));

        getCommand("ban").setExecutor(new Ban(this));

        getCommand("kick").setExecutor(new Kick(this));

        getCommand("mute").setExecutor(new Mute(this));

        getCommand("tempban").setExecutor(new Tempban(this));
        getCommand("tempban").setTabCompleter(new Tempban.TC());

        getCommand("tempmute").setExecutor(new Tempmute(this));
        getCommand("tempmute").setTabCompleter(new Tempmute.TC());

        getCommand("unban").setExecutor(new Unban(this));

        getCommand("unmute").setExecutor(new Unmute(this));
    }

    public void onDisable() {

    }

}
