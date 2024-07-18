package info.preva1l.fadsb;

import info.preva1l.fadsb.island.Environment;
import info.preva1l.fadsb.user.BukkitUser;
import lombok.experimental.UtilityClass;
import org.bukkit.World;
import org.bukkit.entity.Player;

@UtilityClass
public class BukkitAdapter {
    public World.Environment adaptEnvironment(Environment environment) {
        return switch (environment) {
            case OVERWORLD -> World.Environment.NORMAL;
            case NETHER -> World.Environment.NETHER;
            case END -> World.Environment.THE_END;
        };
    }

    public BukkitUser adaptPlayer(Player player) {
        return new BukkitUser(player);
    }
}
