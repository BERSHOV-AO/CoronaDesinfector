package ru.senla;

import java.util.HashMap;
import java.util.Map;

// это не бизнес логика
// в этом месте тоже можно создать через ObjectFactory.getInstance().createObject(CoronaDesinfector.class);
public class Main {
    public static void main(String[] args) {
   //     CoronaDesinfector desinfector = ObjectFactory.getInstance().createObject(CoronaDesinfector.class);
       ApplicationContext context = Application.run("ru.senla", new HashMap<>(Map.of(Policeman.class, PolicemanImpl.class)));
        CoronaDesinfector desinfector = context.getObject(CoronaDesinfector.class);
        desinfector.start(new Room());
    }
}
