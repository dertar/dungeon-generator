package com.t3o.gameworld.components;

import com.badlogic.ashley.core.Component;

public class ColorBatchComponent implements Component{
    public float r = 1;
    public float g = 1;
    public float b = 1;
    public float alpha = 1;

    public ColorBatchComponent(float r, float g, float b, float alpha) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.alpha = alpha;
    }
    public ColorBatchComponent(){

    }
}
