package com.ezddd.app.invoker;

public interface Invoker<T> {

    /**
     * get service interface.
     *
     * @return service interface.
     */
    Class<T> getInterface();

    /**
     * invoke.
     *
     * @param invocation
     * @return result
     * @throws Exception
     */
//    Result invoke(Invocation invocation) throws Exception;
}