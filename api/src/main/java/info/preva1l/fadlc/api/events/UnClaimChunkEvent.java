package info.preva1l.fadlc.api.events;

import info.preva1l.fadlc.models.IClaimChunk;
import info.preva1l.fadlc.models.claim.IClaim;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class UnClaimChunkEvent extends ClaimChunkEvent {
    @Getter private static final HandlerList handlerList = new HandlerList();

    public UnClaimChunkEvent(Player who, IClaim claim, IClaimChunk chunk) {
        super(who, claim, chunk);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
