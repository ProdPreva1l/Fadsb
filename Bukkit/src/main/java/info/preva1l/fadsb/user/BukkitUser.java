package info.preva1l.fadsb.user;

import info.preva1l.fadsb.utils.Text;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public final class BukkitUser extends OnlineUser {
    private final Player player;

    public BukkitUser(@NotNull Player player) {
        super(player.getName(), player.getUniqueId());
        this.player = player;
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public void sendMessage(@NotNull String message) {
        player.sendMessage("prefix" + message);
    }

    @Override
    public void sendMessage(@NotNull List<String> message) {
        player.sendMessage(String.join("\n", message));
    }

    @Override
    public void sendRawMessage(@NotNull String message) {
        player.sendMessage(message);
    }

    @Override
    public void sendActionBar(@NotNull String message) {
        player.sendActionBar(message);
    }

    @Override
    public void sendTitle(@NotNull String titleText, @Nullable String subTitle, long fadeIn, long stay, long fadeOut) {
        if (subTitle == null) {
            subTitle = "";
        }
        Title.Times times = Title.Times.times(
                Duration.of(fadeIn, ChronoUnit.MILLIS),
                Duration.of(stay, ChronoUnit.MILLIS),
                Duration.of(fadeOut, ChronoUnit.MILLIS));
        Title title = Title.title(Text.modernMessage(titleText), Text.modernMessage(subTitle), times);
        player.showTitle(title);
    }

    @Override
    public void sendTitle(@NotNull String titleText, @Nullable String subTitle) {
        if (subTitle == null) {
            subTitle = "";
        }
        Title title = Title.title(Text.modernMessage(titleText), Text.modernMessage(subTitle));
        player.showTitle(title);
    }


}
