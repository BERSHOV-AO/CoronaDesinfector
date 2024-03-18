package ru.senla;

public class CoronaDesinfector {

    public void start(Room room) {

        // желательно Announcer(диктор) делать интерфейсом, по тому что для разных клиентов он может имплементирован по разному
        private Announcer announcer;

        announcer.announce("Начинаем дезинфекцию, все вон!");
        policeman.makePeopleLeaveRoom();
        // todo разогнать всех ето не вышел после объявления
        desinfect(room);
        announcer.announce("Рискните зайти обратно!");

    }

    private void desinfect(Room room) {
        System.out.println("зачитывается молитва: 'корона изыди'! - молитва прочитана, вирус извергнут в ад");
    }
}
