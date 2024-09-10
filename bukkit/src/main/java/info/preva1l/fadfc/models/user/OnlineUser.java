package info.preva1l.fadfc.models.user;

import info.preva1l.fadfc.Fadlc;
import info.preva1l.fadfc.managers.ClaimManager;
import info.preva1l.fadfc.models.claim.IClaim;
import info.preva1l.fadfc.models.claim.IProfileGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class OnlineUser implements User, CommandUser {
    private final String name;
    private final UUID uniqueId;
    private final Player player;
    private final IClaim claim;

    public static OnlineUser fromDb(User user, Player player) {
        return new OnlineUser(user.getName(), user.getUniqueId(), player, user.getClaim());
    }

    @Override
    public @NotNull Audience getAudience() {
        return Fadlc.i().getAudiences().player(player);
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public int getAvailableChunks() {
        return 23;
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
