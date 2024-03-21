package ru.senla;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// класс контекст, у него будет доступ к ObjectFactory
public class ApplicationContext {

    private ObjectFactory factory;
    // у него будет map, в которой, будут закешированы все объекты, которые были созданы, которые будут singleton
    private Map<Class, Object> cache = new ConcurrentHashMap<>();

    // будет метод, который будет возвращать тип Т

    public <T> T getObject(Class<T> type) {
        return null;
    }

}
