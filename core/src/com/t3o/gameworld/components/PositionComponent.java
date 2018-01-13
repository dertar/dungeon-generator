package com.t3o.gameworld.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

//position: Z - используется для слоев
public class PositionComponent implements Component{
    public Vector3 position = null;
    public Vector2 scale = null;
    public float rotation = 0f;

    public PositionComponent(Vector3 position, Vector2 scale, float rotation) {
        set(position,scale,rotation);
    }

    public PositionComponent(float x, float y){
        set(new Vector3(x,y,0),new Vector2(1.0f,1.0f),0f);
    }
    public PositionComponent(float x, float y,float layer){
        set(new Vector3(x,y,layer),new Vector2(1.0f,1.0f),0f);
    }

    public PositionComponent(float x, float y,float layer,float scale){
        set(new Vector3(x,y,layer),new Vector2(scale,scale),0f);
    }

    public PositionComponent(Vector2 pos, float layer){
        set(new Vector3(pos.x,pos.y,layer),new Vector2(1f,1f),0f);

    }


    public PositionComponent(){
        set(new Vector3(),new Vector2(1.0f,1.0f),0f);
    }

    private void set(Vector3 position, Vector2 scale, float rotation) {
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }

}
