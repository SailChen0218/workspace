package cn.com.servyou.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserDo {
    private String id;
    private String name;
    private String pwd;
}
