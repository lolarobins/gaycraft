package gay.gaycraft.plugin.wrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;

public class WorldWrapper {

    private transient World world;
    private transient File file;

    private String displayname;

    public WorldWrapper(JavaPlugin plugin, World world) {
        file = new File(plugin.getDataFolder(), "world/" + world.getName() + ".json");

        this.world = world;

        if (file.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                Gson gson = new Gson();

                WorldWrapper wrapper = gson.fromJson(new String(inputStream.readAllBytes()), WorldWrapper.class);

                displayname = wrapper.displayname;

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

    public String getDisplayName() {
        if (displayname == null)
            return world.getName();
        return displayname;
    }

    public boolean isDisplayNameSet() {
        return displayname != null;
    }

    public void setDisplayName(String displayname) {
        this.displayname = displayname;
        save();
    }

}
