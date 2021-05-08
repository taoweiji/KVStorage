package com.taoweiji.aptpreferences;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解说明该方法是通过commit实时提交的
 * Created by Wiki on 16/7/15.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AptField {
    boolean commit() default false;
    /**
     * 如果是true，那么该字段就会忽略GroupId
     */
    boolean ignoreGroupId() default false;

    boolean preferences() default false;

    boolean save() default true;
}