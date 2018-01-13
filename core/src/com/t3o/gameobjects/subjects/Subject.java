package com.t3o.gameobjects.subjects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.ArrayMap;
import com.t3o.gameworld.components.*;

import java.util.AbstractCollection;

abstract public class Subject {
    public enum STATE {
        IDLE,ATTACK,MOVE,JUMP,DEATH
    }


    private Entity entity = null;

    private BodyComponent bodyComponent = null;
    private TextureComponent textureComponent = null;
    private PositionComponent positionComponent = null;
    private AnimationComponent animationComponent = null;
    private ColorBatchComponent colorBatchComponent = null;

    protected float maxVelocity;
    protected float walkImpulse;
    protected float jumpImpulse;

    public Subject(Body body){
        bodyComponent = new BodyComponent(body);
        textureComponent = new TextureComponent();
        positionComponent = new PositionComponent(body.getPosition(),1);
        animationComponent = new AnimationComponent();
        colorBatchComponent = new ColorBatchComponent();

        bodyComponent.body.setFixedRotation(true);
        bodyComponent.body.setBullet(true);


        /*MassData massData = new MassData();
        massData.mass = 100f;
        bodyComponent.body.setMassData(massData);
        Gdx.app.log("mass",body.getMass() + "");*/

        entity = new Entity();
        entity.add(bodyComponent).add(textureComponent).add(positionComponent).add(animationComponent).add(colorBatchComponent);

        set();
    }


    public Entity getEntity(){
        return entity;
    }

    public Vector2 getPosition(){
        return bodyComponent.body.getPosition();
    }

    public Body getBody(){
        return bodyComponent.body;
    }

    public float getMaxVelocity(){
        return maxVelocity;
    }

    public float getWalkImpulse(){
        return walkImpulse;
    }

    public float getJumpImpulse(){
        return jumpImpulse;
    }


    @Override
    abstract public String toString();
    abstract protected void set();


    public Vector2 SIZE;
}
