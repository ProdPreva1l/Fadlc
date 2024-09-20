package info.preva1l.fadlc.models.user;

import info.preva1l.fadlc.models.MessageLocation;
import info.preva1l.fadlc.models.claim.IClaim;
import info.preva1l.fadlc.models.claim.IProfileGroup;

import java.util.Map;

public interface OnlineUser extends User {
    int getAvailableChunks();
    boolean isViewBorders();
    boolean isShowEnterMessages();
    boolean isShowLeaveMessages();
    MessageLocation getMessageLocation();

    IClaim getClaim();
    Map<IClaim, IProfileGroup> getTrustedClaims();
}
