package com.ezshop.domain.command.order;

import com.ezddd.core.annotation.EzCommand;
import com.ezddd.core.command.AbstractCommand;
import com.ezddd.core.command.CommandType;

@EzCommand(domain = "order", commandType = CommandType.UPDATE)
public class UpdateOrderCmd extends AbstractCommand {
    private static final long serialVersionUID = 3458724402444187156L;
    private String id;
    private String commodity;
    private String postAddress;

    public String getId() {
        return id;
    }

    public String getCommodity() {
        return commodity;
    }

    public String getPostAddress() {
        return postAddress;
    }
}
