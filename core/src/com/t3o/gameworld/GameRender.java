package com.t3o.gameworld;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.t3o.assets.Assets;
import com.t3o.gameworld.systems.AnimationSystem;
import com.t3o.gameworld.systems.FollowCameraSystem;
import com.t3o.gameworld.systems.LightSystem;
import com.t3o.gameworld.systems.RenderSystem;
import com.t3o.screens.GameScreen;
import com.t3o.screens.parallax.ParallaxBackground;

public class GameRender extends GameModule{

    SpriteBatch spriteBatch = null;
    RenderSystem renderSystem = null;
    FollowCameraSystem followCameraSystem = null;
    AnimationSystem animationSystem = null;
    LightSystem lightSystem;


    ParallaxBackground parallax;

    Assets assets;

    public GameRender(final GameScreen gameScreen,final Assets assets){
        super(gameScreen);

        this.assets = assets;

        parallax = new ParallaxBackground(assets.getParallaxLayer("default"));

        spriteBatch = new SpriteBatch();

        renderSystem = new RenderSystem(spriteBatch,parallax);

        followCameraSystem = new FollowCameraSystem(renderSystem.getCamera());
        animationSystem = new AnimationSystem(assets.animations);





    }

    @Override
    public void init() {
        lightSystem = new LightSystem(getGameWorld().getWorld(),renderSystem.getCamera());
        getGameWorld().getEngine().addSystem(lightSystem);
    }


    public void render(float delta){

        //drawFps(0,0,assets.preFont,Color.RED);
    }


    private void drawFps(int x, int y, BitmapFont font,Color color){
        //spriteBatch.setProjectionMatrix(renderSystem.getCamera().combined);

        spriteBatch.begin();
        font.setColor(color);

        font.draw(spriteBatch,"fps: " + Gdx.graphics.getFramesPerSecond(),renderSystem.getCamera().position.x + x,renderSystem.getCamera().position.y + y);

        spriteBatch.end();
    }

    public OrthographicCamera getCamera(){
        return renderSystem.getCamera();
    }

    public RenderSystem getSystem(){
        return renderSystem;
    }

    public SpriteBatch getBatch() {
        return spriteBatch;
    }


    public FollowCameraSystem getAttach(){
        return followCameraSystem;
    }

    public boolean isAttached() {
        return getGameWorld().getEngine().getSystems().contains(followCameraSystem,true);
    }

    public void attachCamera(boolean b) {
        if(b) {
            getGameWorld().getEngine().addSystem(followCameraSystem);
        }
        else {
            getGameWorld().getEngine().removeSystem(followCameraSystem);
        }
    }

    public AnimationSystem getAnimationSystem() {
        return animationSystem;
    }


}
