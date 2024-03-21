package ru.senla;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


// нужно, что бы наша структура Кеширвала все объекты которые сказали, что они Singleton
@Retention(RetentionPolicy.RUNTIME)
public @interface Singleton {
}
