package ru.senla;

import javax.annotation.PostConstruct;

public class PolicemanImpl implements Policeman {

    @InjectByType
    private Recommendator recommendator;

    // вторичный конструктор

    @PostConstruct
    public void init() {
        System.out.println(recommendator.getClass());
    }
    @Override
    public void makePeopleLeaveRoom() {
        System.out.println("пив пав, бах бах, кыш, кыш");
    }
}
