package com.t3o.utils;

import com.badlogic.gdx.math.Vector2;

public class Box2DScale {

    static final public float PPM = 10f;

    static public float toBox2D(float pixels){
        return pixels / PPM;
    }

    static public float toPixels(float box){
        return box * PPM;
    }

    static public Vector2 toPixels(Vector2 box){
        return new Vector2(toPixels(box.x),toPixels(box.y));
    }

}
