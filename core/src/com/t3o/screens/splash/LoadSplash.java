package com.t3o.screens.splash;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.t3o.assets.Assets;
import com.t3o.screens.GameScreen;
import com.t3o.screens.Ratio;
import com.t3o.uwague.Main;

public class LoadSplash extends SweetSplash {
    private Main main;
    private float progress;

    Assets assets;

    public LoadSplash(final Main main,final Assets assets) {
        this.assets = assets;
        this.main = main;
    }

    private void update(float delta){
        progress = MathUtils.lerp(progress,assets.getProgress(),.1f);

        if(assets.isLoaded() &&  MathUtils.isEqual(progress,assets.getProgress(),.001f)){
            Gdx.app.log("LoadSplash", "Assets loaded");
            main.postLoadSplash();
        }
    }

    @Override
    public void show() {
        assets.loadQueue();
        progress = 0;
    }

    @Override
    public void render(float delta) {

        super.render(delta);
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        update(delta);

        spriteBatch.begin();
        assets.preFont.setColor(Color.BLACK);
        assets.preFont.draw(spriteBatch,progress + "", 0f,Ratio.getScreen("orthoHeight"));
        assets.preFont.draw(spriteBatch,"Development build", 0,0);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        super.dispose();
        Gdx.app.log("Load Splash", "dispose over");
    }
}
