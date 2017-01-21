package xyz.tehneon.plugins.staffdisplay.hook.impl;

import xyz.tehneon.plugins.staffdisplay.StaffDisplay;
import xyz.tehneon.plugins.staffdisplay.hook.PluginHook;

/**
 * @author TehNeon
 * @since 1/21/2017
 */
public class zPermissionsHook implements PluginHook {

    private StaffDisplay plugin;

    public zPermissionsHook(StaffDisplay plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {

    }

    @Override
    public void updatePlayers() {

    }

    @Override
    public String getPluginName() {
        return "zPermissions [NOT FINISHED]";
    }
}
