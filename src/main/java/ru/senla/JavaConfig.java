package ru.senla;

import lombok.Getter;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

public class JavaConfig implements Config {

    // в большинстве случаев, имплементация у интерфейсов, будет одна
    // скорее всего при сборке проекта буде какой то jar, в котором будут лежать имплементации,
    // и имплементация для каждого интерфейса, чаще всего будет одна,

    // если кото то захочет сам взять сканер и поискать, что нибудь свое, мы сможем этот сканер отдать
    @Getter
    private Reflections scanner;

    // map для хранения имплементаций
    private Map<Class, Class> ifc2ImplClass;

    // передаем пакет который надо просканировать, а потом этот сканер будет отвечать на вопросы
    public JavaConfig(String packageToScan, Map<Class, Class> ifc2ImplClass) {
        this.scanner = new Reflections(packageToScan);
        this.ifc2ImplClass = ifc2ImplClass;
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {

        // метод, в котором запускается лямда, года в map нет ничего
        // computeIfAbsent принимает ключ ifc, если ключ ifc существует, то он возвращает значение для этого ключа

        // если ключа нет, он запускает эту лямду aClass ->  {}, лмда возвращает значение,
        // это значение засетится по ключу в map, и будет возвращено значение
        return ifc2ImplClass.computeIfAbsent(ifc, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc); // сканер дай все подвиды данного класса
            if (classes.size() != 1) { // если не одно имплементация данного интерфейса, то выбрасываем исключение
                throw new RuntimeException(ifc + " has 0 or more then one impl please update your config");
            }
            // берем класс который мы нашли(имплементацию), iterator() - по тому что set, берем next/
            return classes.iterator().next();
        });


    }
}
