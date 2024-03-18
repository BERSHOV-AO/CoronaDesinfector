package ru.senla;

public interface Config {

    // конфиг должен получить интерфейс в аргументы, а вернуть имплементацию этого интерфейса
    <T> Class<? extends T> getImplClass(Class<T> ifc);
}
