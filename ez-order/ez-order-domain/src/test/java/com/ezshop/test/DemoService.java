package com.ezshop.test;

import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.List;

public interface DemoService {
    DemoDto getDemoDto(@ApiParam(name = "djxh", value = "登记序号")
                       @Length(min = 31, max = 31, message = "[E0003,{登记序号},{31}]")
                               String djxh,
                       @ApiParam(name = "email", value = "电子邮件")
                       @Email(message = "email格式不符合要求。")
                               String email);

    void updateDemoDto(@Valid DemoDto demoDto);

    boolean isNsrxxNormal();

    List<DemoDto> listDemoDto(String djxh);

    List<DemoDto> listDemoDtoWithAuth();

    ResultDto<DemoDto> getResultDemoDto(@ApiParam(name = "djxh", value = "登记序号")
                                        @Length(min = 31, max = 31, message = "djxh长度必须为{min}位。")
                                                String djxh,
                                        @ApiParam(name = "email", value = "电子邮件")
                                        @Email(message = "email格式不符合要求。")
                                                String email);

    ResultDto<?> getResultDemoDtoList(@ApiParam(name = "djxh", value = "登记序号")
                                      @Length(min = 31, max = 31, message = "djxh长度必须为{min}位。")
                                              String djxh,
                                      @ApiParam(name = "email", value = "电子邮件")
                                      @Email(message = "email格式不符合要求。")
                                              String email);
}
