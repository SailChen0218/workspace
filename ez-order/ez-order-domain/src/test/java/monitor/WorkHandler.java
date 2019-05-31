package monitor;

public interface WorkHandler<T> {
    void doWork(Work<T> work);
}
