// Copyright 2012 Baidu Inc. All rights reserved.

package com.jtools.common;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * @author Jian Hengyi (jianhengyi@baidu.com)
 */
public class LogMessageBuilder {

    private String message;

    private List<String> parameters = Lists.newArrayList();

    public LogMessageBuilder() {
    }

    public LogMessageBuilder(String message) {
        this.message = message;
    }

    public LogMessageBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public LogMessageBuilder withParameter(String name, Object value) {
        parameters.add(name + "=" + value);
        return this;
    }

    public String build() {
        Preconditions.checkNotNull(message);

        if (parameters.isEmpty()) {
            return message;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(ensureNotEndWithAlphanumeric(message));
            stringBuilder.append(" Parameters: ");
            stringBuilder.append(StringUtils.join(parameters, ","));
            return stringBuilder.toString();
        }
    }

    private String ensureNotEndWithAlphanumeric(String message) {
        message = StringUtils.trimToEmpty(message);
        if (StringUtils.isAlphanumeric(StringUtils.right(message, 1))) {
            message += ".";
        }
        return message;
    }

    @Override
    public String toString() {
        return build();
    }

}
