package com.ezshop.test;

import com.ezshop.desensitize.Desensitized;
import org.springframework.stereotype.Component;

import javax.annotation.Priority;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//@Api(description = "接口入参校验及返回值脱敏测试接口")
@Component
@Priority(1)
public class DemoServiceImpl implements DemoService {

    /**
     * 1、使用hibernate-validator进行参数校验。
     *
     * @param djxh
     * @param email
     * @return
     */
    @Desensitized
    @Override
    public DemoDto getDemoDto(String djxh, String email) {
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

        ADemoDto aDemoDto1 = new ADemoDto();
        aDemoDto1.setA_field1("a_field_test1");
        aDemoDto1.setA_field2("a_field_test2");

        ADemoDto aDemoDto2 = new ADemoDto();
        aDemoDto2.setA_field1("a_field_test1");
        aDemoDto2.setA_field2("a_field_test2");

        List<ADemoDto> aDemoDtoList = new ArrayList<>();
        aDemoDtoList.add(aDemoDto1);
        aDemoDtoList.add(aDemoDto2);

        demoDto.setADemoDtoList(aDemoDtoList);

        BDemoDto bDemoDto1 = new BDemoDto();
        bDemoDto1.setB_field1("b_field_test1");
        bDemoDto1.setB_field2("b_field_test2");

//        BDemoDto bDemoDto2 = new BDemoDto();
//        bDemoDto2.setB_field1("b_field_test1");
//        bDemoDto2.setB_field2("b_field_test2");
//
//        Set<BDemoDto> bDemoDtoSet = new HashSet<>();
//        bDemoDtoSet.add(bDemoDto1);
//        bDemoDtoSet.add(bDemoDto2);
//        demoDto.setBDemoDtoSet(bDemoDtoSet);

        return demoDto;
    }

    /**
     * 2、业务规则校验，通过Fel实现。
     *
     * @param demoDto
     */
    @Desensitized
    @Override
    public void updateDemoDto(DemoDto demoDto) {
        System.out.println("updateDemoDto by DTO.");
    }

    @Desensitized
    @Override
    public List<String> getDemoNames() {
        return Collections.singletonList("AAA");
    }

    @Override
    public boolean isNsrxxNormal() {
        return true;
    }

    /**
     * 3、返回值脱敏
     *
     * @return
     */
    @Desensitized
    @Override
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
    @Override
    public List<DemoDto> listDemoDtoWithAuth() {
        List<DemoDto> demoDtos = new ArrayList<>();
        DemoDto demoDto = new DemoDto();
        demoDto.setDjxh("1312312");
        demoDto.setEmail("test@163.com");
        demoDto.setName("test");
        demoDtos.add(demoDto);
        return demoDtos;
    }

    /**
     * 1、使用hibernate-validator进行参数校验。
     *
     * @param djxh
     * @param email
     * @return
     */
    @Desensitized
    @Override
    public ResultDto<DemoDto> getResultDemoDto(String djxh, String email) {
        DemoDto demoDto = this.getDemoDto(djxh, email);
        return ResultDto.valueOfSuccess(demoDto);
    }

    /**
     * 1、使用hibernate-validator进行参数校验。
     *
     * @param djxh
     * @param email
     * @return
     */
    @Desensitized
    @Override
    public ResultDto<List<DemoDto>> getResultDemoDtoList(String djxh, String email) {
        DemoDto demoDto = this.getDemoDto(djxh, email);
        return ResultDto.valueOfSuccess(Collections.singletonList(demoDto));
    }

    private boolean isServyouClient() {
        return true;
    }
}
