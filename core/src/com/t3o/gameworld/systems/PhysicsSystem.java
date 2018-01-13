package com.t3o.gameworld.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;
import com.t3o.gameworld.components.BodyComponent;
import com.t3o.gameworld.components.PositionComponent;
import com.t3o.gameworld.components.StateComponent;
import com.t3o.utils.Box2DScale;

public class PhysicsSystem extends IteratingSystem{
    public static final short BIT_SUBJECTS = 0x0001;
    public static final short BIT_PLATFROMS = 0x0002;
    public static final short BIT_PASSIVE = 0x0004;



    World world = null;
    Array<Entity> bodiesQueue;

    ComponentMapper<BodyComponent> bodyMapper = ComponentMapper.getFor(BodyComponent.class);
    ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);
    //ComponentMapper<StateComponent> stateMapper;

    private boolean isUpdate;
    private float accumulator = 0f;
    static private float STEP = 1/60f;

    public PhysicsSystem(World world){
        super(Family.all(BodyComponent.class,PositionComponent.class).get());
        stop();

        this.world = world;

        bodiesQueue = new Array<Entity>();
        accumulator = 0f;
        //stateMapper = ComponentMapper.getFor(StateComponent.class);
    }


    @Override
    public void update(float delta){
        if(isUpdate && world != null) {
            super.update(delta);

            float frameTime = Math.min(delta, 0.25f);
            accumulator += frameTime;
            if (accumulator >= STEP) {
                world.step(STEP, 1, 1);

                for (Entity entity : bodiesQueue) {
                    PositionComponent positionComponent = posMapper.get(entity);
                    BodyComponent bodyComponent = bodyMapper.get(entity);
                    //StateComponent stateComponent = stateMapper.get(entity);

                    Vector2 pos = bodyComponent.body.getPosition();


                    positionComponent.position.x = Box2DScale.toPixels(pos.x);
                    positionComponent.position.y = Box2DScale.toPixels(pos.y);



                    positionComponent.rotation = bodyComponent.body.getAngle() * MathUtils.radiansToDegrees;
                }

                accumulator -= STEP;
            }

            bodiesQueue.clear();
        }
    }

    public void destroyAll(){
        for (Entity entity : bodiesQueue) {
            PositionComponent positionComponent = posMapper.get(entity);
            BodyComponent bodyComponent = bodyMapper.get(entity);
            Vector2 pos = bodyComponent.body.getPosition();

            world.destroyBody(bodyComponent.body);
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        bodiesQueue.add(entity);
    }

    public void start(){
        isUpdate = true;
    }

    public void stop(){
        isUpdate = false;
    }


    public Body createBody(float x, float y, float width, float height, BodyDef.BodyType type, boolean isRect,
                           boolean sleep, boolean bullet,short catBits,short maskBits){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;

        bodyDef.position.set(x,y);

        Body ret = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = isRect ?  getRectShape(width,height) : getCircleShape(width);

        fixtureDef.filter.categoryBits = catBits;
        fixtureDef.filter.maskBits = maskBits;

        ret.createFixture(fixtureDef);
        ret.setSleepingAllowed(sleep);
        ret.setBullet(bullet);

        fixtureDef.shape.dispose();

        return ret;
    }


    PolygonShape getRectShape(float width, float height){
        PolygonShape shape = new PolygonShape();

        shape.set(new float[]
                {       0, 0,
                        width,0,
                        width, height,
                        0, height

                });
        return shape;
    }

    CircleShape getCircleShape(float size){
        CircleShape shape = new CircleShape();

        shape.setRadius(size);

        return shape;
    }
}
