package xyz.tehneon.plugins.staffdisplay.hook.impl;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import xyz.tehneon.plugins.staffdisplay.StaffDisplay;
import xyz.tehneon.plugins.staffdisplay.builder.TargetUser;
import xyz.tehneon.plugins.staffdisplay.hook.PluginHook;

/**
 * @author TehNeon
 * @since 1/21/2017
 */
public class PermissionsExHook implements PluginHook {

    private StaffDisplay plugin;

    public PermissionsExHook(StaffDisplay plugin) {
        this.plugin = plugin;
    }

    @Override
    public void updatePlayers() {
        // Loop through all the ranks inside the config to grab
        for (String rankName : plugin.getConfig().getStringList("ranks")) {
            PermissionGroup permissionGroup = PermissionsEx.getPermissionManager().getGroup(rankName);
            if (permissionGroup != null) {
                for (PermissionUser permissionUser : permissionGroup.getUsers()) {
                    plugin.getMenuBuilder().getTargetUserList().add(new TargetUser(permissionUser.getName(), rankName));
                }
            } else {
                plugin.getLogger().warning("The permission group/rank \"" + rankName + "\" does not seem to exist.");
            }
        }
    }

    @Override
    public String getPluginName() {
        return "PermissionsEx";
    }
}
