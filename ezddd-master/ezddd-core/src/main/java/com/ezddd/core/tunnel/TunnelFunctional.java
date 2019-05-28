package com.ezddd.core.tunnel;

public class TunnelFunctional {

    @FunctionalInterface
    public interface TunnelSave {
        int save();
    }

    @FunctionalInterface
    public interface TunnelFind<T> {
        T find();
    }

    @FunctionalInterface
    public interface TunnelCreate {
        int create();
    }

    @FunctionalInterface
    public interface TunnelRemove {
        int remove();
    }
}


