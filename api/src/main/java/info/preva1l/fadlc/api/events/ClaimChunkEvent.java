package info.preva1l.fadlc.api.events;

import info.preva1l.fadlc.models.IClaimChunk;
import info.preva1l.fadlc.models.claim.IClaim;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public abstract class ClaimChunkEvent extends ClaimEvent {
    protected final IClaimChunk chunk;

    public ClaimChunkEvent(Player who, IClaim claim, IClaimChunk chunk) {
        super(who, claim);
        this.chunk = chunk;
    }
}
