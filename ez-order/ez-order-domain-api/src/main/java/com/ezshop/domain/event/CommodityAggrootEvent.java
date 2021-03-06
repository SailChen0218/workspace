package com.ezshop.domain.event;

import com.ezddd.core.annotation.EzEvent;
import com.ezddd.core.event.AbstractEventDefinition;
import com.ezddd.core.event.impl.MqEventBus;
import com.ezshop.domain.constants.DomainConstants;

@EzEvent(domain = DomainConstants.NAME, eventBusType = MqEventBus.class)
public class CommodityAggrootEvent extends AbstractEventDefinition {
    public static final CommodityAggrootEvent onCommodityCreated =
            new CommodityAggrootEvent("onCommodityCreated");

    protected CommodityAggrootEvent(String eventName) {
        super(eventName);
    }
}
