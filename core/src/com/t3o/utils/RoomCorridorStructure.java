package com.t3o.utils;

public class RoomCorridorStructure {
    public int roomA;
    public Coordinates A;

    public int roomB;
    public Coordinates B;

    public int distance;


    public RoomCorridorStructure() {

    }

    public RoomCorridorStructure(int roomA, Coordinates a, int roomB, Coordinates b, int distance) {
        this.roomA = roomA;
        A = a;
        this.roomB = roomB;
        B = b;
        this.distance = distance;
    }
}
