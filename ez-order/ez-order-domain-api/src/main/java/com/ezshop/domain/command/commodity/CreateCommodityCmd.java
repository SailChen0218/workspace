package com.ezshop.domain.command.commodity;

import com.ezddd.core.annotation.EzCommand;
import com.ezddd.core.command.AbstractCommand;
import com.ezddd.core.command.CommandType;
import com.ezshop.domain.constants.DomainConstants;
import lombok.Data;

@EzCommand(domain = DomainConstants.NAME, commandType = CommandType.CREATE)
@Data
public class CreateCommodityCmd extends AbstractCommand {
    private String name;
    private String classification;
    private String label;
    private String storeForSale;
    private int totalSales;
    private String service;
}
