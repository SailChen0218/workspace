package com.ezddd.domain.Event;

import com.ezddd.domain.model.Aggregate;

import java.time.Instant;
import java.util.Map;

/**
 * <p>标题: </p>
 * <p>功能描述: </p>
 * <p>
 * <p>版权: 税友软件集团股份有限公司</p>
 * <p>创建时间: 2019/4/30</p>
 * <p>作者：cqf</p>
 * <p>修改历史记录：</p>
 * ====================================================================<br>
 */
public class AggregateEvent<T> implements Event {
    private static final long serialVersionUID = -6975653243726439083L;
    private T sender;

    @Override
    public String getEventName() {
        return null;
    }

    @Override
    public Instant getTimestamp() {
        return null;
    }

    @Override
    public int getEventType() {
        return 0;
    }

    @Override
    public <T> Event withSender(T sender) {
        return null;
    }

    @Override
    public Event withArgs(EventArgs args) {
        return null;
    }
}
