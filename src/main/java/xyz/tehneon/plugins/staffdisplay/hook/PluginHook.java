package xyz.tehneon.plugins.staffdisplay.hook;

/**
 * @author TehNeon
 * @since 1/21/2017
 */
public interface PluginHook {

    void init();

    void updatePlayers();

    String getPluginName();
}
