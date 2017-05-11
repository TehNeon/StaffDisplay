package xyz.tehneon.plugins.staffdisplay;

import lombok.Getter;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.tehneon.plugins.staffdisplay.builder.MenuBuilder;
import xyz.tehneon.plugins.staffdisplay.command.StaffDisplayCommand;
import xyz.tehneon.plugins.staffdisplay.hook.PluginHook;
import xyz.tehneon.plugins.staffdisplay.hook.impl.PermissionsExHook;
import xyz.tehneon.plugins.staffdisplay.hook.impl.VaultHook;
import xyz.tehneon.plugins.staffdisplay.listener.MenuListener;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author TehNeon
 * @since 1/21/2017
 * <p>
 * The main class which holds instances to anything important.
 */

public final class StaffDisplay extends JavaPlugin {

    @Getter
    private MenuBuilder menuBuilder;

    @Getter
    private PluginHook permissionsHook;

    private BukkitRunnable updateTask;

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
                getLogger().warning("Your server software's PluginManager does not contain a commandMap so I cannot register a command. This may be due to the fact you might be running a custom Bukkit/Spigot version.");
            }
        } else {
            getServer().getPluginManager().disablePlugin(this);
            getLogger().warning("Your server software is running a PluginManager that is unrecognized. This may be due to the fact you might be running a custom Bukkit/Spigot version.");
        }

        // Register everything after the command just in case the command cannot register it will disable the plugin
        List<PluginHook> hooks = Arrays.asList(new PermissionsExHook(this), new VaultHook(this));
        // Cache the config data so we're not consistently grabbing it inside the loop
        boolean automated = getConfig().getBoolean("hook.automated");
        String manualHook = getConfig().getString("hook.manual");
        Plugin targetPlugin = null;
        for (PluginHook hook : hooks) {
            if (automated) {
                targetPlugin = getServer().getPluginManager().getPlugin(hook.getPluginName());
                if (targetPlugin != null) {
                    permissionsHook = hook;
                    break;
                }
            } else {
                if (hook.getPluginName().equalsIgnoreCase(manualHook)) {
                    permissionsHook = hook;
                    targetPlugin = getServer().getPluginManager().getPlugin(hook.getPluginName());
                    break;
                }
            }
        }

        if (permissionsHook == null || targetPlugin == null) {
            getServer().getPluginManager().disablePlugin(this);
            getLogger().warning("The plugin could not start as there were no permission based plugins found.");
            return;
        }

        getLogger().info("Using permissions hook for: " + permissionsHook.getPluginName());
        new BukkitRunnable() {
            public void run() {
                permissionsHook.init();
                getLogger().info("Hook initialized");
            }
        }.runTaskLater(this, 1L);

        menuBuilder = new MenuBuilder(this);
        getServer().getPluginManager().registerEvents(new MenuListener(this), this);

        updateTask = new BukkitRunnable() {
            @Override
            public void run() {
                getMenuBuilder().updateMenu();
            }
        };

        updateTask.runTaskLater(this, getConfig().getLong("staff-updater.delay") * 20);
    }

    @Override
    public void onDisable() {

    }
}
