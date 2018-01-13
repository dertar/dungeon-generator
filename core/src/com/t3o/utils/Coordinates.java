package com.t3o.utils;


public class Coordinates{
    public int x;
    public int y;


    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(Coordinates coor) {
        this.x = coor.x;
        this.y = coor.y;
    }

    @Override
    public boolean equals(Object a){
        Coordinates coord = (Coordinates)a;
        return x == coord.x && y == coord.y;
    }

    @Override
    public String toString(){
        return "(" + x + "," + y +")";
    }

    public void set(Coordinates another){
        x = another.x;
        y = another.y;
    }

}