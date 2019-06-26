package com.ezshop.test;

import com.ezshop.desensitize.Desensitized;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

//@Api(description = "接口入参校验及返回值脱敏测试接口")
@Component
public class DemoService {

    /**
     * 1、使用hibernate-validator进行参数校验。
     *
     * @param djxh
     * @param email
     * @return
     */
    @Desensitized
    public DemoDto getDemoDto(
            @ApiParam(name = "djxh", value = "登记序号")
            @Length(min = 31, max = 31, message = "djxh长度必须为{min}位。") String djxh,
            @ApiParam(name = "email", value = "电子邮件")
            @Email(message = "email格式不符合要求。") String email) {
        DemoDto demoDto = new DemoDto();
        demoDto.setDjxh("12312312312");
        demoDto.setEmail("test@163.com");
        demoDto.setName("test");

        ADemoDto aDemoDto = new ADemoDto();
        aDemoDto.setA_field1("a_field_test1");
        aDemoDto.setA_field2("a_field_test2");
        demoDto.setADemoDto(aDemoDto);

        BDemoDto bDemoDto = new BDemoDto();
        bDemoDto.setB_field1("b_field_test1");
        bDemoDto.setB_field2("b_field_test2");
        demoDto.setBDemoDto(bDemoDto);

        return demoDto;
    }

    /**
     * 2、业务规则校验，通过Fel实现。
     *
     * @param demoDto
     */
    public void updateDemoDto(@Valid DemoDto demoDto) {
        System.out.println("updateDemoDto by DTO.");
    }

    public boolean isNsrxxNormal() {
        return true;
    }

    /**
     * 3、返回值脱敏
     *
     * @return
     */
    @Desensitized
    public List<DemoDto> listDemoDto(String djxh) {
        List<DemoDto> demoDtos = new ArrayList<>();
        DemoDto demoDto = new DemoDto();
        demoDto.setDjxh("12312312312");
        demoDto.setEmail("test@163.com");
        demoDto.setName("test");
        demoDtos.add(demoDto);
        return demoDtos;
    }

    /**
     * 4、返回字段权限控制
     *
     * @return
     */
//    @ApiOperation("查询所有demoDto数据")
    @Desensitized
    public List<DemoDto> listDemoDtoWithAuth() {
        List<DemoDto> demoDtos = new ArrayList<>();
        DemoDto demoDto = new DemoDto();
        demoDto.setDjxh("1312312");
        demoDto.setEmail("test@163.com");
        demoDto.setName("test");
        demoDtos.add(demoDto);
        return demoDtos;
    }

    private boolean isServyouClient() {
        return true;
    }
}
