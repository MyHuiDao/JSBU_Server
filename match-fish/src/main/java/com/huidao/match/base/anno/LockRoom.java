package com.huidao.match.base.anno;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({METHOD,ElementType.TYPE})
@Retention(RUNTIME)
public @interface LockRoom {

}
