// Copyright 2018 Baidu Inc. All rights reserved.
package com.jtools.condition;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;

/**
 * The MyProfile.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(MyProfileCondition.class)
public @interface MyProfile {


    /**
     * The set of profiles for which the annotated component should be registered.
     */
    String[] value();

}
