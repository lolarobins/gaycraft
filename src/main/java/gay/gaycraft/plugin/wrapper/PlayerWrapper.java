package gay.gaycraft.plugin.wrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;

import gay.gaycraft.plugin.permission.Group;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class PlayerWrapper {

    private transient OfflinePlayer player;
    private transient File file;
    private transient JavaPlugin plugin;

    private boolean muted = false, banned = false;
    private String displayname, color, pronouns, join, group, muteReason, banReason;
    private long banExpire, muteExpire;

    public PlayerWrapper(JavaPlugin plugin, OfflinePlayer player) {
        file = new File(plugin.getDataFolder(), "player/" + player.getUniqueId() + ".json");

        this.player = player;
        this.plugin = plugin;

        if (file.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                Gson gson = new Gson();

                PlayerWrapper wrapper = gson.fromJson(new String(inputStream.readAllBytes()), PlayerWrapper.class);

                displayname = wrapper.displayname;
                color = wrapper.color;
                pronouns = wrapper.pronouns;
                join = wrapper.join;
                group = wrapper.group;

                inputStream.close();
            } catch (IOException e) {
                // shouldn't happen.
                e.printStackTrace();
            }
        } else {
            // create and save
            file.getParentFile().mkdirs();

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // set join date
            Date date = new Date(System.currentTimeMillis());
            Calendar calendar = Calendar.getInstance();

            calendar.setTime(date);

            String month = null;
            switch (calendar.get(Calendar.MONTH)) {
                case 0:
                    month = "January";
                    break;
                case 1:
                    month = "February";
                    break;
                case 2:
                    month = "March";
                    break;
                case 3:
                    month = "April";
                    break;
                case 4:
                    month = "May";
                    break;
                case 5:
                    month = "June";
                    break;
                case 6:
                    month = "July";
                    break;
                case 7:
                    month = "August";
                    break;
                case 8:
                    month = "September";
                    break;
                case 9:
                    month = "October";
                    break;
                case 10:
                    month = "November";
                    break;
                case 11:
                    month = "December";
                    break;
                default:
                    break;
            }

            String suffix = null;
            switch (calendar.get(Calendar.DAY_OF_MONTH) % 10) {
                case 1:
                    suffix = "st";
                    break;
                case 2:
                    suffix = "nd";
                    break;
                case 3:
                    suffix = "rd";
                    break;
                default:
                    suffix = "th";
                    break;
            }

            join = month + " " + calendar.get(Calendar.DAY_OF_MONTH) + suffix + ", "
                    + calendar.get(Calendar.YEAR);

            save();
        }
    }

    private void save() {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            Gson gson = new Gson();

            outputStream.write(gson.toJson(this).getBytes(StandardCharsets.UTF_8));
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BaseComponent[] getHoverText() {
        BaseComponent[] username = isDisplayNameSet()
                ? new ComponentBuilder("\nUsername: ")
                        .color(ChatColor.AQUA)
                        .append(player.getName())
                        .color(ChatColor.WHITE)
                        .create()
                : new ComponentBuilder("")
                        .create();

        BaseComponent[] pronouns = this.pronouns != null
                ? new ComponentBuilder("\nPronouns: ")
                        .color(ChatColor.AQUA)
                        .append(getPronouns())
                        .color(ChatColor.WHITE)
                        .create()
                : new ComponentBuilder("")
                        .create();

        return new ComponentBuilder(getDisplayName())
                .color(ChatColor.of(getColor()))
                .append(username)
                .append(pronouns)
                .append("\nWorld: ")
                .color(ChatColor.AQUA)
                .append(new WorldWrapper(plugin, player.getPlayer().getLocation().getWorld()).getDisplayName())
                .color(ChatColor.WHITE)
                .append("\nRank: ")
                .color(ChatColor.AQUA)
                .append(getGroup().getDisplayName())
                .color(ChatColor.WHITE)
                .append("\nJoined: ")
                .color(ChatColor.AQUA)
                .append(getJoinDate())
                .color(ChatColor.WHITE)
                .create();
    }

    public static PlayerWrapper getTarget(JavaPlugin plugin, OfflinePlayer player) {
        File file = new File(plugin.getDataFolder(), "player/" + player.getUniqueId() + ".json");

        if (file.exists()) {
            PlayerWrapper wrapper = null;

            try {
                FileInputStream inputStream = new FileInputStream(file);
                Gson gson = new Gson();

                wrapper = gson.fromJson(new String(inputStream.readAllBytes()), PlayerWrapper.class);

                wrapper.plugin = plugin;
                wrapper.player = player;
                wrapper.file = file;

                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return wrapper;
        }

        return null;
    }

    public String getPronouns() {
        return pronouns;
    }

    public void setPronouns(String pronouns) {
        this.pronouns = pronouns;
        save();
    }

    public String getDisplayName() {
        if (displayname == null)
            return player.getName();

        return displayname;
    }

    public boolean isDisplayNameSet() {
        return displayname != null;
    }

    public void setDisplayName(String displayname) {
        this.displayname = displayname;
        save();
    }

    public String getColor() {
        if (color == null)
            return "#FF69B4";

        return color;
    }

    public void setColor(String color) {
        this.color = color;
        save();
    }

    public String getJoinDate() {
        return join;
    }

    public Group getGroup() {
        if (group == null)
            return Group.MEMBER;

        return Group.valueOf(group);
    }

    public void setGroup(Group group) {
        if (player.isOnline())
            player.getPlayer()
                    .sendMessage(ChatColor.LIGHT_PURPLE + "Your rank has been set to " + group.getDisplayName() + ".");

        this.group = group.name();
        save();
    }

    public void mute(long duration, String reason) {
        if (player.isOnline())
            player.getPlayer().sendMessage(ChatColor.RED + "You have been muted for '" + reason + "'.");

        if (duration == 0)
            muteExpire = 0;
        else if (muteExpire < System.currentTimeMillis())
            muteExpire = System.currentTimeMillis() + duration;
        else
            muteExpire += duration;

        muted = true;
        muteReason = reason;

        save();
    }

    public void unmute() {
        muted = false;
        save();
    }

    public boolean isMuted() {
        if (!muted)
            return false;

        if (muteExpire != 0 && muteExpire < System.currentTimeMillis()) {
            muted = false;
            save();
            return false;
        }

        return true;
    }

    public String getMuteReason() {
        return muteReason;
    }

    public void ban(long duration, String reason) {
        if (player.isOnline())
            player.getPlayer().kickPlayer(reason);

        if (duration == 0)
            banExpire = duration;
        else if (banExpire < System.currentTimeMillis())
            banExpire = System.currentTimeMillis() + duration;
        else
            banExpire += duration;

        banned = true;
        banReason = reason;

        save();
    }

    public void unban() {
        banned = false;
        save();
    }

    public boolean isBanned() {
        if (!banned)
            return false;

        if (banExpire != 0 && banExpire < System.currentTimeMillis()) {
            banned = false;
            save();
            return false;
        }

        return true;
    }

    public String getBanReason() {
        return banReason;
    }

}
