package com.ezshop.domain.aggregate;

import com.ezddd.core.aggregate.AggregateManager;
import com.ezddd.core.annotation.EzAggregate;
import com.ezddd.core.annotation.EzCommandHandler;
import com.ezddd.core.annotation.EzIdentifier;
import com.ezddd.core.event.EventPublishFailedException;
import com.ezddd.core.utils.AggregateUtil;
import com.ezshop.domain.aggregate.entity.BaseEntity;
import com.ezshop.domain.command.commodity.CreateCommodityCmd;
import lombok.Data;

import java.time.Instant;

@EzAggregate
@Data
public class CommodityAggroot extends BaseEntity {
    private static final long serialVersionUID = 5488832782823340677L;

    @EzIdentifier
    private String commodityId;
    private String name;
    private String classification;
    private String label;
    private String storeForSale;
    private int totalSales;
    private String service;

    @EzCommandHandler
    public CommodityAggroot(CreateCommodityCmd cmd) throws EventPublishFailedException {
        this.commodityId = AggregateUtil.generateID();
        this.name = cmd.getName();
        this.classification = cmd.getClassification();
        this.label = cmd.getLabel();
        this.storeForSale = cmd.getStoreForSale();
        this.totalSales = cmd.getTotalSales();
        this.service = cmd.getService();
        this.version = 1L;
        this.createTime = System.currentTimeMillis();
        this.updateTime = System.currentTimeMillis();
        this.deleted = false;
        AggregateManager.apply("onCommodityCreated", this, cmd);
    }
}
