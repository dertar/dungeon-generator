package com.t3o.utils;

import com.t3o.gameobjects.userdata.UserData;

public class ShapeRect {
    public Coordinates xy;
    public Coordinates size;

    private UserData data;

    public ShapeRect() {
        xy = new Coordinates(0,0);
        size = new Coordinates(0,0);
    }

    public ShapeRect(int x,int y, int width,int height){
        this();
        setXY(x,y);
        setSize(width,height);
    }

    @Override
    public String toString(){
        return "(" + xy.x + "," + xy.y + "," + size.x + "," + size.y + ")";
    }

    @Override
    public boolean equals(Object a){
        ShapeRect tmp = (ShapeRect)a;
        return xy.equals(tmp.xy) && size.equals(tmp.size);
    }

    public void setXY(int x, int y) {
        this.xy.x = x;
        this.xy.y = y;
    }

    public void setSize(int width, int height) {
        size.x = width;
        size.y = height;
    }

    public void setData(UserData.TYPE type){
        if(data == null) {
            data = new UserData(type);
        }else{
            data.set(type);
        }
    }

    public UserData getData(){
        return data;
    }
}
