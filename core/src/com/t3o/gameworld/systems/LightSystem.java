package com.t3o.gameworld.systems;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.t3o.gameworld.components.LightComponent;
import com.t3o.utils.Box2DScale;

public class LightSystem extends IteratingSystem{
    RayHandler rayHandler;
    Camera camera;

    ComponentMapper<LightComponent> lightMapper;

    Array<Entity> entities;


    public LightSystem(final World world,final Camera camera) {
        super(Family.all(LightComponent.class).get());

        this.camera = camera;

        entities = new Array<Entity>();
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.3f);

        rayHandler.setCombinedMatrix(camera.combined);
        //rayHandler.useCustomViewport(0, 0, (int)Ratio.getScreen("width"),(int)Ratio.getScreen("height"));
        lightMapper = ComponentMapper.getFor(LightComponent.class);


    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        //rayHandler.setCombinedMatrix(camera.combined);

        rayHandler.setCombinedMatrix(camera.combined.cpy().scl(Box2DScale.PPM));

        for(Entity it : entities){


            LightComponent light = lightMapper.get(it);

            if(light.minDist != light.maxDist) {
                float distance = light.light.getDistance();

                if (distance > light.maxDist) {
                    light.force = false;
                } else if (distance < light.minDist) {
                    light.force = true;
                }

                light.light.setDistance(distance + (light.force ? deltaTime  : -deltaTime ));
            }



        }

        rayHandler.updateAndRender();

        entities.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        entities.add(entity);
    }

    public void dispose(){
        rayHandler.dispose();
    }

    public RayHandler getRayHandler(){
        return rayHandler;
    }

    public Light createPlayerLight(Body body,int distPixels){
        return createPointLight(rayHandler,distPixels,Color.BROWN,body,1.6f,2.4f,
                PhysicsSystem.BIT_PASSIVE,PhysicsSystem.BIT_PLATFROMS,false,24);
    }



    public Light createLight(Body body,int distPixels,final Color c,int rays){
        return createPointLight(rayHandler,distPixels,c,
                body,0f,0f,PhysicsSystem.BIT_PLATFROMS,PhysicsSystem.BIT_PLATFROMS,
                false,rays);
    }

    PointLight createPointLight(RayHandler rayHandler, int distPixels, Color color,Body body,float offsetX,float offsetY,
                                short catMask,short bitMask,boolean isStatic,int rays){
        PointLight ret = new PointLight(rayHandler,rays,color,Box2DScale.toBox2D(distPixels),0,0);

        ret.attachToBody(body,offsetX,offsetY);
        ret.setXray(true);

        ret.setSoft(false);
        ret.setContactFilter(catMask,(short)0,bitMask);

        ret.setStaticLight(isStatic);


        return ret;
    }

    public void clear(){
        rayHandler.removeAll();
    }
}
