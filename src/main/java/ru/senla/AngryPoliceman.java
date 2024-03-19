package ru.senla;

public class AngryPoliceman implements Policeman {
    @Override
    public void makePeopleLeaveRoom() {
        System.out.println("Всех убью!");
    }
}
