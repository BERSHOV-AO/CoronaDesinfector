package ru.senla;


import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

// так делали раньше
public class ObjectFactory {
    private static ObjectFactory ourInstance = new ObjectFactory();
 //   private Config config = new JavaConfig("ru.senla"); // пока хардкодим
    private Config config;
    public static ObjectFactory getInstance() {
        return ourInstance;
    }
    // Map.of(Policeman.class, AngryPoliceman.class))); указываем что Policeman.class имеет больше одной имплементации,
    // поэтому указываем что используем имплементацию AngryPoliceman.class
    // По сути данная HashMap<>(Map.of(Policeman.class, AngryPoliceman.class))); должна строиться из внешнего
    // конфигурационного файла
    private ObjectFactory() {
        config = new JavaConfig("ru.senla", new HashMap<>(Map.of(Policeman.class, PolicemanImpl.class)));
    }

    // пока не было дженериков было так
    // public Object objectObject(Class type);

    // в рантайме пришел объект типа T то возвращать тоже будем объект типа Т
    @SneakyThrows // для того что бы не обрабатывать exceptions
    public <T> T createObject(Class<T> type) {
        Class<? extends T> implClass = type; // вдруг это класс конкретный
        if(type.isInterface()) {
            implClass = config.getImplClass(type); // заменяем на конкретный тип
        }
        // уверены что у нас есть дефолтный конструктор
        T t = implClass.getDeclaredConstructor().newInstance();

        //todo настройка

        return t;
    }

}
