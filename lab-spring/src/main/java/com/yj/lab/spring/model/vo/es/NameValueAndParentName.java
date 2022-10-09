package com.yj.lab.spring.model.vo.es;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NameValueAndParentName implements Serializable {

    private static final long serialVersionUID = 2439668390619490098L;
    private String name;
    private String value;
    private String parentName;
    private boolean ck;

    public NameValueAndParentName(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
