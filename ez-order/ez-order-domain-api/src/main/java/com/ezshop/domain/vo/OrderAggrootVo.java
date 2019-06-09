package com.ezshop.domain.vo;

import com.ezddd.core.utils.ConvertDate;
import lombok.Data;

@Data
public class OrderAggrootVo extends BaseVo {
    private static final long serialVersionUID = -6041074755993608711L;
    private String orderId;
    private String commodity;
    private String postAddress;
    private long version;
    @ConvertDate
    private String createTime;
    @ConvertDate
    private String updateTime;
    private boolean deleted;
}
