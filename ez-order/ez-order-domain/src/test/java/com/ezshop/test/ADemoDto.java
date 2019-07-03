package com.ezshop.test;

import com.ezshop.desensitize.DesensitizedField;
import com.ezshop.desensitize.impl.type.AddressType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "A样例")
public class ADemoDto {

    @ApiModelProperty("a字段1")
    @DesensitizedField(AddressType.class)
    private String a_field1;

    @ApiModelProperty("a字段2")
    private String a_field2;

    public String getA_field1() {
        return a_field1;
    }

    public void setA_field1(String a_field1) {
        this.a_field1 = a_field1;
    }

    public String getA_field2() {
        return a_field2;
    }

    public void setA_field2(String a_field2) {
        this.a_field2 = a_field2;
    }
}
