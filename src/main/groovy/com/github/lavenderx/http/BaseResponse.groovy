package com.github.lavenderx.http

import groovy.transform.TypeChecked
import org.apache.commons.lang3.builder.ToStringBuilder

/**
 * Created by lavenderx on 2016-05-05.
 */
@TypeChecked
abstract class BaseResponse implements Serializable {

    String status, message

    @Override
    String toString() {
        ToStringBuilder.reflectionToString(this)
    }
}
