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

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class PlayerWrapper {

    private transient OfflinePlayer player;
    private transient File file;

    private String displayname, color = "#FF69B4", pronouns, join;
    
    public PlayerWrapper(JavaPlugin plugin, OfflinePlayer player) {
        file = new File(plugin.getDataFolder(), "player/" + player.getUniqueId() + ".json");
        this.player = player;

        if (file.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                Gson gson = new Gson();

                PlayerWrapper wrapper = gson.fromJson(new String(inputStream.readAllBytes()), PlayerWrapper.class);

                displayname = wrapper.displayname;
                color = wrapper.color;
                pronouns = wrapper.pronouns;
                join = wrapper.join;

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
                .color(ChatColor.of(color))
                .append(username)
                .append(pronouns)
                .append("\nWorld: ")
                .color(ChatColor.AQUA)
                .append(player.getPlayer().getLocation().getWorld().getName())
                .color(ChatColor.WHITE)
                .append("\nJoined: ")
                .color(ChatColor.AQUA)
                .append(getJoinDate())
                .color(ChatColor.WHITE)
                .create();
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
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        save();
    }

    public String getJoinDate() {
        return join;
    }

}
