package com.ezddd.core.tunnel;

public interface Convertor<E, D> {
    D EntityToDataObject(E entity);
    E DataObjectToEntity(D dataObject);
}
