package com.ezshop.domain.event;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.annotation.EzEventHandler;
import com.ezddd.core.event.AbstractEventListener;
import com.ezddd.core.event.Event;
import com.ezshop.domain.aggregate.CommodityAggroot;
import com.ezshop.domain.repository.CommodityAggrootRepository;
import org.springframework.beans.factory.annotation.Autowired;

@EzComponent
public class CommodityAggrootListener extends AbstractEventListener<CommodityAggroot> {

    @Autowired
    CommodityAggrootRepository commodityAggrootRepository;

    @EzEventHandler
    public void onCommodityCreated(Event<CommodityAggroot> event) {
        commodityAggrootRepository.create(event.getSender());
    }
}
