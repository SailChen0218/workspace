package monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class WaitWhenMaxTail implements WaitStrategy {
    private static Logger log = LoggerFactory.getLogger(WaitWhenMaxTail.class);

    private long sleepTime;
    private int counts;
    private int maxTail;
    public WaitWhenMaxTail(int counts, long sleepTime, int maxTail) {
        if (counts < 0 || sleepTime < 0 || maxTail < 0) {
            throw new IllegalArgumentException("WaitByCounts init error.counts and sleepTime must grather than 0.");
        }
        this.counts = counts;
        this.sleepTime = sleepTime;
        this.maxTail = maxTail;
    }

    @Override
    public boolean waitFor(WorkBuffer workBuffer) {
        int tail = workBuffer.getTail();
        if (tail == maxTail) {
            int count = 0;
            while (workBuffer.getHead() < workBuffer.getTail()) {
                try {
                    if (count < counts) {
                        count++;
                        log.error("max tail. wait for clear.");
                        TimeUnit.MILLISECONDS.sleep(sleepTime);
                    } else {
                        return false;
                    }
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                    return false;
                }
            }
            workBuffer.clear();
        }
        return true;
    }
}
