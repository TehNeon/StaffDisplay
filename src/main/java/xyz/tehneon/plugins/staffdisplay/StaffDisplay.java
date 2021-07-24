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
import xyz.tehneon.plugins.staffdisplay.hook.impl.PowerRanksHook;
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
        this.saveDefaultConfig();

        if (!this.registerCommands() || !this.registerHook()) {
            this.getLogger().warning("Disabling plugin due to the issue above");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.menuBuilder = new MenuBuilder(this);
        this.registerListeners();

        this.updateTask = new BukkitRunnable() {
            @Override
            public void run() {
                StaffDisplay.this.getMenuBuilder().updateMenu();
            }
        };

        this.updateTask.runTaskLater(this, getConfig().getLong("staff-updater.delay") * 20);
    }

    @Override
    public void onDisable() {

    }

    private boolean registerCommands() {
        // Thanks to ItsSteve for the general concept of using the commandMap to register commands without using the plugin.yml
        // Source: https://www.spigotmc.org/threads/small-easy-register-command-without-plugin-yml.38036/
        if (this.getServer().getPluginManager() instanceof SimplePluginManager) {
            CommandMap commandMap = null;

            try {
                Field commandMapField = SimplePluginManager.class.getDeclaredField("commandMap");
                commandMapField.setAccessible(true);
                commandMap = (CommandMap) commandMapField.get(this.getServer().getPluginManager());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (commandMap != null) {
                commandMap.register(getConfig().getString("command.label"), new StaffDisplayCommand(this));
            } else {
                this.getServer().getPluginManager().disablePlugin(this);
                this.getLogger().warning("Your server software's PluginManager does not contain a commandMap so I cannot register a command. This may be due to the fact you might be running a custom Bukkit/Spigot version.");
                return false;
            }
        } else {
            this.getLogger().warning("Your server software is running a PluginManager that is unrecognized. This may be due to the fact you might be running a custom Bukkit/Spigot version.");
            return false;
        }

        return true;
    }

    private boolean registerHook() {
        // Register everything after the command just in case the command cannot register it will disable the plugin
        List<PluginHook> hooks = Arrays.asList(new PermissionsExHook(this), new VaultHook(this));
        // Cache the config data so we're not consistently grabbing it inside the loop
        boolean automated = this.getConfig().getBoolean("hook.automated");
        String manualHook = this.getConfig().getString("hook.manual");
        Plugin targetPlugin = null;
        for (PluginHook hook : hooks) {
            if (automated) {
                targetPlugin = this.getServer().getPluginManager().getPlugin(hook.getPluginName());
                if (targetPlugin != null) {
                    permissionsHook = hook;
                    break;
                }
            } else {
                if (hook.getPluginName().equalsIgnoreCase(manualHook)) {
                    permissionsHook = hook;
                    targetPlugin = this.getServer().getPluginManager().getPlugin(hook.getPluginName());
                    break;
                }
            }
        }

        if (permissionsHook == null || targetPlugin == null) {
            if (automated) {
                this.getLogger().warning("We could not automatically find a permissions plugin that we are capable of hooking");
            } else {
                this.getLogger().warning("We could not find manual hook target \"" + manualHook + "\" are you sure you're specifying the correct manual hook?");
            }

            return false;
        }

        this.getLogger().info("Using permissions hook for: " + this.permissionsHook.getPluginName());
        new BukkitRunnable() {
            public void run() {
                permissionsHook.init();
                getLogger().info("Hook initialized");
            }
        }.runTaskLater(this, 1L);

        return true;
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new MenuListener(this), this);
    }
}
