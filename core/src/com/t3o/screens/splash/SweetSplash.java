package com.t3o.screens.splash;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.t3o.screens.Ratio;

abstract public class SweetSplash implements Screen {
    protected SpriteBatch spriteBatch = null;

    protected OrthographicCamera camera = null;
    protected Viewport viewport = null;

    protected SweetSplash() {
        spriteBatch = new SpriteBatch();

        camera = new OrthographicCamera();
        //viewport = new StretchViewport(Ratio.getScreen("width") * Ratio.getScreen("aspectRatio"),Ratio.getScreen("height"),camera);
        //viewport.apply();
        camera.setToOrtho(false,Ratio.getScreen("orthoWidth"),Ratio.getScreen("orthoHeight"));

        spriteBatch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void render(float delta){
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        //camera = null;
        //viewport = null;
    }

}
