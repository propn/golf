package com.golf.dao.anno;

import java.lang.annotation.Inherited;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE) 
@Retention(RUNTIME)
@Inherited
public @interface Entity {

    /** The name of an entity. Defaults to the unqualified 
     * name of the entity class. This name is used to
     * refer to the entity in queries. The name must not be 
     * a reserved literal in the Java Persistence query language.  */
    String name() default "";
}
