package com.t3o.gameworld.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.t3o.gameobjects.subjects.Subject;
import com.t3o.gameobjects.userdata.UserData;
import com.t3o.gameworld.components.AnimationComponent;
import com.t3o.gameworld.components.MovementComponent;
import com.t3o.gameworld.components.StateComponent;
import com.t3o.gameworld.components.TextureComponent;
import com.t3o.screens.Ratio;
import com.t3o.utils.Box2DScale;


public class MovementSystem extends IteratingSystem{

    ComponentMapper<MovementComponent> mapperMovement = null;
    ComponentMapper<TextureComponent> mapperTexture = null;
    ComponentMapper<StateComponent> mapperState;
    ComponentMapper<AnimationComponent> mapperAnim;

    private Entity entity = null;

    World world;
    public MovementSystem(final World world) {
        super(Family.all(MovementComponent.class, TextureComponent.class,AnimationComponent.class,StateComponent.class).get());

        this.world = world;

        mapperMovement = ComponentMapper.getFor(MovementComponent.class);
        mapperTexture = ComponentMapper.getFor(TextureComponent.class);
        mapperState = ComponentMapper.getFor(StateComponent.class);
        mapperAnim = ComponentMapper.getFor(AnimationComponent.class);
    }

    @Override
    public void update(float delta){
        super.update(delta);
        if(entity != null){

            MovementComponent move = mapperMovement.get(entity);
            TextureComponent texture = mapperTexture.get(entity);
            StateComponent state = mapperState.get(entity);
            AnimationComponent animation = mapperAnim.get(entity);


//            /move.subject.getBody().getFixtureList().first().setFriction(0f);
            if(state.is(Subject.STATE.IDLE) ||
                    state.is(Subject.STATE.MOVE)) {
                //stopping
                if (!Gdx.input.isKeyPressed(move.keyLeft) && !Gdx.input.isKeyPressed(move.keyRight)) {
                    //move.subject.getBody().setLinearVelocity(0f,0f);
                    move.subject.getBody().getFixtureList().first().setFriction(10f);
                }

                if (Gdx.input.isKeyPressed(move.keyLeft) && move.subject.getBody().getLinearVelocity().x > -move.subject.getMaxVelocity()) {
                    move.subject.getBody().applyLinearImpulse(-move.subject.getWalkImpulse(), 0f, move.subject.getPosition().x, move.subject.getPosition().y, true);
                    texture.isMirrored = true;
                    //state.change(AnimationResource.ANIMATIONS.MOVE);
                }
                if (Gdx.input.isKeyPressed(move.keyRight) && move.subject.getBody().getLinearVelocity().x < move.subject.getMaxVelocity()) {
                    move.subject.getBody().applyLinearImpulse(move.subject.getWalkImpulse(), 0f, move.subject.getPosition().x, move.subject.getPosition().y, true);
                    texture.isMirrored = false;
                    //state.change(AnimationResource.ANIMATIONS.MOVE);
                }

                if (Gdx.input.isKeyPressed(move.keyJump)) {
                    //Gdx.app.log("move","key jump pressed");
                    move.subject.getBody().setLinearVelocity(0f,move.subject.getJumpImpulse());
                }
                stabilizeVelocity(move.subject.getBody(),move.subject.getMaxVelocity());
            }else if(state.is(Subject.STATE.JUMP)){

                if (Gdx.input.isKeyPressed(move.keyLeft) || Gdx.input.isKeyPressed(move.keyRight)) {
                    move.subject.getBody().getFixtureList().first().setFriction(0f);
                }

                float div = 1.5f;
                if (Gdx.input.isKeyPressed(move.keyLeft) && move.subject.getBody().getLinearVelocity().x > -move.subject.getMaxVelocity()/div) {
                    move.subject.getBody().applyLinearImpulse(-move.subject.getWalkImpulse()/div, 0f, move.subject.getPosition().x, move.subject.getPosition().y, false);
                    texture.isMirrored = true;
                }
                if (Gdx.input.isKeyPressed(move.keyRight) && move.subject.getBody().getLinearVelocity().x < move.subject.getMaxVelocity()/div) {
                    move.subject.getBody().applyLinearImpulse(move.subject.getWalkImpulse()/div, 0f, move.subject.getPosition().x, move.subject.getPosition().y, false);
                    texture.isMirrored = false;
                }
                stabilizeVelocity(move.subject.getBody(),MathUtils.floor( move.subject.getMaxVelocity()/div ));
            }
            //Gdx.app.log("info", move.subject.getBody().getLinearVelocity().toString());

            updateState(move.subject.getBody(),state,animation);

        }
    }
    void stabilizeVelocity(Body body,float maxVelocity){
        if(Math.abs(body.getLinearVelocity().x) > maxVelocity) {
            body.getLinearVelocity().x = Math.signum(body.getLinearVelocity().x) * maxVelocity;
            body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y);
        }
    }

    void updateState(Body player,StateComponent stateComponent,AnimationComponent animations){
        boolean onGround = isGrounded(player.getFixtureList().first(),player.getPosition());
        boolean changed = false;

        if(onGround){
            //player.getFixtureList().first().setFriction(100f);
            if (stateComponent.is(Subject.STATE.JUMP)) {
                player.setLinearVelocity(0f,0f);
                //Gdx.app.log("PhysSystem","Landed");
                changed = stateComponent.change(Subject.STATE.IDLE);


            }else if(player.getLinearVelocity().epsilonEquals(0f,0f,0.1f)){
                changed = stateComponent.change(Subject.STATE.IDLE);
                //player.getFixtureList().first().setFriction(10f);
            }else {
                changed = stateComponent.change(Subject.STATE.MOVE);
                //player.getFixtureList().first().setFriction(10f);
            }
        }else{
            if(stateComponent.is(Subject.STATE.IDLE) ||
                    stateComponent.is(Subject.STATE.MOVE) ) {
                //Gdx.app.log("PhysSystem", "Jumped");

                //player.getFixtureList().first().setFriction(0f);
            }
            changed = stateComponent.change(Subject.STATE.JUMP);
        }

        animations.animations = stateComponent.get();
        animations.wipeStateTime(changed);
        //Gdx.app.log("friction",player.getFixtureList().first().getFriction() + "");
    }

    boolean isGrounded(Fixture player, Vector2 pos){
        Array<Contact> contacts = world.getContactList();
        //Gdx.app.log("score" ,contacts.size + "");
        boolean ground =  false;
        for(Contact it : contacts){
            if(it.isTouching() && (it.getFixtureA() == player || it.getFixtureB() == player)){
                WorldManifold manifold = it.getWorldManifold();
                it.setFriction(0f);
                UserData dataA = (UserData)it.getFixtureA().getBody().getUserData(),
                         dataB = (UserData)it.getFixtureB().getBody().getUserData();
                //Gdx.app.log("score" ,manifold.getNumberOfContactPoints() + "");
//                Gdx.app.log("A" , UserData.getInfo(dataA));
//                Gdx.app.log("B" , UserData.getInfo(dataB));
//                Gdx.app.log("boolean" , "" + (dataA != null && dataB != null && (onFloor(dataA,dataB) || onFloor(dataB,dataA))));
                if(dataA != null && dataB != null) {
                    Fixture floor, subject;
                    if (dataA.toString().equals(UserData.TYPE.FLOOR.toString())) {
                        floor = it.getFixtureA();
                        subject = it.getFixtureB();
                    } else {
                        floor = it.getFixtureB();
                        subject = it.getFixtureA();
                    }


                    if ( onFloor((UserData)subject.getBody().getUserData(),(UserData)floor.getBody().getUserData()) ){
                        Vector2 topEdge = floor.getBody().
                                getPosition().add(0f, Box2DScale.toBox2D( Ratio.sizeTile));

//|| topEdge.x + Box2DScale.toBox2D( Ratio.sizeTile) >= pos.x + Box2DScale.toBox2D(30)) &&topEdge.x <= pos.x
                        if( topEdge.y < pos.y &&
                                MathUtils.isEqual(player.getBody().getLinearVelocity().y,0f,0.5f)){

                            //Gdx.app.log(player.getBody().getLinearVelocity().toString(),"");
                            it.setFriction(1f);
                            ground = true;
                        }
                        /*for (int i = 0; i < manifold.getNumberOfContactPoints(); i++) {
                            //it.getFixtureA().getBody();

                            if (manifold.getPoints()[i].y < pos.y ) {
                                ground = true;
                                break;
                            }
                        }*/
                    }
                }

                if(ground) return true;

            }
        }
        return ground;
    }



    boolean onFloor(UserData subject,UserData floor){
        return subject.toString().equals(UserData.TYPE.SUBJECT.toString()) &&
                floor.toString().equals(UserData.TYPE.FLOOR.toString());
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        this.entity = entity;
    }
}
