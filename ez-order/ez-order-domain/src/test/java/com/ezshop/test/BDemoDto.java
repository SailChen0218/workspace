package com.ezshop.test;

import com.ezshop.desensitize.DesensitizedField;
import com.ezshop.desensitize.impl.type.AddressType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "B样例")
public class BDemoDto {

    @ApiModelProperty("b字段1")
    @DesensitizedField(AddressType.class)
    private String b_field1;

    @ApiModelProperty("b字段2")
    private String b_field2;

    public String getB_field1() {
        return b_field1;
    }

    public void setB_field1(String b_field1) {
        this.b_field1 = b_field1;
    }

    public String getB_field2() {
        return b_field2;
    }

    public void setB_field2(String b_field2) {
        this.b_field2 = b_field2;
    }
}
