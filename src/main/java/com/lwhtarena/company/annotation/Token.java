package com.lwhtarena.company.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author：liwh
 * @Description:
 * @Date 12:13 2018/8/5
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {
    boolean ajax() default false;

    boolean admin() default false;

    boolean admin0() default false;

    boolean token() default false;

    boolean login() default false;

    boolean loginOrAdmin() default false;

    boolean role0() default false;

    boolean log() default false;

    String failedPage() default "";

    String msgKey() default "";

    String mark() default "";
}