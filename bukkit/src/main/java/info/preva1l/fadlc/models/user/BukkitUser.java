package info.preva1l.fadlc.models.user;

import info.preva1l.fadlc.Fadlc;
import info.preva1l.fadlc.managers.ClaimManager;
import info.preva1l.fadlc.models.claim.IClaim;
import info.preva1l.fadlc.models.claim.IProfileGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class BukkitUser implements OnlineUser, CommandUser {
    private final String name;
    private final UUID uniqueId;
    private final Player player;
    private final int availableChunks;

    @Override
    public @NotNull Audience getAudience() {
        return Fadlc.i().getAudiences().player(player);
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public Player asPlayer() {
        return player;
    }

    @Override
    public IClaim getClaim() {
        return ClaimManager.getInstance().getClaimByOwner(uniqueId);
    }

    @Override
    public Map<IClaim, IProfileGroup> getTrustedClaims() {
        return Map.of();
    }
}
