package com.ezshop.domain.command.order;

import com.ezddd.core.annotation.EzCommand;
import com.ezddd.core.command.AbstractCommand;
import com.ezddd.core.command.CommandType;
import com.ezddd.core.command.query.constraints.Constraint;
import com.ezddd.core.command.query.constraints.ConstraintType;
import com.ezshop.domain.constants.DomainConstants;
import lombok.Data;

@EzCommand(domain = DomainConstants.NAME, commandType = CommandType.CREATE)
@Data
public class CreateOrderCmd extends AbstractCommand {
    private static final long serialVersionUID = 3163630996471381514L;
    @Constraint(type = ConstraintType.NotNull, message = "${V0001}")
    private String commodity;
    @Constraint(type = ConstraintType.NotNull, message = "${V0001}")
    private String postAddress;
    @Constraint(type = ConstraintType.Email, message = "${V0001}")
    private String email;
}
