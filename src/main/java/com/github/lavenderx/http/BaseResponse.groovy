package com.github.lavenderx.http

import org.apache.commons.lang3.builder.ToStringBuilder

/**
 * Created by lavenderx on 2016-04-30.
 */
class BaseResponse implements Serializable {

    String status, message

    @Override
    String toString() {
        ToStringBuilder.reflectionToString(this)
    }
}
