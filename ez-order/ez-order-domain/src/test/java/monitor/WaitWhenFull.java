package monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class WaitWhenFull implements WaitStrategy {
    private static Logger log = LoggerFactory.getLogger(WaitWhenFull.class);

    private long sleepTime;
    private int counts;

    public WaitWhenFull(int counts, long sleepTime) {
        if (counts < 0 || sleepTime < 0) {
            throw new IllegalArgumentException("WaitByCounts init error.counts and sleepTime must grather than 0.");
        }
        this.counts = counts;
        this.sleepTime = sleepTime;
    }

    @Override
    public boolean waitFor(WorkBuffer workBuffer) {
        int count = 0;
        while (workBuffer.isFull()) {
            try {
                if (count < this.counts) {
                    count++;
                    log.error("WaitWhenFull: " + count);
                    TimeUnit.MILLISECONDS.sleep(sleepTime);
                } else {
                    log.error("WaitWhenFull failed.");
                    return false;
                }
            } catch (InterruptedException e) {
                return false;
            }
        }
        return true;
    }
}
