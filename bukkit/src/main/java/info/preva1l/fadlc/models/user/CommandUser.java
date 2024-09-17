package info.preva1l.fadlc.models.user;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface CommandUser {
    @NotNull
    Audience getAudience();

    boolean hasPermission(@NotNull String permission);

    default void sendMessage(@NotNull String message) {
        getAudience().sendMessage(MiniMessage.miniMessage().deserialize(message));
    }

    default void sendMessage(@NotNull Component component) {
        getAudience().sendMessage(component);
    }

    Player asPlayer();
}