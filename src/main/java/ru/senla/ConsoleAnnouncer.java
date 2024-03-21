package ru.senla;

public class ConsoleAnnouncer implements Announcer {

    @InjectByType
    private Recommendator recomendator;

   // private Recommendator recomendator = ObjectFactory.getInstance().createObject(Recommendator.class);
    @Override
    public void announce(String message) {
        System.out.println(message);
        recomendator.recomend();
    }
}
