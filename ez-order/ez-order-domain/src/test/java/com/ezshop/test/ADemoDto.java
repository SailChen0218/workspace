package com.ezshop.test;

import com.ezshop.desensitize.DesensitizedField;
import com.ezshop.desensitize.type.impl.AddressType;

public class ADemoDto {
    @DesensitizedField(AddressType.class)
    private String a_field1;
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
