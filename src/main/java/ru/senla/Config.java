package ru.senla;

import org.reflections.Reflections;

public interface Config {

    // конфиг должен получить интерфейс в аргументы, а вернуть имплементацию этого интерфейса
    <T> Class<? extends T> getImplClass(Class<T> ifc);

    Reflections getScanner();
}
