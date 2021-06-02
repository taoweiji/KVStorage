package com.taoweiji.aptpreferences;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Wiki on 16/7/15.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AptField {
    /**
     * 默认是false，apply提交
     */
    boolean commit() default false;

    /**
     * 如果是true，那么该字段就会忽略GroupId，可以用于实现多用户登录的场景，部分字段需要区分用户发、设置为false；部分字段无需区分用户，是全局参数，那么就是true
     */
    boolean ignoreGroupId() default false;

    /**
     * 如果是true，就是忽略这个字段
     */
    boolean ignore() default false;
}