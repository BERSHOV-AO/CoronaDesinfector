package ru.senla;

public class CoronaDesinfector {

    public void start(Room room) {
        // todo сообщить всем присутствующим в комнате, о начале дезинфекции, попросить все свалить
        // todo разогнать всех ето не вышел после объявления
        desinfect(room);
        // todo сообщить всем присутствующим в комнате, что они могут вернуться обратно

    }

    private void desinfect(Room room) {
        System.out.println("зачитывается молитва: 'корона изыди'! - молитва прочитана, вирус извергнут в ад");
    }
}
