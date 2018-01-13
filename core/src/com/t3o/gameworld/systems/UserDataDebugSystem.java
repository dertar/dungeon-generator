package com.t3o.gameworld.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.t3o.gameobjects.userdata.UserData;
import com.t3o.gameworld.components.BodyComponent;
import com.t3o.gameworld.components.ColorBatchComponent;
import com.t3o.gameworld.components.StringDataComponent;
import com.t3o.screens.Ratio;
import com.t3o.utils.Box2DScale;

public class UserDataDebugSystem extends IteratingSystem{

    OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFont bitmapFont;

    ComponentMapper<BodyComponent> body;

    Array<Entity> entities;

    public UserDataDebugSystem(OrthographicCamera camera, SpriteBatch batch, BitmapFont bitmapFont) {
        super(Family.all(BodyComponent.class).get());

        this.camera = camera;
        this.batch = batch;
        this.bitmapFont = bitmapFont;

        entities = new Array<Entity>();

        body = ComponentMapper.getFor(BodyComponent.class);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.setColor(1,1,1,1);
        for (Entity entity : entities){

            BodyComponent bodyComponent = body.get(entity);


            UserData data = (UserData)bodyComponent.body.getUserData();

            if(data != null) {
                bitmapFont.setColor(data.getColor()[0],data.getColor()[1],data.getColor()[2],data.getColor()[3]);

                bitmapFont.draw(batch, data.acronym(), Box2DScale.toPixels( bodyComponent.body.getPosition().x )+ 16f,
                        Box2DScale.toPixels( bodyComponent.body.getPosition().y ) + 16f);
            }

        }
        batch.end();
        entities.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        entities.add(entity);
    }
}
