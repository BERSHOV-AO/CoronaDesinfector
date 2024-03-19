package ru.senla;

import org.reflections.Reflections;

import java.util.Set;

public class JavaConfig implements Config {

    // в большинстве случаев, имплементация у интерфейсов, будет одна
    // скорее всего при сборке проекта буде какой то jar, в котором будут лежать имплементации,
    // и имплементация для каждого интерфейса, чаще всего будет одна,
    private Reflections scanner;

    // передаем пакет который надо просканировать, а потом этот сканер будет отвечать на вопросы
    public JavaConfig(String packageToScan) {
        this.scanner = new Reflections(packageToScan);
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {

        Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc); // сканер дай все подвиды данного класса
        if(classes.size() != 1) { // если не одно имплементация данного интерфейса, то выбрасываем исключение
            throw new RuntimeException(ifc+" has 0 or more then one impl");
        }
        // берем класс который мы нашли(имплементацию), iterator() - по тому что set, берем next/
        return classes.iterator().next();
    }
}
