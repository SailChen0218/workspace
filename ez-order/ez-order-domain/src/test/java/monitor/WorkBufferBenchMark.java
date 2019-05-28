package monitor;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime) // 测试方法平均执行时间
@OutputTimeUnit(TimeUnit.MICROSECONDS) // 输出结果的时间粒度为微秒
@State(Scope.Thread) // 每个测试线程一个实例
public class WorkBufferBenchMark {
    final WaitStrategy waitWhenFull = new WaitWhenFull(2, 100);
    final WaitStrategy waitWhenMaxTail = new WaitWhenMaxTail(2, 100, 100);
    final WorkBuffer workBuffer = new WorkBuffer(1024 * 16, waitWhenFull, waitWhenMaxTail);
    ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(1024 * 16);

    @Benchmark
    public void workBuffer() {
        workBuffer.clear();
        for (int i = 0; i < 100; i++) {
            Runnable thread = new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        workBuffer.put(new Work("abc"));
                    }
                }
            };
            thread.run();
        }
//        workBuffer.clear();
//        Runnable thread1 = new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 300; i++) {
//                    workBuffer.put(new Work("abc"));
//                }
//            }
//        };
//        thread1.run();
//
//        Runnable thread2 = new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 300; i++) {
//                    workBuffer.put(new Work("abc"));
//                }
//            }
//        };
//        thread2.run();
//
//        Runnable thread3 = new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 300; i++) {
//                    workBuffer.put(new Work("abc"));
//                }
//            }
//        };
//        thread3.run();
    }

    @Benchmark
    public void queue() {
//        arrayBlockingQueue.add(new Work("abc"));
//        arrayBlockingQueue.poll();
        arrayBlockingQueue.clear();
        for (int i = 0; i < 100; i++) {
            Runnable thread = new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        arrayBlockingQueue.add(new Work("abc"));
                    }
                }
            };
            thread.run();
        }
//        arrayBlockingQueue.clear();
//        for (int i = 0; i < arrayBlockingQueue.size(); i++) {
//            try {
//                arrayBlockingQueue.put(new Work("abc"));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        for (int i = 0; i < arrayBlockingQueue.size(); i++) {
//            arrayBlockingQueue.poll();
//        }

//        Runnable thread1 = new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 300; i++) {
//                    arrayBlockingQueue.add(new Work("abc"));
//                }
//            }
//        };
//        thread1.run();
//
//        Runnable thread2 = new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 300; i++) {
//                    arrayBlockingQueue.add(new Work("abc"));
//                }
//            }
//        };
//        thread2.run();
//
//        Runnable thread3 = new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 300; i++) {
//                    arrayBlockingQueue.add(new Work("abc"));
//                }
//            }
//        };
//        thread3.run();
    }

    public static void main(String[] args) throws RunnerException {
        // 使用一个单独进程执行测试，执行5遍warmup，然后执行5遍测试
        Options opt = new OptionsBuilder()
                .include(WorkBufferBenchMark.class.getSimpleName())
                .forks(1)
                .warmupIterations(1)
                .measurementIterations(3)
                .build();
        new Runner(opt).run();
    }
}
