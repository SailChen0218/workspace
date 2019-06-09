package com.ezshop.infra.tunnel.dataobject;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class BaseDataObject implements Serializable {
    private long version;
    private long createTime;
    private long updateTime;
    private boolean deleted;
}
