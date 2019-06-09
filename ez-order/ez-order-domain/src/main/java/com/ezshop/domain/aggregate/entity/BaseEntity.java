package com.ezshop.domain.aggregate.entity;

import com.ezddd.core.annotation.EzVersion;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class BaseEntity implements Serializable, Cloneable {
    @EzVersion
    protected long version;
    protected long createTime;
    protected long updateTime;
    protected boolean deleted;
}
