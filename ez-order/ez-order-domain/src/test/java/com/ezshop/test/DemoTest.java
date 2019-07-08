package com.ezshop.test;

import com.OrderDomainBootstrap;
import com.alibaba.fastjson.JSON;
import com.ezshop.desensitize.DesensitizeMethodHolder;
import com.ezshop.desensitize.dto.MethodResovingDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.MethodDescriptor;
import javax.validation.metadata.MethodType;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
//@ConfigurationProperties("classpath:application.properties")
@SpringBootTest(classes={OrderDomainBootstrap.class})
public class DemoTest {

    @Autowired
    DemoService demoService;

    @Autowired
    DesensitizeMethodHolder desensitizeMethodHolder;

    @Test
    public void testValidator_getDemoDto() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        ExecutableValidator executableValidator = factory.getValidator().forExecutables();

        DemoServiceImpl demoService = new DemoServiceImpl();
        Class<?> clazz = demoService.getClass();
        try {
            Method method = clazz.getDeclaredMethod("getDemoDto", String.class, String.class);
            Object[] parameterValues = {null, null};
            Set<ConstraintViolation<DemoServiceImpl>> violations = executableValidator
                    .validateParameters(demoService, method, parameterValues);
            for (ConstraintViolation<DemoServiceImpl> constraintViolation:violations){
                System.out.println(constraintViolation.getMessage());
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testValidator_getDemoDto_pg() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

        Validator validator = validatorFactory.getValidator();
        BeanDescriptor carDescriptor = validator.getConstraintsForClass( DemoServiceImpl.class );
        Set<MethodDescriptor> methodDescriptorSet = carDescriptor.getConstrainedMethods(MethodType.NON_GETTER);
        for (MethodDescriptor methodDescriptor: methodDescriptorSet){
            methodDescriptor.getConstraintDescriptors();
        }
    }

    @Test
    public void testupdateDemoDto_Aspect() {
        DemoDto demoDto = new DemoDto();
        demoDto.setDjxh("djxh");
        demoDto.setEmail("email");
        demoService.updateDemoDto(demoDto);
    }

    @Test
    public void testlistDemoDto_Aspect(){
        demoService.listDemoDto("djxh123123123123");
    }

    @Test
    public void testgetDemoDto_Aspect(){
        DemoDto demoDto = demoService.getDemoDto("1234567890123456789012345678901", "test@163.com");
//        DemoDto demoDto = demoService.getDemoDto(null, null);
        ResultDto<MethodResovingDto> resultDto = desensitizeMethodHolder
                .resolveMethod("com.ezshop.test.DemoService.getDemoDto");
        System.out.println(JSON.toJSONString(resultDto.getValue()));
        System.out.println(JSON.toJSONString(demoDto));
    }

    @Test
    public void testgetResultDemoDto_Aspect(){
        ResultDto<DemoDto> resultDemoDto = demoService.getResultDemoDto("1234567890123456789012345678901", "test@163.com");
        System.out.println(JSON.toJSONString(resultDemoDto));
    }

    @Test
    public void testgetDemoNames_Aspect(){
        List<String> names = demoService.getDemoNames();
        System.out.println(JSON.toJSONString(names));
    }

    @Test
    public void testgetResultDemoDtoList_Aspect(){
        ResultDto<List<DemoDto>> resultDemoDto = demoService.getResultDemoDtoList("1234567890123456789012345678901", "test@163.com");
        System.out.println(JSON.toJSONString(resultDemoDto));
    }
}
