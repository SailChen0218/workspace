package com.ezddd.domain.model;

import com.ezddd.domain.Event.AggregateEvent;
import com.ezddd.domain.Event.Event;
import com.ezddd.domain.Event.EventArgs;
import com.ezddd.domain.Event.EventBus;
import com.ezddd.domain.repository.RepositoryProvider;
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
public class AggregateManager {
    public static RepositoryProvider repositoryProvider;
    public static EventBus eventBus;
    public static <T> void raiseEvent(String eventName, T sender) {
        AggregateManager.raiseEvent(eventName, sender, null);
    }

    public static <T> void raiseEvent(String eventName, T sender, Map<String, Object> args) {
//        repositoryProvider.repositoryFor()
    }

    public static <T, A> void applyEvent(String eventName, T sender, A args) {
        try {
            EventArgs<A> eventArgs = new EventArgs<>();
            eventArgs.setArgs(args);
            Event event = new AggregateEvent().withSender(sender).withArgs(eventArgs);
            eventBus.publish(event);
        } catch (Exception ex) {

        }
    }
}
