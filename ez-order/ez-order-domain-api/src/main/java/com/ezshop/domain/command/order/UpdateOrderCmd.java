package com.ezshop.domain.command.order;

import com.ezddd.core.annotation.EzCommand;
import com.ezddd.core.annotation.EzIdentifier;
import com.ezddd.core.command.AbstractCommand;
import com.ezddd.core.command.CommandType;
import com.ezshop.domain.constants.DomainConstants;
import lombok.Data;

@EzCommand(domain = DomainConstants.NAME, commandType = CommandType.UPDATE)
@Data
public class UpdateOrderCmd extends AbstractCommand {
    private static final long serialVersionUID = 3458724402444187156L;
    @EzIdentifier
    private String orderId;
    private String commodity;
    private String postAddress;
}
