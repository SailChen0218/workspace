package com.ezshop.domain.aggregate;

import com.ezddd.core.aggregate.AggregateManager;
import com.ezddd.core.annotation.EzAggregate;
import com.ezddd.core.annotation.EzCommandHandler;
import com.ezddd.core.annotation.EzIdentifier;
import com.ezddd.core.event.EventPublishFailedException;
import com.ezddd.core.utils.AggregateUtil;
import com.ezshop.domain.aggregate.entity.BaseEntity;
import com.ezshop.domain.command.commodity.CreateCommodityCmd;

import java.time.Instant;

@EzAggregate
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
        this.createTime = Instant.now();
        this.updateTime = Instant.now();
        this.deleted = false;
        AggregateManager.apply("onCommodityCreated", this, cmd);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getStoreForSale() {
        return storeForSale;
    }

    public void setStoreForSale(String storeForSale) {
        this.storeForSale = storeForSale;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
