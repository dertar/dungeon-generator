package com.t3o.gameworld.components;

import com.badlogic.ashley.core.Component;

public class AnimationComponent implements Component {

    public String animations;
    public float stateTime;


    public AnimationComponent(){

    }

    public AnimationComponent(String animations){
        set(animations,0f);
    }

    public AnimationComponent(String animations,float stateTime){
        set(animations,stateTime);
    }

    private void set(String animations,float stateTime){
        this.animations = animations;
        this.stateTime = stateTime;
    }

    public void delta(float delta){
        stateTime += delta;
    }

    public void wipeStateTime(boolean wipe){
        if(wipe) stateTime = 0;
    }

    public float getStateTime(){
        return stateTime;
    }
}
