package com.t3o.assets.resources;

import com.badlogic.gdx.math.Vector2;

public class StaticDecorationResource {
    public boolean canMirror;
    public boolean isNear;

    public String textureName;
    public Vector2 offset;

    public float maxScale;
    public float minScale;

    public boolean isFloor;

    //public Pattern pattern;

    public StaticDecorationResource(){
        set(null,null,1f,1f,false,false);
    }

    public StaticDecorationResource(String textureName, final Vector2 offset, boolean canMirror, boolean isNear){
        set(textureName,offset,1f,1f,canMirror,isNear);
    }

    public StaticDecorationResource(String textureName, final Vector2 offset, float minScale,float maxScale, boolean canMirror, boolean isNear){
        set(textureName,offset,minScale,maxScale,canMirror,isNear);
    }


    private void set(String textureName, final Vector2 offset,float minScale,float maxScale, boolean canMirror, boolean isNear){
        this.textureName = textureName;
        this.offset = offset;

        this.canMirror = canMirror;
        this.isNear = isNear;

        this.minScale = minScale;
        this.maxScale = maxScale;

        isFloor = true;
    }

}
