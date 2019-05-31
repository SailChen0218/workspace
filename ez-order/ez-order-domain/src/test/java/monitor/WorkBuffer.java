package monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 任务缓存
 */
public class WorkBuffer<T> {
    private static Logger log = LoggerFactory.getLogger(WorkBuffer.class);

    /**
     * 每个MonitorLogDto对象占用3149 B内存。
     */
    private final Work[] buffer;
    private AtomicInteger tail;
    private AtomicInteger head;
    private final int bufferSize;
    private final int indexMask;
    private final WaitStrategy waitWhenFull;
    private final WaitStrategy waitWhenMaxTail;
    private static final int MAX_TAIL = Integer.MAX_VALUE;

    public WorkBuffer(int initSize, WaitStrategy waitWhenFull, WaitStrategy waitWhenMaxTail) {
        this.bufferSize = initSize;
        this.buffer = new Work[bufferSize];
        this.indexMask = bufferSize - 1;
        this.waitWhenFull = waitWhenFull;
        this.waitWhenMaxTail = waitWhenMaxTail;

        tail = new AtomicInteger(0);
        head = new AtomicInteger(0);
    }

    public Boolean isEmpty() {
        return head.get() == tail.get();
    }

    public Boolean isFull() {
        int headValue = head.get();
        if (headValue == 0) {
            return false;
        }

        int tailValue = tail.get();
        if ((tailValue / bufferSize) > (headValue / bufferSize)) {
            return ((tailValue + 1) & indexMask) >= (headValue & indexMask);
        } else {
            return false;
        }
    }

    public void clear() {
        Arrays.fill(buffer, null);
        tail.set(0);
        head.set(0);
    }

    public Boolean put(Work<T> v) {
        if (!waitWhenMaxTail.waitFor(this)) {
            return false;
        }

        if (!waitWhenFull.waitFor(this)) {
            return false;
        }

        int index = tail.getAndIncrement() & indexMask;
        buffer[index] = v;
        return true;
    }

    public Work<T> get() {
        if (isEmpty()) {
            return null;
        }
        int index = head.getAndIncrement() & indexMask;
        Work<T> work = buffer[index];
        return work;
    }

    public List<Work<T>> get(int length) {
        if (isEmpty()) {
            return null;
        }

        if (length <= 0) {
            throw new IllegalArgumentException("length must greater than 0");
        }

        ArrayList<Work<T>> workList = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            Work work = this.get();
            if (work != null) {
                workList.add(work);
            }
        }
        workList.trimToSize();
        return workList;
    }

    public int getTail() {
        return tail.get();
    }

    public int getHead() {
        return head.get();
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public static void main(String[] args) {
        WaitStrategy waitWhenFull = new WaitWhenFull(2, 100);
        WaitStrategy waitWhenMaxTail = new WaitWhenMaxTail(2, 100, 100);
        final WorkBuffer workBuffer = new WorkBuffer(32, waitWhenFull, waitWhenMaxTail);

        ScheduledExecutorService masterScheduleService = new ScheduledThreadPoolExecutor(1,
                new NamedThreadFactory("MonitorMaster"));
        masterScheduleService.scheduleAtFixedRate(() -> {
            try {
                String now = LocalDateTime.now().toString();
                workBuffer.put(new Work(now));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }, 1, 200, TimeUnit.MILLISECONDS);

        ScheduledExecutorService workerScheduleService = new ScheduledThreadPoolExecutor(1,
                new NamedThreadFactory("MonitorWorker"));
        workerScheduleService.scheduleAtFixedRate(() -> {
            try {
                Work<String> work = workBuffer.get();
                if (work == null) {
                    System.out.println("work is null.");
                } else {
                    System.out.println(work.getWorkContent() + " tail:" + workBuffer.getTail() + " head:" + workBuffer.getHead());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, 1, 500, TimeUnit.MILLISECONDS);
    }
}
