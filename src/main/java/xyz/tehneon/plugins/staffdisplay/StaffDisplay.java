package xyz.tehneon.plugins.staffdisplay;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author TehNeon
 * @since 1/21/2017
 */
public final class StaffDisplay extends JavaPlugin {

    private MenuBuilder menuBuilder;

    @Override
    public void onEnable() {
        menuBuilder = new MenuBuilder(this);

        getServer().getPluginManager().registerEvents(new MenuListener(this), this);
    }

    @Override
    public void onDisable() {

    }
}
