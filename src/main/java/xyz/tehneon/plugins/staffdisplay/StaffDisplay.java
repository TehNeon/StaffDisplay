package xyz.tehneon.plugins.staffdisplay;

import org.bukkit.command.CommandMap;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.tehneon.plugins.staffdisplay.command.StaffDisplayCommand;

import java.lang.reflect.Field;

/**
 * @author TehNeon
 * @since 1/21/2017
 * <p>
 * The main class which holds instances to anything important.
 */
public final class StaffDisplay extends JavaPlugin {

    private MenuBuilder menuBuilder;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // Thanks to ItsSteve for the general concept of using the commandMap to register commands without using the plugin.yml
        // Source: https://www.spigotmc.org/threads/small-easy-register-command-without-plugin-yml.38036/
        if (getServer().getPluginManager() instanceof SimplePluginManager) {
            CommandMap commandMap = null;

            try {
                Field commandMapField = SimplePluginManager.class.getDeclaredField("commandMap");
                commandMapField.setAccessible(true);
                commandMap = (CommandMap) commandMapField.get(getServer().getPluginManager());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (commandMap != null) {
                commandMap.register(getConfig().getString("command.label"), new StaffDisplayCommand(this));
            } else {
                getServer().getPluginManager().disablePlugin(this);
                new RuntimeException("Your server software's PluginManager does not contain a commandMap so I cannot register a command. This may be due to the fact you might be running a custom Bukkit/Spigot version.");
            }
        } else {
            getServer().getPluginManager().disablePlugin(this);
            new RuntimeException("Your server software is running a PluginManager that is unrecognized. This may be due to the fact you might be running a custom Bukkit/Spigot version.");
        }

        // Register everything after the command incase the command cannot register it will disable the plugin
        menuBuilder = new MenuBuilder(this);

        getServer().getPluginManager().registerEvents(new MenuListener(this), this);
    }

    @Override
    public void onDisable() {

    }

    public MenuBuilder getMenuBuilder() {
        return menuBuilder;
    }
}
