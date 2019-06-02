package com.ezshop.infra.tunnel.dataobject;


import com.ezddd.core.tunnel.DataObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "commodity")
public class CommodityAggrootDo extends DataObject {
    private static final long serialVersionUID = 8581456861693862055L;
    @Id
    private String commodityId;
    private String name;
    private String classification;
    private String label;
    private String storeForSale;
    private int totalSales;
    private String service;

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
