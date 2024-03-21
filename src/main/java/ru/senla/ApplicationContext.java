package ru.senla;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// класс контекст, у него будет доступ к ObjectFactory
public class ApplicationContext {

    @Setter
    private ObjectFactory factory;
    // у него будет map, в которой, будут закешированы все объекты, которые были созданы, которые будут singleton
    // Class - это интерфейс
    private Map<Class, Object> cache = new ConcurrentHashMap<>();
    @Getter
    private Config config;

    public ApplicationContext(Config config) {
        this.config = config;

    }

    // будет метод, который будет возвращать тип Т
    public <T> T getObject(Class<T> type) {
        // Если наш cache, уже содержит в себе этот type
        if (cache.containsKey(type)) {
            // возвращаем из кеша
            return (T) cache.get(type);
        }

        Class<? extends T> implClass = type; // вдруг это класс конкретный

        // проверяем, если это интерфейс, то простим имплементацию у конфига
        if (type.isInterface()) {
            implClass = config.getImplClass(type); // заменяем на конкретный тип
        }
        // после мы все таки просим фабрику создать объект этой имплементации
        T t = factory.createObject(implClass);
        // проверяем, не надо ли наш объект положить в кеш
        // проверяем если implClass у него есть аннотация Singleton
        if (implClass.isAnnotationPresent(Singleton.class)) {
            // тогда складываем его в кеш, по type
            cache.put(type, t);
        }
        return t;
    }

}
