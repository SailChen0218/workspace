package com.ezshop.domain.aggregate.entity;

import com.ezddd.core.annotation.EzVersion;

import java.io.Serializable;
import java.time.Instant;

public abstract class BaseEntity implements Serializable, Cloneable {
    @EzVersion
    private long version;
    private Instant createTime;
    private Instant updateTime;
    private boolean isDeleted;

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
