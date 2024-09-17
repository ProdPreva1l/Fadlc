package info.preva1l.fadlc.models.user;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class ConsoleUser implements CommandUser {
    @NotNull private final Audience audience;

    @Override
    @NotNull
    public Audience getAudience() {
        return audience;
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return true;
    }

    @Override
    public Player asPlayer() {
        return null;
    }
}