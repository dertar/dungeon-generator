package com.t3o.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.t3o.assets.Assets;
import com.t3o.gameworld.GameDebug;
import com.t3o.gameworld.GameInput;
import com.t3o.gameworld.GameRender;
import com.t3o.gameworld.GameWorld;

public class GameScreen implements Screen{

    private GameWorld gameWorld = null;
    private GameRender gameRender = null;
    private GameInput gameInput = null;
    private GameDebug gameDebug = null;

    //private SpriteBatch testBatch = new SpriteBatch();

    Assets assets;

    public GameScreen(final Assets assets){
        this.assets = assets;

        gameWorld = new GameWorld(this,assets);
        gameRender = new GameRender(this,assets);
        gameInput = new GameInput(this);
        gameDebug = new GameDebug(this,assets);

        gameWorld.init();
        gameRender.init();
        gameInput.init();
        gameDebug.init();
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public GameRender getGameRender() {
        return gameRender;
    }

    public GameInput getGameInput() {
        return gameInput;
    }

    public GameDebug getGameDebug() {
        return gameDebug;
    }


    @Override
    public void show() {

    }

    private void update(float delta){
        gameInput.handle();
        gameWorld.update(delta);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f,0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameRender.render(delta);
        this.update(delta);



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
        gameWorld.dispose();
    }

    public void newGame() {
        gameWorld.newGame();
    }


}
