package com.ezshop.domain.command.order;

import com.ezddd.core.annotation.EzCommand;
import com.ezddd.core.command.AbstractCommand;
import com.ezddd.core.command.CommandType;

@EzCommand(domain = "order", commandType = CommandType.CREATE)
public class CreateOrderCmd extends AbstractCommand {
    private static final long serialVersionUID = 3163630996471381514L;
    private String commodity;
    private String postAddress;

    public String getCommodity() {
        return commodity;
    }
    public String getPostAddress() {
        return postAddress;
    }
}
