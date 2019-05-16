package com.ezshop.domain.aggregate.entity;

import java.io.Serializable;
import java.time.Instant;

public abstract class BaseEntity implements Serializable {
    private Instant createTime;
    private Instant updateTime;

    public Instant getCreateTime() {
        return createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }
}
