package ru.senla;


import lombok.SneakyThrows;

// так делали раньше
public class ObjectFactory {
    private static ObjectFactory ourInstance = new ObjectFactory();
    private Config config = new JavaConfig("ru.senla"); // пока хардкодим
    public static ObjectFactory getInstance() {
        return ourInstance;
    }
    private ObjectFactory() {
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

        //todo

        return t;
    }

}
