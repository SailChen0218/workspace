package com.ezddd.core.tunnel;

public interface Tunnel<T> {
    int removeByIdentifier(String identifier);
    int create(T aggregateDo);
    int save(T aggregateDo);
    T findByIdentifier(String identifier);
    T findByIdentifier(String identifier, String version);
}
