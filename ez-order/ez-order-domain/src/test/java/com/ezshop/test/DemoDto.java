package com.ezshop.test;

import com.ezshop.desensitize.DesensitizedField;
import com.ezshop.desensitize.impl.type.ChineseNameType;
import com.ezshop.desensitize.impl.type.EmailType;
import com.ezshop.desensitize.impl.type.IDCardType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@ApiModel(value = "样例", description = "用于模拟后端校验及脱敏功能。")
public class DemoDto {

    @ApiModelProperty("登记序号")
    @Length(min = 31, max = 31, message = "djxh长度为31位")
    private String djxh;

    @ApiModelProperty("测试ID")
    @NotNull(message = "id不能为空。")
    private String id;

    @ApiModelProperty("测试姓名")
    @DesensitizedField(ChineseNameType.class)
    private String name;

    @ApiModelProperty("电子邮件")
    @Email(message = "email格式不符合要求。")
    @DesensitizedField(EmailType.class)
    private String email;

    @ApiModelProperty("a值")
    private int a;
    @ApiModelProperty("b值")
    private int b;

    @ApiModelProperty("身份证件号码")
    @DesensitizedField(IDCardType.class)
    private String sfzjhm;

    @ApiModelProperty("A样例对象")
    private ADemoDto aDemoDto;

    @ApiModelProperty("B样例对象")
    private BDemoDto bDemoDto;

    @ApiModelProperty("A样例对象List")
    private List aDemoDtoList;

    @ApiModelProperty("B样例对象Set")
    private Set<BDemoDto> bDemoDtoSet;

    public ADemoDto getADemoDto() {
        return aDemoDto;
    }

    public void setADemoDto(ADemoDto aDemoDto) {
        this.aDemoDto = aDemoDto;
    }

    public BDemoDto getBDemoDto() {
        return bDemoDto;
    }

    public void setBDemoDto(BDemoDto bDemoDto) {
        this.bDemoDto = bDemoDto;
    }

    public String getDjxh() {
        return djxh;
    }

    public void setDjxh(String djxh) {
        this.djxh = djxh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public String getSfzjhm() {
        return sfzjhm;
    }

    public void setSfzjhm(String sfzjhm) {
        this.sfzjhm = sfzjhm;
    }

    public List<ADemoDto> getADemoDtoList() {
        return aDemoDtoList;
    }

    public void setADemoDtoList(List<ADemoDto> aDemoDtoList) {
        this.aDemoDtoList = aDemoDtoList;
    }

    public Set<BDemoDto> getBDemoDtoSet() {
        return bDemoDtoSet;
    }

    public void setBDemoDtoSet(Set<BDemoDto> bDemoDtoSet) {
        this.bDemoDtoSet = bDemoDtoSet;
    }
}
