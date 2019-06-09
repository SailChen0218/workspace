package com.ezddd.core.utils.load;

public class PollingLoadStrategy<T> {
    private volatile int index;
    private T[] items;
    public PollingLoadStrategy(T[] items) {
        if (items == null) {
            throw new IllegalArgumentException("PollingLoadStrategy items must not be null.");
        }
        this.items = items;
    }
    public T choose() {
        index = (index + 1) % items.length;
        return items[index];
    }
}
