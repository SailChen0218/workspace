/**
 * 任务内容
 *
 * @param <T>
 */
public class Work<T> {
    private T workContent;

    public Work(T workContent) {
        this.workContent = workContent;
    }

    public T getWorkContent() {
        return this.workContent;
    }
}
