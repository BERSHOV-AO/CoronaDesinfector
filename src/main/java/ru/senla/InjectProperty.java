package ru.senla;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface InjectProperty {
    String value() default "";  // возможно будет создать value для property, по default это будет пусто ""
}
