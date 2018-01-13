package com.t3o.gameworld.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.t3o.gameworld.components.AttachComponent;
import com.t3o.screens.Ratio;
import com.t3o.utils.Box2DScale;

public class FollowCameraSystem extends IteratingSystem{

    private OrthographicCamera camera = null;

    private ComponentMapper<AttachComponent> mapper;

    private Entity entity = null;

    float[] fringle = new float[4];

    public FollowCameraSystem(final OrthographicCamera camera) {
        super(Family.all(AttachComponent.class).get());

        mapper =  ComponentMapper.getFor(AttachComponent.class);

        this.camera = camera;
    }

    @Override
    public void update(float delta){

        super.update(delta);

        if(entity != null){
            AttachComponent attachComponent = mapper.get(entity);
            Vector2 pos = Box2DScale.toPixels( attachComponent.subject.getPosition() );
            Vector2 cameraPos = new Vector2(pos);

            if(fringle[0] != 0 && fringle[1] != 0 && fringle[2] != 0 && fringle[3] != 0) {
                if (pos.x < fringle[0])
                    cameraPos.x = fringle[0];
                else if (pos.x > fringle[2])
                    cameraPos.x = fringle[2];

                if (pos.y < fringle[1])
                    cameraPos.y = fringle[1];
                else if (pos.y > fringle[3])
                    cameraPos.y = fringle[3];
            }



            camera.position.set(cameraPos,0);

        }

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        this.entity = entity;
    }

    public void setFringle(float width,float height){
        setFringle(width,height,false);
    }

    public void setFringle(float width,float height,boolean puffins){

        float heightOffset = puffins ? Ratio.sizeTile : 0,
                widthOffset = puffins ? Ratio.sizeTile : 0;

        fringle[0] = camera.viewportWidth/2 + widthOffset;
        fringle[1] = camera.viewportHeight/2 + heightOffset;

        fringle[2] = width - widthOffset - camera.viewportWidth/2 ;
        fringle[3] = height - heightOffset - camera.viewportHeight/2 ;

    }
}
