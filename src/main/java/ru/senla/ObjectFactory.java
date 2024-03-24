package ru.senla;


import lombok.Setter;
import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

// так делали раньше
public class ObjectFactory {
    private final ApplicationContext context;
    private List<ObjectConfigurator> configurators = new ArrayList<>();


    // Map.of(Policeman.class, AngryPoliceman.class))); указываем что Policeman.class имеет больше одной имплементации,
    // поэтому указываем что используем имплементацию AngryPoliceman.class
    // По сути данная HashMap<>(Map.of(Policeman.class, AngryPoliceman.class))); должна строиться из внешнего
    // конфигурационного файла
   @SneakyThrows
    public ObjectFactory(ApplicationContext context) {
        this.context= context;

     //   config = new JavaConfig("ru.senla", new HashMap<>(Map.of(Policeman.class, PolicemanImpl.class)));
        // у конфига просим сканер, возьмем все подвиды наших ObjectConfigurator, по ним проитерируемся
        for (Class<? extends ObjectConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            // можно еще проверять, абстрактный ли класс мы получили
            // берем наш конфигуратор и добавляем в него
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }



    // в рантайме пришел объект типа T то возвращать тоже будем объект типа Т
    @SneakyThrows // для того что бы не обрабатывать exceptions
    public <T> T createObject(Class<T> implClass) {
       // T t = implClass.getDeclaredConstructor().newInstance();
        // создаем объект класса
        T t = create(implClass);

        // берем все наши конфигураторы, и просим каждый из них настроить наш объект t
        // настраиваем
        configure(t);

//        for (Method method : implClass.getMethods()) {
//            if(method.isAnnotationPresent(PostConstruct.class)) {
//                method.invoke(t); // запускаем метод именно на этом объекте t
//            }
//        }
        // запуск вторичных конструкторов класса
        invokeInit(implClass, t);

        return t;
    }

    private <T> void invokeInit(Class<T> implClass, T t) throws InvocationTargetException, IllegalAccessException {
        for (Method method : implClass.getMethods()) {
            if(method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(t); // запускаем метод именно на этом объекте t
            }
        }

    }
    private <T> void configure(T t) {
        configurators.forEach(objectConfigurator -> objectConfigurator.configure(t, context));
    }
    private <T> T create(Class<T> implClass) throws InstantiationError, IllegalAccessError, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
       return implClass.getDeclaredConstructor().newInstance();
    }
}
