package com.ezddd.core.aggregate;

import com.ezddd.core.event.AggregateEvent;
import com.ezddd.core.event.EventArgs;
import com.ezddd.core.event.EventBus;
import com.ezddd.core.annotation.EzComponent;

import com.ezddd.core.constants.PropertyKey;
import com.ezddd.core.repository.RepositoryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(name = PropertyKey.SERVER_TYPE, value = "domain")
@EzComponent
public class AggregateManager {

    @Autowired
    RepositoryProvider repositoryProvider;

    @Autowired
    EventBus defaultEventBus;

    private static AggregateManager aggregateManager;

    public static <S, A> void raiseEvent(String eventName, S sender, A args, int eventType) {
    }

    public static <S, A> void applyEvent(String eventName, S sender, A args, int eventType) {
        try {
            EventArgs<A> eventArgs = new EventArgs<>(args);
            AggregateEvent<S> event = AggregateEvent.Factory.createEvent(eventName, sender, eventArgs, eventType);
            aggregateManager.defaultEventBus.publish(event);
        } catch (Exception ex) {
        }
    }
}
