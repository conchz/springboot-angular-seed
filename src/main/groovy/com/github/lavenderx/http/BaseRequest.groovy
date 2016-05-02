package com.github.lavenderx.http

import org.apache.commons.lang3.builder.ToStringBuilder

/**
 * Created by lavenderx on 2016-04-30.
 */
abstract class BaseRequest implements Serializable {

    @Override
    public String toString() {
        ToStringBuilder.reflectionToString(this)
    }
}
