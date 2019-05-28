import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MonitorLogBuffer<T> {
    private static final int DEFAULT_SIZE  = 1024 * 8;
    private String[] buffer;
    private AtomicInteger head = new AtomicInteger(0);
    private AtomicInteger tail = new AtomicInteger(0);
    private int bufferSize;

    public MonitorLogBuffer(){
        this.bufferSize = DEFAULT_SIZE;
        this.buffer = new String[bufferSize];
    }

    public MonitorLogBuffer(int initSize){
        this.bufferSize = initSize;
        this.buffer = new String[bufferSize];
    }

    private Boolean isEmpty() {
        return head.get() == tail.get();
    }

    public void clear(){
        Arrays.fill(buffer,null);
        head.set(0);
        tail.set(0);
    }

    public Boolean put(String v) {
        buffer[tail.get()] = v;
        tail.set(tail.incrementAndGet() % bufferSize);
        return true;
    }

    public String get() {
        if (isEmpty()) {
            return null;
        }
        String monitorLogDto = buffer[head.get()];
        head.set(head.incrementAndGet() % bufferSize);
        return monitorLogDto;
    }

    public List<String> get(int length) {
        if (isEmpty() || length < 0) {
            return null;
        }

        List<String> monitorLogDtoList = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            String monitorLogDto = this.get();
            monitorLogDtoList.add(monitorLogDto);
            if (isEmpty()) {
                break;
            }
        }
        return monitorLogDtoList;
    }

    public static void main(String[] args) {
        MonitorLogBuffer monitorLogBuffer = new MonitorLogBuffer();
        monitorLogBuffer.put("1");
        monitorLogBuffer.put("2");
        monitorLogBuffer.put("3");
        monitorLogBuffer.put("4");
        while (true) {
            List<String> values = monitorLogBuffer.get(10);
            if (values != null && values.size() > 0) {
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i) != null) {
                        System.out.println(values.get(i));
                    }
                }
            }
        }
    }
}
