package com.t3o.assets.resources;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.t3o.map.Pattern;
import com.t3o.utils.Coordinates;

public class DecorationResource {
    public boolean canMirror;
    public boolean isNear;

    public Vector2 light;
    public Vector2 offset;

    public AnimationResource animation;
    public Color color;

    public int pxDistLightMin;
    public int pxDistLightMax;

    public boolean torch;

    public float minScale;
    public float maxScale;

    public Pattern pattern;
    public Vector2 offsetPattern;

    public boolean isOnBg;
    public int rays;

    public boolean isRoof;

    public DecorationResource(){
        set(null,null,null,null,0,0,false,false);
    }

    public DecorationResource(final AnimationResource animation, final Color color,final Vector2 pos,final Vector2 offset,int minDist,int maxDist,boolean canMirror,boolean isNear){
        set(animation,color,pos,offset,minDist,maxDist,canMirror,isNear);
    }

    public void set(final AnimationResource animation,final Color color, final Vector2 pos,final Vector2 offset,int minDist,int maxDist,boolean canMirror,boolean isNear){
        this.animation = animation;
        light = pos;
        this.canMirror = canMirror;
        this.isNear = isNear;
        this.offset = offset;
        this.color = color;
        pxDistLightMax = maxDist;
        pxDistLightMin = minDist;
        torch = false;

        minScale = maxScale = 1.0f;

        pattern = null;
        offsetPattern = null;
        isOnBg = false;
        rays = 24;
        isRoof = false;
    }
}
