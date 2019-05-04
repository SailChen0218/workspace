package com.ezshop.domain.command.order;

import com.ezddd.core.annotation.EzCommand;
import com.ezddd.core.command.AbstractCommand;
import com.ezddd.core.command.CommandType;

@EzCommand(domain = "order", commandType = CommandType.DELETE)
public class DeleteOrderCmd extends AbstractCommand {
    private static final long serialVersionUID = -6599816723422549033L;
    private String id;
}
