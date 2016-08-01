package com.thejoyrun.aptpreferences;

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

    boolean save() default true;

    boolean commit() default false;

//    boolean force() default false;

    boolean preferences() default false;

    /**
     * 如果是true全局参数通过get(name1)或者get(name2)使用都是一样的,如果是false,那么字段名会带上name(name1_fieldName)
     * @return
     */
    boolean global() default true;
}