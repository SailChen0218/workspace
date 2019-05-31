package com.ezshop.domain.aggregate.entity;

import com.ezddd.core.annotation.EzVersion;

import java.io.Serializable;
import java.time.Instant;

public abstract class BaseEntity implements Serializable, Cloneable {
    @EzVersion
    protected long version;
    protected Instant createTime;
    protected Instant updateTime;
    protected boolean deleted;

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

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
