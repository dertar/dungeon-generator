package com.t3o.gameworld.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.t3o.gameworld.comparators.LayerZComparator;
import com.t3o.gameworld.components.AttachComponent;
import com.t3o.gameworld.components.ColorBatchComponent;
import com.t3o.gameworld.components.PositionComponent;
import com.t3o.gameworld.components.TextureComponent;
import com.t3o.screens.Ratio;
import com.t3o.screens.parallax.ParallaxBackground;
import com.t3o.screens.parallax.ParallaxLayer;

import java.util.Comparator;

public class RenderSystem  extends SortedIteratingSystem{

    private SpriteBatch spriteBatch = null;

    private ComponentMapper<TextureComponent> componentTexture = null;
    private ComponentMapper<PositionComponent> compomentPosition = null;
    private ComponentMapper<ColorBatchComponent> compomentColorBatch = null;



    private OrthographicCamera camera = null;
    //private Viewport viewport = null;

    private Array<Entity> renderQueue = null;
    private Comparator<Entity> comparator = new LayerZComparator();

    ParallaxBackground background;



    public RenderSystem(final SpriteBatch spriteBatch,final ParallaxBackground background) {
        super(Family.all(PositionComponent.class, TextureComponent.class,ColorBatchComponent.class).get(), new LayerZComparator());
        this.spriteBatch = spriteBatch;

        componentTexture = ComponentMapper.getFor(TextureComponent.class);
        compomentPosition = ComponentMapper.getFor(PositionComponent.class);
        compomentColorBatch = ComponentMapper.getFor(ColorBatchComponent.class);


        renderQueue = new Array<Entity>();

        camera = new OrthographicCamera();
        //viewport = new StretchViewport(Ratio.getScreen("width") * Ratio.getScreen("aspectRatio"),Ratio.getScreen("height"),camera);
        //viewport.apply();
        camera.setToOrtho(false,Ratio.getScreen("orthoWidth"),Ratio.getScreen("orthoHeight"));

        this.background = background;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    @Override
    public void update(float deltaTime){
        super.update(deltaTime);

        renderQueue.sort(comparator);

        camera.update();

        background.update(deltaTime);
        background.render(spriteBatch,camera.projection,camera.viewportWidth,camera.viewportHeight);

        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();
        spriteBatch.setColor(1,1,1,1);
        for (Entity entity : renderQueue){
            TextureComponent textureComponent = componentTexture.get(entity);
            PositionComponent positionComponent = compomentPosition.get(entity);
            ColorBatchComponent colorBatchComponent = compomentColorBatch.get(entity);


            if(colorBatchComponent != null) spriteBatch.setColor(colorBatchComponent.r,colorBatchComponent.g,colorBatchComponent.b,colorBatchComponent.alpha);
            if(textureComponent.textureRegion != null) {


//                spriteBatch.draw(textureComponent.textureRegion,
//                        textureComponent.isMirrored ? positionComponent.position.x + textureComponent.textureRegion.getRegionWidth() : positionComponent.position.x,
//                        positionComponent.position.y,
//                        textureComponent.isMirrored ? positionComponent.position.x + textureComponent.textureRegion.getRegionWidth() : positionComponent.position.x,
//                        positionComponent.position.y * positionComponent.scale.y,
//                        textureComponent.isMirrored ? -(textureComponent.textureRegion.getRegionWidth() * positionComponent.scale.x) : textureComponent.textureRegion.getRegionWidth() * positionComponent.scale.x,
//                        textureComponent.textureRegion.getRegionHeight() * positionComponent.scale.y,
//                        1f,
//                        1f,
//                        positionComponent.rotation);
                spriteBatch.draw(textureComponent.textureRegion,
                        textureComponent.isMirrored ? positionComponent.position.x + textureComponent.textureRegion.getRegionWidth() * positionComponent.scale.x  : positionComponent.position.x,
                        positionComponent.position.y,
                        positionComponent.position.x,
                        positionComponent.position.y,
                        textureComponent.isMirrored ? -(textureComponent.textureRegion.getRegionWidth() * positionComponent.scale.x) : textureComponent.textureRegion.getRegionWidth() * positionComponent.scale.x,
                        textureComponent.textureRegion.getRegionHeight() * positionComponent.scale.y,
                        1f,
                        1f,
                        positionComponent.rotation);

            }
        }

        spriteBatch.end();
        renderQueue.clear();
    }

    public void show(){
        //viewport.apply();

        camera.update();
    }

    public OrthographicCamera getCamera(){
        return camera;
    }

}
