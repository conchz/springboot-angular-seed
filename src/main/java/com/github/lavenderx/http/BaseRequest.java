package com.github.lavenderx.http;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by lavenderx on 2016-04-30.
 */
public abstract class BaseRequest implements Serializable {

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
