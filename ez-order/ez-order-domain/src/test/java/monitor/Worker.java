package monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 工作者线程，用于执行work。 T: workContent
 */
public class Worker<T> implements Runnable {
    private static Logger log = LoggerFactory.getLogger(Worker.class);

    private final WorkBuffer<T> workerBuffer;
    private final ScheduledExecutorService workerScheduleService;
    private final int workerId;
    private final WaitStrategy waitWhenFull;
    private final WaitStrategy waitWhenMaxTail;
    private final WorkHandler<T> workHandler;
    private static final int intervalMillis = 500;

    public Worker(int workerId, String threadName, int workerBufferSize, WorkHandler workHandler) {
        workerScheduleService = new ScheduledThreadPoolExecutor(1,
                new NamedThreadFactory(threadName));
        this.waitWhenFull = new WaitWhenFull(2, 1000);
        this.waitWhenMaxTail = new WaitWhenMaxTail(2, 1000, Integer.MAX_VALUE);
        this.workerBuffer = new WorkBuffer(workerBufferSize, waitWhenFull, waitWhenMaxTail);
        this.workerId = workerId;
        this.workHandler = workHandler;
    }

    public void start() {
        log.error("Worker" + workerId + " started.");
        workerScheduleService.scheduleAtFixedRate(() -> {
            this.doWork();
        }, 2, intervalMillis, TimeUnit.MILLISECONDS);
    }

    public void addWork(Work<T> work) {
        this.workerBuffer.put(work);
    }

    private void doWork() {
        try {
            Work<T> work = this.workerBuffer.get();
            if (work != null) {
                workHandler.doWork(work);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            doWork();
            try {
                TimeUnit.MILLISECONDS.sleep(intervalMillis);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
