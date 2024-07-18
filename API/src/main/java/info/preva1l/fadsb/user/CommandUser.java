package info.preva1l.fadsb.user;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface CommandUser {
    boolean hasPermission(@NotNull String permission);

    void sendMessage(@NotNull String message);

    void sendMessage(@NotNull List<String> message);

    void sendRawMessage(@NotNull String message);
}
