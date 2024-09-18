package info.preva1l.fadlc.jobs;

import info.preva1l.fadlc.config.Config;
import info.preva1l.fadlc.utils.Logger;
import lombok.AllArgsConstructor;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Represents a job that gets run on a thread.
 */
@AllArgsConstructor
public abstract class Job {
    private final String name;
    private final Duration interval;
    private static final ScheduledExecutorService scheduler =
            new ScheduledThreadPoolExecutor(Config.getInstance().getJobs().getPoolSize());

    public final void start() {
        scheduler.scheduleAtFixedRate(this::run, interval.get(ChronoUnit.SECONDS),
                interval.get(ChronoUnit.SECONDS), TimeUnit.SECONDS);
        Logger.info("[JOBS] Job '%s' scheduled at an interval of %s seconds".formatted(this.name, interval.get(ChronoUnit.SECONDS)));
    }

    void run() {
        int errors = 0;
        Logger.info("[JOBS] Running job '%s'".formatted(name));
        try {
            execute();
        } catch (Exception e) {
            e.printStackTrace();
            errors++;
        }
        Logger.info("[JOBS] Job '%s' completed (%s errors)".formatted(this.name, errors));
    }

    void shutdown() {
        scheduler.shutdownNow();
        Logger.info("[JOBS] Job '%s' shutdown!".formatted(this.name));
    }

    protected abstract void execute();
}
