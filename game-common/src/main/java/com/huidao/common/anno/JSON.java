package com.huidao.common.anno;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({METHOD,ElementType.TYPE})
@Retention(RUNTIME)
public @interface JSON {
	Class<?>[] clazz() default {};
	String [] property() default {};
	String [] filterProperty() default {};
}
