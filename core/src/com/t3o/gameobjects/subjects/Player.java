package com.t3o.gameobjects.subjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.ArrayMap;
import com.t3o.assets.resources.AnimationResource;

public class Player extends  Subject{
    static public final Vector2 SIZE = new Vector2(30,46);

    static public final AnimationResource[] ANIMATIONS = new AnimationResource[]{
            new AnimationResource("PLAYER_MOVE",0.6f, Animation.PlayMode.LOOP),
            new AnimationResource("PLAYER_IDLE",0.6f, Animation.PlayMode.LOOP),
            new AnimationResource("PLAYER_JUMP",0.6f, Animation.PlayMode.NORMAL),
    };


    public Player(Body body) {
        super(body);
    }

    @Override
    protected void set(){
        walkImpulse = 4f;
        maxVelocity = 12f;
        jumpImpulse = 32f;
    }


    @Override
    public String toString(){
        return "PLAYER";
    }



}
