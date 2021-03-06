package benchmark;

import monitor.NamedThreadFactory;
import monitor.Work;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 任务缓存
 */
public class WorkBufferInt {
    /**
     * 每个MonitorLogDto对象占用3149 B内存。
     */
    private final Work[] buffer;
    private int tail;
    private int head;
    private final int bufferSize;
    private final int indexMask;

    private static final int MAX_TAIL = Integer.MAX_VALUE;

    public WorkBufferInt(int initSize) {
        this.bufferSize = initSize;
        this.buffer = new Work[bufferSize];
        this.indexMask = bufferSize - 1;

        tail = 0;
        head = 0;
    }

    private Boolean isEmpty() {
        return head == tail;
    }

    private Boolean isFull() {
        if (head == 0) {
            return false;
        } else {
            if ((tail / bufferSize) > (head / bufferSize)) {
                return ((tail + 1) & indexMask) >= (head & indexMask);
            } else {
                return false;
            }
        }
    }

    public void clear() {
        Arrays.fill(buffer, null);
        tail = 0;
        head = 0;
    }

    public Boolean put(Work v) {
        if (tail == MAX_TAIL) {
//            while (head.get() < tail.get()) {
//                try {
//                    System.out.println("max tail. wait for clear.");
//                    TimeUnit.MILLISECONDS.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    return false;
//                }
//            }
            if (head < tail) {
                System.out.println("max tail. wait for clear.");
                return false;
            } else {
                clear();
            }
        }

        int count = 0;
        while (isFull()) {
            try {
                if (count > 2) {
                    System.out.println("sleep 500mllis." + LocalDateTime.now().toString() + " count > 2");
                    return false;
                } else {
                    count++;
                }
                System.out.println("sleep 500mllis." + LocalDateTime.now().toString() + " count:" + count);
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }
        int index = tail & indexMask;
        buffer[index] = v;
        tail++;
        return true;
    }

    public Work get() {
        if (isEmpty()) {
            return null;
        }
        int index = head & indexMask;
        Work work = buffer[index];
        head++;
        return work;
    }

    public List<Work> getWorks(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length must greater than 0");
        }
        List<Work> workList = new ArrayList<>(length);
        Work work = this.get();
        workList.add(work);
        return workList;
    }

    public int getTail() {
        return this.tail;
    }

    public int getHead() {
        return this.head;
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    public static void main(String[] args) {
        final WorkBufferInt workBuffer = new WorkBufferInt(32);

        ScheduledExecutorService masterScheduleService = new ScheduledThreadPoolExecutor(1,
                new NamedThreadFactory("MonitorMaster"));
        masterScheduleService.scheduleAtFixedRate(() -> {
            try {
//                String now = LocalDateTime.now().toString();
//                while (!workBuffer.put(new Work(now))) {
//                    try {
//                        System.out.println("sleep 500mllis");
//                        TimeUnit.MILLISECONDS.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
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
//                List<Work<String>> works = new ArrayList<>();
//                for (int i = 0; i < 10; i++) {
//                    Work<String> work = workBuffer.get();
//                    if (work == null) {
//                        works.add(work);
//                    } else {
//                        break;
//                    }
//                }


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

//        PutTask putTask = new PutTask(workBuffer);
//        GetTask getTask = new GetTask(workBuffer);
//        putTask.run();
//        getTask.run();
    }

    static class PutTask implements Runnable {
        private WorkBufferInt workBuffer;

        public PutTask(WorkBufferInt workBuffer) {
            this.workBuffer = workBuffer;
        }

        @Override
        public void run() {
            while (true) {
                String now = LocalDateTime.now().toString();
                while (!workBuffer.put(new Work(now))) {
                    try {
                        System.out.println("sleep 500mllis." + LocalDateTime.now().toString());
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class GetTask implements Runnable {
        private WorkBufferInt workBuffer;

        public GetTask(WorkBufferInt workBuffer) {
            this.workBuffer = workBuffer;
        }

        @Override
        public void run() {
            while (true) {
                Work<String> work = workBuffer.get();
                System.out.println(work.getWorkContent() + " tail:" + workBuffer.getTail() + " head:" + workBuffer.getHead());
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
