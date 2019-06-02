package com.ezshop.infra.tunnel.dataobject;

import com.ezddd.core.tunnel.DataObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "order")
public class OrderAggrootDo extends DataObject {
    private static final long serialVersionUID = -1915674260118652718L;
    @Id
    private String orderId;
    private String commodity;
    private String postAddress;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getPostAddress() {
        return postAddress;
    }

    public void setPostAddress(String postAddress) {
        this.postAddress = postAddress;
    }
}
