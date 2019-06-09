package com.ezshop.infra.tunnel.dataobject;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "commodity")
@Data
public class CommodityAggrootDo extends BaseDataObject {
    private static final long serialVersionUID = 8581456861693862055L;
    @Id
    private String commodityId;
    private String name;
    private String classification;
    private String label;
    private String storeForSale;
    private int totalSales;
    private String service;
}
