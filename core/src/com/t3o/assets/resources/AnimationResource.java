package com.t3o.assets.resources;

import com.badlogic.gdx.graphics.g2d.Animation;

public class AnimationResource {
    public String name;
    public float speed;
    public Animation.PlayMode playMode;


    public AnimationResource(String name,float speed, Animation.PlayMode playMode){
        set(name,speed,playMode);
    }

    public AnimationResource(){
        set("NULL",0f, Animation.PlayMode.NORMAL);
    }

    public void set(String name,float speed, Animation.PlayMode playMode){
        this.name = name;
        this.speed = speed;
        this.playMode = playMode;
    }
}
