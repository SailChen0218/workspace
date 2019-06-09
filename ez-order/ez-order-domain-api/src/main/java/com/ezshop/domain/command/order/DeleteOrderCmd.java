package com.ezshop.domain.command.order;

import com.ezddd.core.annotation.EzCommand;
import com.ezddd.core.annotation.EzIdentifier;
import com.ezddd.core.command.AbstractCommand;
import com.ezddd.core.command.CommandType;
import com.ezshop.domain.constants.DomainConstants;
import lombok.Data;

@EzCommand(domain = DomainConstants.NAME, commandType = CommandType.DELETE)
@Data
public class DeleteOrderCmd extends AbstractCommand {
    private static final long serialVersionUID = -6599816723422549033L;
    @EzIdentifier
    private String orderId;
}
