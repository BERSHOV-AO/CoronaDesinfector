package ru.senla;


import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

// так делали раньше
public class ObjectFactory {
    private static ObjectFactory ourInstance = new ObjectFactory();
    //   private Config config = new JavaConfig("ru.senla"); // пока хардкодим
    private List<ObjectConfigurator> configurators = new ArrayList<>();
    private Config config;

    public static ObjectFactory getInstance() {
        return ourInstance;
    }

    // Map.of(Policeman.class, AngryPoliceman.class))); указываем что Policeman.class имеет больше одной имплементации,
    // поэтому указываем что используем имплементацию AngryPoliceman.class
    // По сути данная HashMap<>(Map.of(Policeman.class, AngryPoliceman.class))); должна строиться из внешнего
    // конфигурационного файла
   @SneakyThrows
    private ObjectFactory() {
        config = new JavaConfig("ru.senla", new HashMap<>(Map.of(Policeman.class, PolicemanImpl.class)));
        // у конфига просим сканер, возьмем все подвиды наших ObjectConfigurator, по ним проитерируемся
        for (Class<? extends ObjectConfigurator> aClass : config.getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            // можно еще проверять, абстрактный ли класс мы получили
            // берем наш конфигуратор и добавляем в него
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    // пока не было дженериков было так
    // public Object objectObject(Class type);

    // в рантайме пришел объект типа T то возвращать тоже будем объект типа Т
    @SneakyThrows // для того что бы не обрабатывать exceptions
    public <T> T createObject(Class<T> type) {
        Class<? extends T> implClass = type; // вдруг это класс конкретный
        if (type.isInterface()) {
            implClass = config.getImplClass(type); // заменяем на конкретный тип
        }
        // уверены что у нас есть дефолтный конструктор
        T t = implClass.getDeclaredConstructor().newInstance();
        //todo настройка

        // берем у класса implClass все его поля, итерируемся,
//        for (Field field : implClass.getDeclaredFields()) {
//            // у каждого поля пытаемся считать аннотацию InjectProperty
//            InjectProperty annotation = field.getAnnotation(InjectProperty.class);
//
//            //------------------------------------------------------------------------
//            // временная реализация работы с application.properties
//            String path = ClassLoader.getSystemClassLoader().getResource("application.properties").getPath();
//            Stream<String> lines = new BufferedReader(new FileReader(path)).lines();
//            //split без пробелов
//            Map<String, String> propertiesMap =
//                    lines.map(line -> line.split("=")).collect(toMap(arr -> arr[0], arr -> arr[1]));


            //------------------------------------------------------------------------
            // делаем проверку что не получили сдесь null

//            if(annotation != null) {
//                String value;
//                // если в аннотации значение пустое, то тогда мы захотим вытаскивать из нашего application.properties
//                // значение поля value пустое, тогда мы идем в наш propertiesMap
//                if(annotation.value().isEmpty()) {
//                    // Вытягиваем из propertiesMap по названию поля(field)
//                    value = propertiesMap.get(field.getName());
//                }else {
//                    // если у нас properties не пустой, то берем из аннотации // если значение было, то его и берем
//                    value = propertiesMap.get(annotation.value());
//                }
//            }

//            if (annotation != null) {
//                String value =
//                        annotation.value().isEmpty() ? propertiesMap.get(field.getName()) : propertiesMap.get(annotation.value());
//                // таким образом мы берм значение которое нам нужно будет засетить, в наше поле (field)
//                // После вызова метода setAccessible(true), можно получить доступ к значению поля и изменить его,
//                // игнорируя модификаторы доступа
//                field.setAccessible(true);
//                // настройка объекта t, передаем ему значение
//                field.set(t, value);
//           }
//        }

        // берем все наши конфигураторы, и просим каждый из них настроить наш объект t
        configurators.forEach(objectConfigurator -> objectConfigurator.configure(t));
        return t;
    }

}
