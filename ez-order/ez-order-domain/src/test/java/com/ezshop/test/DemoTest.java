package com.ezshop.test;

import com.OrderDomainBootstrap;
import com.alibaba.fastjson.JSON;
import com.ezshop.desensitize.DesensitizeMethodHolder;
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
    DemoServiceImpl demoService;

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
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        ExecutableValidator executableValidator = factory.getValidator().forExecutables();
//
//        ConstraintMapping constraintMapping = configuration.createConstraintMapping();
//
//

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

        Validator validator = validatorFactory.getValidator();
        BeanDescriptor carDescriptor = validator.getConstraintsForClass( DemoServiceImpl.class );
        Set<MethodDescriptor> methodDescriptorSet = carDescriptor.getConstrainedMethods(MethodType.NON_GETTER);
        for (MethodDescriptor methodDescriptor: methodDescriptorSet){
            methodDescriptor.getConstraintDescriptors();
        }
//
//        ConstraintMapping constraintMapping = configuration.createConstraintMapping();
//        constraintMapping.constraintDefinition()
//
//                .type( Car.class )
//                .property( "manufacturer", FIELD )
//                .constraint( new NotNullDef() )
//                .property( "licensePlate", FIELD )
//                .ignoreAnnotations( true )
//                .constraint( new NotNullDef() )
//                .constraint( new SizeDef().min( 2 ).max( 14 ) )
//                .type( RentalCar.class )
//                .property( "rentalStation", METHOD )
//                .constraint( new NotNullDef() );
//
//        Validator validator = configuration.addMapping( constraintMapping )
//                .buildValidatorFactory()
//                .getValidator();
//        DemoService demoService = new DemoService();
//        Class<?> clazz = demoService.getClass();
//        try {
//            Method method = clazz.getDeclaredMethod("getDemoDto", String.class, String.class);
//            Object[] parameterValues = {"djxh", "email"};
//            Set<ConstraintViolation<DemoService>> violations = executableValidator
//                    .validateParameters(demoService, method, parameterValues);
//            for (ConstraintViolation<DemoService> constraintViolation:violations){
//                System.out.println(constraintViolation.getMessage());
//            }
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void testValidator_updateDemoDto() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        ExecutableValidator executableValidator = factory.getValidator().forExecutables();

        DemoServiceImpl demoService = new DemoServiceImpl();
        Class<?> clazz = demoService.getClass();
        try {
            Method method = clazz.getDeclaredMethod("updateDemoDto", DemoDto.class);
            DemoDto demoDto = new DemoDto();
            demoDto.setDjxh("djxh");
            demoDto.setEmail("email");
            Object[] parameterValues = {demoDto};
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
    public void testlistDemoDto_Aspect(){
        demoService.listDemoDto("djxh123123123123");
    }

    @Test
    public void testgetDemoDto_Aspect(){
        DemoDto demoDto = demoService.getDemoDto("1234567890123456789012345678901", "test@163.com");
        desensitizeMethodHolder.findMethodByName("test");
        System.out.println(JSON.toJSONString(demoDto));
    }

    @Test
    public void testgetResultDemoDto_Aspect(){
        ResultDto<DemoDto> resultDemoDto = demoService.getResultDemoDto("1234567890123456789012345678901", "test@163.com");
        System.out.println(JSON.toJSONString(resultDemoDto));
    }

    @Test
    public void testgetResultDemoDtoList_Aspect(){
        ResultDto<List<DemoDto>> resultDemoDto = demoService.getResultDemoDtoList("1234567890123456789012345678901", "test@163.com");
        System.out.println(JSON.toJSONString(resultDemoDto));
    }

//    public static void main(String[] args) {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        ExecutableValidator executableValidator = factory.getValidator().forExecutables();
//
//        DemoService demoService = new DemoService();
//        Class<?> clazz = demoService.getClass();
//        try {
//            Method method = clazz.getDeclaredMethod("getDemoDto", String.class, String.class);
//            Object[] parameterValues = {"djxh", "email"};
//            Set<ConstraintViolation<DemoService>> violations = executableValidator
//                    .validateParameters(demoService, method, parameterValues);
//            System.out.println("finish.");
//
////
////            Class<?>[] parameterTypes = method.getParameterTypes();
////            Annotation[][] annotations = method.getParameterAnnotations();
////            for (int i = 0; i < annotations.length; i++) {
////                if (annotations[i].length > 0) {
////                    if (annotations[i][0].annotationType().isAnnotationPresent(Constraint.class)) {
////                        System.out.println("");
////                    }
////                }
////            }
////            method.getParameterAnnotations();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//    }
}
