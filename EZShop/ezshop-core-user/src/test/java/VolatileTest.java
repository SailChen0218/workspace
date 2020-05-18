import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VolatileTest {
    private static final Object[] obj = {};
    private static final int THREAD_COUNT = 20;

    public static volatile int race = 0;
    public static void increase() {
        synchronized (obj) {
            race ++;
        }
    }

//    public static void main(String[] args) {
//        Thread[] threads = new Thread[THREAD_COUNT];
//        int i = 0;
//        for (Thread thread: threads) {
//            i++;
//            thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    for (int i = 0; i < 10000; i++) {
//                        increase();
//                    }
//                }
//            });
//            thread.start();
//            System.out.println("thread " + String.valueOf(i) + " started");
//        }
//
//        while (true) {
//            if (Thread.activeCount() == 1) {
//                System.out.println(race);
//                return;
//            }
//        }
//    }

    public static void main(String[] args) {
        ExecutorService exe = Executors.newFixedThreadPool(50);
        for (int i = 1; i <= THREAD_COUNT; i++) {
            exe.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        increase();
                    }
                }
            });
            System.out.println("thread " + String.valueOf(i) + " started");
        }
        exe.shutdown();
        while (true) {
            if (exe.isTerminated()) {
                System.out.println("结束了！" + "race:" + String.valueOf(race));
                break;
            }
        }
    }
}
