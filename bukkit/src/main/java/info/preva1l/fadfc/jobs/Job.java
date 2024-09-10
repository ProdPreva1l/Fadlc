package info.preva1l.fadfc.jobs;

import info.preva1l.fadfc.config.Config;
import info.preva1l.fadfc.utils.Logger;
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
        scheduler.scheduleAtFixedRate(this::run, 0L,
                interval.get(ChronoUnit.MILLIS), TimeUnit.of(ChronoUnit.MILLIS));
        Logger.info("[JOBS] Job '" + name + "' started");
    }

    private void run() {
        int errors = 0;
        Logger.info("[JOBS] Running job '" + name);
        try {
            execute();
        } catch (Exception e) {
            e.printStackTrace();
            errors++;
        }
        Logger.info("[JOBS] Job '" + name + "' completed (%s errors)".formatted(errors));
    }

    protected abstract void execute();
}
