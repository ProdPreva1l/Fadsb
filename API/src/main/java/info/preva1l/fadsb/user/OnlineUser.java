package info.preva1l.fadsb.user;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Getter
public abstract class OnlineUser extends User implements CommandUser {

    protected OnlineUser(@NotNull String username, @NotNull UUID uniqueId) {
        super(username, uniqueId);
    }

    public abstract void sendMessage(@NotNull String message);

    public abstract void sendActionBar(@NotNull String message);

    public abstract void sendTitle(@NotNull String titleText, @Nullable String subTitle, long fadeIn, long stay, long fadeOut);

    public abstract void sendTitle(@NotNull String titleText, @Nullable String subTitle);
}