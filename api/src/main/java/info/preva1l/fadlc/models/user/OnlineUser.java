package info.preva1l.fadlc.models.user;

import info.preva1l.fadlc.models.MessageLocation;
import info.preva1l.fadlc.models.claim.IClaim;
import info.preva1l.fadlc.models.claim.IClaimProfile;

public interface OnlineUser extends User {
    int getAvailableChunks();
    boolean isViewBorders();
    boolean isShowEnterMessages();
    boolean isShowLeaveMessages();
    MessageLocation getMessageLocation();
    IClaimProfile getClaimWithProfile();

    IClaim getClaim();
}
