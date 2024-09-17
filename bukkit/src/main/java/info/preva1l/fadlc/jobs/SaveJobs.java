package info.preva1l.fadlc.jobs;

import info.preva1l.fadlc.config.Config;
import info.preva1l.fadlc.managers.ClaimManager;
import info.preva1l.fadlc.managers.PersistenceManager;
import info.preva1l.fadlc.managers.UserManager;
import info.preva1l.fadlc.models.claim.IClaim;
import info.preva1l.fadlc.models.user.User;

import java.time.Duration;

public class SaveJobs {
    public static void startAll() {
        new ClaimSaveJob().start();
        new UsersSaveJob().start();
    }

    public static class ClaimSaveJob extends Job {
        public ClaimSaveJob() {
            super("Claim Save", Duration.ofMinutes(Config.getInstance().getJobs().getClaimSaveInterval()));
        }

        @Override
        protected void execute() {
            ClaimManager.getInstance().getAllClaims().forEach(f ->
                    PersistenceManager.getInstance().save(IClaim.class, f));
        }
    }

    public static class UsersSaveJob extends Job {
        public UsersSaveJob() {
            super("Users Save", Duration.ofMinutes(Config.getInstance().getJobs().getUsersSaveInterval()));
        }

        @Override
        protected void execute() {
            UserManager.getInstance().getAllUsers().forEach(f ->
                    PersistenceManager.getInstance().save(User.class, f));
        }
    }
}
