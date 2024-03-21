package ru.senla;

public class CoronaDesinfector {

//    private Announcer announcer = ObjectFactory.getInstance().createObject(Announcer.class);
//    private Policeman policeman = ObjectFactory.getInstance().createObject(Policeman.class);

    @InjectByType
    private Announcer announcer;
    @InjectByType
    private Policeman policeman;

    public void start(Room room) {
        // желательно Announcer(диктор) делать интерфейсом,
        // по тому что для разных клиентов он может имплементирован по разному
        announcer.announce("Начинаем дезинфекцию, все вон!");
        policeman.makePeopleLeaveRoom();
        desinfect(room);
        announcer.announce("Рискните зайти обратно!");
    }

    private void desinfect(Room room) {
        System.out.println("зачитывается молитва: 'корона изыди'! - молитва прочитана, вирус извергнут в ад");
    }
}
