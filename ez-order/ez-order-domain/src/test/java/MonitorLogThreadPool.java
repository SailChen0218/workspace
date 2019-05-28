
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class MonitorLogThreadPool {
    private static Logger log = LoggerFactory.getLogger(MonitorLogThreadPool.class);

    /**
     * 日志缓存队列
     */
    private static final int BUFFER_SIZE = 1024 * 8 * 4;
    private MonitorLogBuffer monitorLogBuffer = new MonitorLogBuffer(BUFFER_SIZE);

    private class MonitorMaster {
        /**
         * 拉取日志定时任务
         */
        private ScheduledExecutorService masterScheduleService = new ScheduledThreadPoolExecutor(1,
                new NamedThreadFactory("MonitorMaster-thread"));
        private static final int INTERVAL_MILLIS = 100;

        /**
         * 每次从buffer拉取的日志件数
         */
        private static final int FETCH_LENGTH = 100;

        /**
         * 工作者
         */
        private MonitorWorker[] monitorWorkers;
        private int workerCount;

        /**
         * 轮询工作者时用的索引
         */
        private int index = 0;

        /**
         * 工作者线程CountDown
         */
        private CountDownLatch latch;

        public MonitorMaster(int workerCount) {
            latch = new CountDownLatch(workerCount);
            this.startWorkers(workerCount);
            try {
                latch.await();
                this.startMaster();
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }

        private void startMaster() {
            List<MonitorLogDto> logs = monitorLogBuffer.get(FETCH_LENGTH);
            masterScheduleService.scheduleAtFixedRate(() -> {
                this.assignWork(new Work(logs));
            }, 5, INTERVAL_MILLIS, TimeUnit.MILLISECONDS);
        }

        private void startWorkers(int workerCount) {
            if (workerCount <= 0) {
                throw new IllegalArgumentException("workerCount must be greater than 0.");
            }
            monitorWorkers = new MonitorWorker[workerCount];
            for (int i = 0; i < workerCount; i++) {
                monitorWorkers[i] = new MonitorWorker(i, "MonitorWorker-thread" + i, 100);
                monitorWorkers[i].start(latch);
            }
        }

        private void assignWork(Work work) {
            long time = System.currentTimeMillis();
            index = (index + 1) % workerCount;
            monitorWorkers[index].addWork(work);
        }

    }

    /**
     * 日志写入工作者
     */
    private class MonitorWorker {
        private WorkBuffer workBuffer;
        private ScheduledExecutorService workerScheduleService;
        private int workerId;
        private int intervalMillis = 500;

        public MonitorWorker(int workerId, String threadName, Integer bufferSize) {
            workerScheduleService = new ScheduledThreadPoolExecutor(1,
                    new NamedThreadFactory(threadName));
            workBuffer = new WorkBuffer(bufferSize);
            this.workerId = workerId;
        }

        public void start(CountDownLatch latch) {
            workerScheduleService.scheduleAtFixedRate(() -> {
                this.doWork();
            }, 2, intervalMillis, TimeUnit.MILLISECONDS);
            latch.countDown();
        }

        public void addWork(Work work) {
            workBuffer.put(work);
        }

        private void doWork() {
//            MonitorWriter monitorWriter =
//                    BondeInterfaceFactory.getInstance().getInterface(MonitorWriter.class);

            Work<List<MonitorLogDto>> work = workBuffer.get();
            if (work == null) {
                return;
            }

            List<MonitorLogDto> logs = work.getWorkContent();
            for (int i = 0; i < logs.size(); i++) {
                if (logs.get(i) == null) {
                    break;
                }
//                monitorWriter.trace(logs.get(i));
            }
        }
    }

    /**
     * 任务内容
     *
     * @param <T>
     */
    private class Work<T> {
        private T workContent;

        public Work(T workContent) {
            this.workContent = workContent;
        }

        public T getWorkContent() {
            return this.workContent;
        }
    }

    /**
     * 任务缓存
     */
    private class WorkBuffer {
        /**
         * 每个MonitorLogDto对象占用3149 B内存。
         */
        private Work[] buffer;
        private int head = 0;
        private int tail = 0;
        private int bufferSize;

        public WorkBuffer(int initSize) {
            this.bufferSize = initSize;
            this.buffer = new Work[bufferSize];
        }

        private Boolean isEmpty() {
            return head == tail;
        }

        public void clear() {
            Arrays.fill(buffer, null);
            head = 0;
            tail = 0;
        }

        public Boolean put(Work v) {
            buffer[tail] = v;
            if (tail >= bufferSize) {
                tail = (tail + 1) % bufferSize;
            } else {
                tail++;
            }
            return true;
        }

        public Work get() {
            if (isEmpty()) {
                return null;
            }
            Work work = buffer[head];
            if (head >= bufferSize) {
                head = (head + 1) % bufferSize;
            } else {
                head++;
            }
            return work;
        }
    }
}
