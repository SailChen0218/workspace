package benchmark;

import monitor.Work;
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
public class WorkBufferIntBenchMark {
    final WorkBufferInt workBuffer = new WorkBufferInt(1024);
    ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(1024);

    @Benchmark
    public void workBuffer() {
        workBuffer.clear();
        Runnable thread1 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 300; i++) {
                    workBuffer.put(new Work("abc"));
                }
            }
        };
        thread1.run();

        Runnable thread2 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 300; i++) {
                    workBuffer.put(new Work("abc"));
                }
            }
        };
        thread2.run();

        Runnable thread3 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 300; i++) {
                    workBuffer.put(new Work("abc"));
                }
            }
        };
        thread2.run();
    }

    @Benchmark
    public void queue() {
        arrayBlockingQueue.clear();
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

        Runnable thread1 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 300; i++) {
                    arrayBlockingQueue.add(new Work("abc"));
                }
            }
        };
        thread1.run();

        Runnable thread2 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 300; i++) {
                    arrayBlockingQueue.add(new Work("abc"));
                }
            }
        };
        thread2.run();

        Runnable thread3 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 300; i++) {
                    arrayBlockingQueue.add(new Work("abc"));
                }
            }
        };
        thread2.run();
    }

    public static void main(String[] args) throws RunnerException {
        // 使用一个单独进程执行测试，执行5遍warmup，然后执行5遍测试
        Options opt = new OptionsBuilder()
                .include(WorkBufferIntBenchMark.class.getSimpleName())
                .forks(1)
                .warmupIterations(1)
                .measurementIterations(3)
                .build();
        new Runner(opt).run();
    }
}
