package com.yj.lab.spring.model.vo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1228449926833134845L;

    private String firstname;
    private String itCode;
    private String displayname;
    private String telephone;
    private String department;
    private String email;
    private String lastname;
    private String token; // 用户的登录凭证
    private String status;
}
