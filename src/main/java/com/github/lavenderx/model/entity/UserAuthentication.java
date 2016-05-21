package com.github.lavenderx.model.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by lavenderx on 2016-05-05.
 */
@Data
public class UserAuthentication {

    private String username;
    private String password;
    private List<String> roleList;
}
