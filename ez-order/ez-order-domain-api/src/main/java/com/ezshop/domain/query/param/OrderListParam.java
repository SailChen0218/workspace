package com.ezshop.domain.query.param;

import com.ezddd.core.command.query.AbstractQueryParam;
import lombok.Data;

@Data
public class OrderListParam extends AbstractQueryParam {
    private static final long serialVersionUID = -7312541402183836208L;
    private String orderId;
    private String commodity;
}
