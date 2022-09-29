package com.epam.atlab2022cw16.ui.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
public @interface JiraTicketsLink {
    int[] id();

    String description();

    String[] url() default "";
}