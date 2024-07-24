package info.preva1l.fadsb.user;

import info.preva1l.fadsb.Fadsb;
import info.preva1l.fadsb.utils.Text;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConsoleUser implements CommandUser {
    private final Audience audience;
    private final Fadsb plugin;

    @NotNull
    public static ConsoleUser wrap(@NotNull Audience audience, Fadsb plugin) {
        return new ConsoleUser(audience, plugin);
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return true;
    }

    @Override
    public void sendMessage(@NotNull String message) {
        audience.sendMessage(Text.modernMessage("prefix" + message));
    }

    @Override
    public void sendMessage(@NotNull List<String> message) {
        audience.sendMessage(Text.modernMessage(String.join("\n", message)));
    }

    @Override
    public void sendRawMessage(@NotNull String message) {
        audience.sendMessage(Text.modernMessage(message));
    }
}