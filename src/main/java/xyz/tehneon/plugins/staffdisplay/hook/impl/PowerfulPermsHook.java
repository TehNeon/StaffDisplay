package xyz.tehneon.plugins.staffdisplay.hook.impl;

import com.github.cheesesoftware.PowerfulPermsAPI.Group;
import com.github.cheesesoftware.PowerfulPermsAPI.PowerfulPermsPlugin;
import org.bukkit.Bukkit;
import xyz.tehneon.plugins.staffdisplay.StaffDisplay;
import xyz.tehneon.plugins.staffdisplay.hook.PluginHook;

/**
 * @author TehNeon
 * @since 1/21/2017
 */
public class PowerfulPermsHook implements PluginHook {

    private StaffDisplay plugin;
    private PowerfulPermsPlugin powerfulPermsPlugin;

    public PowerfulPermsHook(StaffDisplay plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        powerfulPermsPlugin = (PowerfulPermsPlugin) Bukkit.getPluginManager().getPlugin("PowerfulPerms");
    }

    @Override
    public void updatePlayers() {
        // Loop through all the ranks inside the config to grab
        for (String rankName : plugin.getConfig().getStringList("ranks")) {
            Group group = powerfulPermsPlugin.getPermissionManager().getGroup(rankName);
            if(group != null) {

            } else {
                plugin.getLogger().warning("The permission group/rank \"" + rankName + "\" does not seem to exist.");
            }
        }
    }

    @Override
    public String getPluginName() {
        return "PowerfulPerms";
    }
}
