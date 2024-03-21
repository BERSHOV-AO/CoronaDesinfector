package ru.senla;

import lombok.SneakyThrows;

import java.lang.reflect.Field;

// в данном классе использование ObjectFactory.getInstance().createObject(field.getType()); это норма

public class InjectByTypeAnnotationObjectConfigurator implements ObjectConfigurator {
    @Override
    @SneakyThrows
    public void configure(Object t, ApplicationContext context) {
        for (Field field :  t.getClass().getDeclaredFields()){
            if (field.isAnnotationPresent(InjectByType.class)) {
                // field.getType()
                field.setAccessible(true);
                //Object object = ObjectFactory.getInstance().createObject(field.getType());
                Object object = context.getObject(field.getType());
                field.set(t, object);
            }
        }
    }
}
