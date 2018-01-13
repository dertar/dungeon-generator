package com.t3o.utils;

import com.badlogic.gdx.graphics.Color;

public class LightContainer extends Coordinates {

    public Color color;
    public float pxDistMin;
    public float pxDistMax;
    public int rays;


    public LightContainer(int x, int y,final Color color,float pxDistMin,float pxDistMax,int rays) {
        super(x,y);
        set(color,pxDistMin,pxDistMax,rays);
    }

    void set(final Color color,float pxDistMin,float pxDistMax,int rays){
        this.color = color;
        this.pxDistMin = pxDistMin;
        this.pxDistMax = pxDistMax;
        this.rays = rays;
    }

    public LightContainer(Coordinates coor,final Color color,float pxDistMin,float pxDistMax) {
        super(coor);
        set(color,pxDistMin,pxDistMax,24);
    }

    public Color getColor(){
        return color;
    }
}
