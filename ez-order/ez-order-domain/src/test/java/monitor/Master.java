package monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * master主线程，用于分配work
 */
public class Master<T> {
    private static Logger log = LoggerFactory.getLogger(Master.class);

    /**
     * 日志缓存队列
     */
    private final int masterBufferSize;
    private final WaitStrategy waitWhenFull;
    private final WaitStrategy waitWhenMaxTail;
    private final WorkBuffer<T> masterWorkBuffer;

    /**
     * 拉取日志定时任务
     */
    private final ScheduledExecutorService masterScheduleService = new ScheduledThreadPoolExecutor(1,
            new NamedThreadFactory("MonitorMaster"));
    private static final int INTERVAL_MILLIS = 100;

    /**
     * 每次从buffer拉取的日志件数
     */
    private static final int FETCH_LENGTH = 100;

    /**
     * 工作者
     */
    private final Worker<List<T>>[] workers;
    private int workerCount;
    private int workerBufferSize;

    private final WorkHandler<T> workHandler;

    /**
     * 轮询工作者时用的索引
     */
    private int index = 0;

    /**
     * 启动标识
     */
    private AtomicBoolean isStarted = new AtomicBoolean(false);

    public Master(int masterBufferSize, int workerCount, int workerBufferSize, WorkHandler workHandler) {
        if (masterBufferSize <= 0 || workerCount <= 0 || workerBufferSize <= 0) {
            throw new IllegalArgumentException("workerCount,masterBufferSize,workerBufferSize must be greater than 0.");
        }

        this.masterBufferSize = masterBufferSize;
        this.workerCount = workerCount;
        this.workerBufferSize = workerBufferSize;

        this.waitWhenFull = new WaitWhenFull(2, 1000);
        this.waitWhenMaxTail = new WaitWhenMaxTail(2, 1000, Integer.MAX_VALUE);
        this.masterWorkBuffer = new WorkBuffer(masterBufferSize, waitWhenFull, waitWhenMaxTail);

        this.workHandler = workHandler;
        workers = new Worker[workerCount];
        for (int i = 0; i < workerCount; i++) {
            workers[i] = new Worker(i, "MonitorWorker" + i, workerBufferSize, workHandler);
        }
    }

    public void start() {
        if (isStarted.compareAndSet(false, true)) {
            // start workers
            for (int i = 0; i < workerCount; i++) {
                workers[i].start();
            }
            // start master
            masterScheduleService.scheduleAtFixedRate(() -> {
                this.assignWork();
            }, 5, INTERVAL_MILLIS, TimeUnit.MILLISECONDS);
            log.error("Master started.");
        }
    }

    public boolean isStarted() {
        return isStarted.get();
    }

    private void assignWork() {
        try {
            List<Work<T>> works = masterWorkBuffer.get(FETCH_LENGTH);
            if (works != null) {
                List<T> workContentList = new ArrayList<>(works.size());
                for (int i = 0; i < works.size(); i++) {
                    workContentList.add(works.get(i).getWorkContent());
                }
                Work<List<T>> work = new Work(workContentList);
                index = (index + 1) % workerCount;
                workers[index].addWork(work);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public void assign(Work<T> work) {
        masterWorkBuffer.put(work);
    }

    public static void main(String[] args) {
        int masterBufferSize = 1024 * 8;
        int workerCount = 4;
        int workerBufferSize = 1024;
        WorkHandler<List<MonitorLogDto>> workHandler = new MonitorLogHandler();
        Master<MonitorLogDto> master = new Master(masterBufferSize, workerCount, workerBufferSize, workHandler);
        master.start();

        for (int i = 0; i < 1000; i++) {
            MonitorLogDto monitorLogDto = new MonitorLogDto();
            monitorLogDto.setChannelId(String.valueOf(i));
            master.assign(new Work(monitorLogDto));
        }

        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
