package com.t3o.gameworld.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.t3o.assets.Assets;
import com.t3o.gameworld.components.ColorBatchComponent;
import com.t3o.gameworld.components.PositionComponent;
import com.t3o.gameworld.components.StringDataComponent;
import com.t3o.gameworld.components.TextureComponent;

public class RoomsDebugSystem extends IteratingSystem{

    OrthographicCamera camera = null;
    SpriteBatch batch = null;
    BitmapFont font = null;


    private ComponentMapper<StringDataComponent> componentStringData = null;
    private ComponentMapper<PositionComponent> compomentPosition = null;
    private ComponentMapper<ColorBatchComponent> compomentColorBatch = null;

    private Array<Entity> renderQueue = null;

    public RoomsDebugSystem(OrthographicCamera camera, SpriteBatch batch, BitmapFont bitmapFont) {
        super(Family.all(PositionComponent.class, ColorBatchComponent.class, StringDataComponent.class).get());

        this.camera = camera;
        this.batch = batch;
        this.font = bitmapFont;

        componentStringData = ComponentMapper.getFor(StringDataComponent.class);
        compomentPosition = ComponentMapper.getFor(PositionComponent.class);
        compomentColorBatch = ComponentMapper.getFor(ColorBatchComponent.class);

        renderQueue = new Array<Entity>();
    }



    @Override
    public void update(float delta){
        super.update(delta);
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.setColor(1,1,1,1);
        for (Entity entity : renderQueue){
            StringDataComponent stringDataComponent = componentStringData.get(entity);
            PositionComponent positionComponent = compomentPosition.get(entity);
            ColorBatchComponent colorBatchComponent = compomentColorBatch.get(entity);

            font.setColor(colorBatchComponent.r,colorBatchComponent.g,colorBatchComponent.b,colorBatchComponent.alpha);
            font.draw(batch,stringDataComponent.data,positionComponent.position.x + 16f,positionComponent.position.y + 16f);

        }
        batch.end();
        renderQueue.clear();
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }
}
