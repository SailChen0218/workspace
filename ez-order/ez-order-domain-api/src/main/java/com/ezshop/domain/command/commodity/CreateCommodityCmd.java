package com.ezshop.domain.command.commodity;

import com.ezddd.core.annotation.EzCommand;
import com.ezddd.core.command.AbstractCommand;
import com.ezddd.core.command.CommandType;

@EzCommand(domain = "order", commandType = CommandType.CREATE)
public class CreateCommodityCmd extends AbstractCommand {
    private String name;
    private String classification;
    private String label;
    private String storeForSale;
    private int totalSales;
    private String service;

    public String getName() {
        return name;
    }

    public String getClassification() {
        return classification;
    }

    public String getLabel() {
        return label;
    }

    public String getStoreForSale() {
        return storeForSale;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public String getService() {
        return service;
    }
}
