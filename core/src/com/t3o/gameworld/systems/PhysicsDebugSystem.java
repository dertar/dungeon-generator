package com.t3o.gameworld.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.t3o.utils.Box2DScale;

public class PhysicsDebugSystem extends IteratingSystem{
    private Box2DDebugRenderer debugRenderer;
    private World world;
    private OrthographicCamera camera;
    private Matrix4 scaling;
    public PhysicsDebugSystem(World world,OrthographicCamera camera){
        super(Family.all().get());
        this.world = world;

        debugRenderer = new Box2DDebugRenderer();
        this.camera = camera;


    }

    @Override
    public void update(float delta){
        super.update(delta);
        if(world != null){
            scaling = new Matrix4(camera.combined);
            scaling.scale(Box2DScale.PPM,Box2DScale.PPM,1f);
            debugRenderer.render(world,scaling);
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

}
