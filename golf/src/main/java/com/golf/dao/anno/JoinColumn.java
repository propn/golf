/*
 * Copyright (C) 2012 The Golf Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 */
package com.golf.dao.anno;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Thunder.Hsu
 * 2012-12-6
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)

public @interface JoinColumn {

    /** 
     * (Optional) The name of the foreign key column.
     * The table in which it is found depends upon the
     * context. If the join is for a OneToOne or Many-
     * ToOne mapping, the foreign key column is in the
     * table of the source entity. If the join is for a
     * ManyToMany, the foreign key is in a join table.
     *
     * Default (only applies if a single join column is used):
     * The concatenation of the following: the name of the 
     * referencing relationship property or field of the referencing 
     * entity; "_"; the name of the referenced primary key column. 
     * If there is no such referencing relationship property or 
     * field in the entity, the join column name is formed as the 
     * concatenation of the following: the name of the entity; "_"; 
     * the name of the referenced primary key column.
     */
    String name() default "";

    /**
     * (Optional) The name of the column referenced
     * by this foreign key column. When used with
     * relationship mappings, the referenced column is
     * in the table of the target entity. When used inside
     * a JoinTable annotation, the referenced key column
     * is in the entity table of the owning entity, or
     * inverse entity if the join is part of the inverse join
     * definition.
     *
     * Default (only applies if single join column is being 
     * used): The same name as the primary key column of the 
     * referenced table.
     */
    String referencedColumnName() default "";

    /**
     * (Optional) Whether the property is a unique key.
     * This is a shortcut for the UniqueConstraint annotation
     * at the table level and is useful for when the
     * unique key constraint is only a single field. It is
     * not necessary to explicitly specify this for a join
     * column that corresponds to a primary key that is
     * part of a foreign key.
     */
    boolean unique() default false;

    /** (Optional) Whether the foreign key column is nullable. */
    boolean nullable() default true;

    /**
     * (Optional) Whether the column is included in
     * SQL INSERT statements generated by the persistence
     * provider.
     */
    boolean insertable() default true;

    /**
     * (Optional) Whether the column is included in
     * SQL UPDATE statements generated by the persistence
     * provider.
     */
    boolean updatable() default true;

    /**
     * (Optional) The SQL fragment that is used when
     * generating the DDL for the column.
     * <p> Defaults to the generated SQL for the column.
     */
    String columnDefinition() default "";

    /**
     * (Optional) The name of the table that contains
     * the column. If a table is not specified, the column
     * is assumed to be in the primary table of the
     * applicable entity.
     */
    String table() default "";
}