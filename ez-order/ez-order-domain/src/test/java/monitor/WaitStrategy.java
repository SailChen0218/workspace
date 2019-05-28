package monitor;

public interface WaitStrategy {
    boolean waitFor(WorkBuffer workBuffer);
}
