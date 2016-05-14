package com.github.lavenderx.model.entity;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

/**
 * Created by lavenderx on 2016-05-05.
 */
@Data
public class UserAuthentication {

    private String username;
    private String password;
    private List<String> roleList;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
