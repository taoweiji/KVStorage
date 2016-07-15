package com.thejoyrun.aptpreferences;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解说明该方法是通过commit实时提交的
 * Created by Wiki on 16/7/15.
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface AptField {

    boolean serialize() default true;

    boolean deserialize() default true;

    boolean commit() default false;

    boolean force() default false;


}