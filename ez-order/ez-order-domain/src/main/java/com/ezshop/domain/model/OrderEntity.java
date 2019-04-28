package com.ezshop.domain.model;

import com.ezddd.domain.annotation.EzAggregate;

@EzAggregate
public class OrderEntity extends BaseEntity {
    private static final long serialVersionUID = 5488832782823340677L;

//    @EzEvent(name="", type=EventType.Created)
    public void createOrder() {
        System.out.println("create OrderEntity...");
    }
}
