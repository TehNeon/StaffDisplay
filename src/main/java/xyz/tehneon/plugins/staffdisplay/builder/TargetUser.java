package xyz.tehneon.plugins.staffdisplay.builder;

import lombok.Data;

/**
 * @author TehNeon
 * @since 1/21/2017
 * <p>
 * Class which holds all information related to users who should be inside the Staff Menu.
 */
@Data
public class TargetUser {
    private final String username;
    private final String rankName;
}
