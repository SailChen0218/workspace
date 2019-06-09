package com.ezshop.infra.tunnel.dataobject;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "order")
@Data
public class OrderAggrootDo extends BaseDataObject {
    private static final long serialVersionUID = -1915674260118652718L;
    @Id
    private String orderId;
    private String commodity;
    private String postAddress;
}
