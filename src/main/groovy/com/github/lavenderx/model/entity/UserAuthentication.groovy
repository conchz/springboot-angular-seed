package com.github.lavenderx.model.entity

import org.apache.commons.lang3.builder.ToStringBuilder

/**
 * Created by lavenderx on 2016-05-01.
 */
class UserAuthentication {

    String username, password

    List<String> roleList

    @Override
    String toString() {
        ToStringBuilder.reflectionToString(this)
    }
}
