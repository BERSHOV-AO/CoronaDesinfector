package ru.senla;


// так делали раньше
public class ObjectFactory {
    private static ObjectFactory ourInstance = new ObjectFactory();
    private Config config;
    public static ObjectFactory getInstance() {
        return ourInstance;
    }
    private ObjectFactory() {
    }

    // пока не было дженериков было так
    // public Object objectObject(Class type);

    // в рантайме пришел объект типа T то возвращать тоже будем объект типа Т
    public <T> T createObject(Class<T> type) {
        Class<? extends T> implClass = type; // вдруг это класс конкретный
        if(type.isInterface()) {
            implClass = config.getImplClass(type); // заменяем на конкретный тип
        }
    }

}
